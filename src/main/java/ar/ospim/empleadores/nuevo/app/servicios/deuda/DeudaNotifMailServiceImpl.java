package ar.ospim.empleadores.nuevo.app.servicios.deuda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaMailInfoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;
import ar.ospim.empleadores.nuevo.app.servicios.deuda.infomail.DeudaNotifMailGetFechaProcesoVigenteService;
import ar.ospim.empleadores.nuevo.app.servicios.deuda.infomail.DeudaNotifMailGetService;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.DeudaNominaMailConfigService;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.DeudaNotifMailRegistrarService;
import ar.ospim.empleadores.nuevo.app.servicios.mail.MailService;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNominaMailEnvio;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class DeudaNotifMailServiceImpl implements DeudaNotifMailService {

	private final DeudaNominaMailConfigService deudaNominaMailConfigService;
	private final DeudaNotifMailGetService deudaNotifMailGetService;
	private final MailService mailService;
	private final DeudaNotifMailRegistrarService deudaNotifMailRegistrarService;
	private final DeudaNotifMailGetFechaProcesoVigenteService fechaProcesoGetService;
	 
	
	
	public DeudaNotifMailServiceImpl(DeudaNotifMailGetService deudaNotifMailGetService,
			DeudaNominaMailConfigService deudaNominaMailConfigService, MailService mailService,
			DeudaNotifMailRegistrarService deudaNotifMailRegistrarService,
			DeudaNotifMailGetFechaProcesoVigenteService fechaProcesoGetService) {
		super();
		this.deudaNotifMailGetService = deudaNotifMailGetService;
		this.deudaNominaMailConfigService = deudaNominaMailConfigService;
		this.mailService = mailService;
		this.deudaNotifMailRegistrarService = deudaNotifMailRegistrarService;
		this.fechaProcesoGetService = fechaProcesoGetService;
	}


    @Scheduled(cron = "${app.cron.ejecucion-frecuencia}")
	@Override
	public void run() {
		log.debug("Scheduler - Notificacion Deuda - INIT");
		
		Optional<DeudaNominaMailConfigBO> mailPlantillaBO = deudaNominaMailConfigService.consultarVigente();
		if ( mailPlantillaBO.isEmpty() ) {
			log.debug("Scheduler - Notificacion Deuda - SIN PROCESAR - Falta crear Plantilla ");
			return;
		}
		
		if ( !mailPlantillaBO.get().getHabilitado() ) {
			log.debug("Scheduler - Notificacion Deuda - SIN PROCESAR - Plantilla deshabilitada ");						
			return;
		}
				
		Optional<LocalDate> fechaProcesoVigente = fechaProcesoGetService.run();				
		if( fechaProcesoVigente.isEmpty() ) {
			log.debug("Scheduler - Notificacion Deuda - SIN PROCESAR - Fecha de Proceso Vigente NULA ");		
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
			String cuerpoMail = mailPlantillaBO.get().getCuerpoMail().replace("{{capital}}", empresaDeuda.getImporte().toString() );
			cuerpoMail = cuerpoMail.replace("{{interes}}", empresaDeuda.getInteres().toString() );

			
			//Log resultado envio Mail
			DeudaNominaMailEnvio mailLog = new DeudaNominaMailEnvio();
			mailLog.setCuerpoMail(cuerpoMail);
			mailLog.setCuit(empresaDeuda.getCuit());
			mailLog.setDatos( lstEmpresaDeuda.toString() );
			mailLog.setFechaEnvio( LocalDateTime.now());
			mailLog.setFechaProceso( fechaProcesoVigente.get() );
			
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

}
