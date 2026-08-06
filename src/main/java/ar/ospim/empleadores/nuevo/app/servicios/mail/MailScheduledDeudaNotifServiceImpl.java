package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.servicios.deuda.infomail.DeudaNotifMailGetService;
import ar.ospim.empleadores.nuevo.dominio.DeudaMailInfoBO;
import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.MailTipoEnvio;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MailScheduledDeudaNotifServiceImpl implements MailScheduledDeudaNotifService {

	private final int MAIL_ID_NOTIF_DEUDA = 1; 
	private final MailTipoConfiguracionService mailTipoConfiguracionService;
	private final DeudaNotifMailGetService deudaNotifMailGetService;
	private final MailService mailService;
	private final MailTipoEnvioRegistrarService deudaNotifMailRegistrarService;
	 
	
	
	public MailScheduledDeudaNotifServiceImpl(DeudaNotifMailGetService deudaNotifMailGetService,
			MailTipoConfiguracionService mailTipoConfiguracionService, MailService mailService,
			MailTipoEnvioRegistrarService deudaNotifMailRegistrarService) {
		super();
		this.deudaNotifMailGetService = deudaNotifMailGetService;
		this.mailTipoConfiguracionService = mailTipoConfiguracionService;
		this.mailService = mailService;
		this.deudaNotifMailRegistrarService = deudaNotifMailRegistrarService;
	}


    @Scheduled(cron = "${app.cron.frecuencia}")
	@Override
	public void run() {
		log.debug("Scheduler - Notificacion Deuda - INIT");
		
		Optional<MailTipoConfiguracionBO> mailTipoConfigBO = mailTipoConfiguracionService.consultarVigente(MAIL_ID_NOTIF_DEUDA);
		if ( mailTipoConfigBO.isEmpty() ) {
			log.debug("Scheduler - Notificacion Deuda - SIN PROCESAR - Falta crear Plantilla ");
			return;
		}
		
		if ( !mailTipoConfigBO.get().getHabilitado() ) {
			log.debug("Scheduler - Notificacion Deuda - SIN PROCESAR - Plantilla deshabilitada ");						
			return;
		}
				
		if ( !esDiaProceso(mailTipoConfigBO.get()) ) {
			log.debug("Scheduler - Notificacion Deuda - SIN PROCESAR - No es Dia de Proceso - " + mailTipoConfigBO.get().getDiaProceso() );
			return;
		}
		
		List<DeudaMailInfoBO> consulta = deudaNotifMailGetService.run();					
		if ( consulta.size() == 0 ) {
			log.debug("Scheduler - Notificacion Deuda - SIN PROCESAR - NO HAY REGISTROS pendentes y con deuda ");
			return;
		}
		
		
		//1 juntar todos los registros de 1 cuit
		List<List<DeudaMailInfoBO>> lst = new ArrayList<>(
				consulta.stream()
			        .collect(Collectors.groupingBy(
			            DeudaMailInfoBO::getCuit,
			            LinkedHashMap::new,
			            Collectors.toList()))
			        .values()
			);
		log.debug("Scheduler - Notificacion Deuda - registros: {}", lst.size() );
		
		//2 recorro cada CUIT y genero mail.-
		for (List<DeudaMailInfoBO> lstEmpresaDeuda : lst) {

			//TODO: hay que definir como se muestra la INFO !!!!
			//TESTING: sumo toda la deuda y la imprimo.-
			DeudaMailInfoBO empresaDeuda = new DeudaMailInfoBO();
			empresaDeuda.setImporte(BigDecimal.ZERO);
			empresaDeuda.setInteres(BigDecimal.ZERO);
			
			for (DeudaMailInfoBO reg : lstEmpresaDeuda) {
				empresaDeuda.setCuit(reg.getCuit());
				empresaDeuda.setEmail(reg.getEmail());
				empresaDeuda.setEntidad(reg.getEntidad());
				empresaDeuda.setImporte( reg.getImporte().add(empresaDeuda.getImporte()));
				empresaDeuda.setInteres( reg.getInteres().add(empresaDeuda.getInteres()));
			}
			
			//TODO: hay que definir como se muestra la INFO !!!!
		    //armo cuerpo mail
			String cuerpoMail = mailTipoConfigBO.get().getCuerpoMail().replace("{{capital}}", empresaDeuda.getImporte().toString() );
			cuerpoMail = cuerpoMail.replace("{{interes}}", empresaDeuda.getInteres().toString() );

			
			//Log resultado envio Mail
			MailTipoEnvio mailLog = new MailTipoEnvio();
			mailLog.setCuerpoMail(cuerpoMail);
			mailLog.setCuit(empresaDeuda.getCuit());
			mailLog.setDatos( lstEmpresaDeuda.toString() );
			mailLog.setFechaEnvio( LocalDateTime.now());
			mailLog.setMailTipoConfigId(mailTipoConfigBO.get().getId());
			
			//genero Mail
			try {
				mailService.runMailDeudaNotif(empresaDeuda.getEmail(), cuerpoMail);
				mailLog.setEstado("OK");
			} catch ( Exception e) {
				mailLog.setEstado("ERROR: " + e.toString() );
			}
			
			//Registro resultado Envio
			deudaNotifMailRegistrarService.run(mailLog);						
		}
		log.debug("Scheduler - Notificacion Deuda - FIN");		
	}

    private Boolean esDiaProceso(MailTipoConfiguracionBO config) {
    	
    	LocalDate hoy = LocalDate.now();
    	LocalDate envioInicio = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
    	envioInicio = envioInicio.plusDays(config.getDiaProceso()-2);
    	
    	LocalDate envioFin = envioInicio.plusDays(3);

    	return 	hoy.isAfter(envioInicio) && hoy.isBefore(envioFin); 

    }
}
