package ar.ospim.empleadores.nuevo.app.servicios.mail;

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

import ar.ospim.empleadores.nuevo.dominio.MailEnum;
import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;
import ar.ospim.empleadores.nuevo.dominio.MailTipoDdjjPendienteBO;
import ar.ospim.empleadores.nuevo.dominio.MailTipoDeudaInfoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.MailTipoEnvio;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MailTipoNotifScheduledServiceImpl implements MailTipoNotifScheduledService {

	private final MailTipoConfiguracionService mailTipoConfiguracionService;
	private final MailTipoConsultarNotifDeudaService mailTipoConsultarDeudaNotifService;
	private final MailTipoConsultarNotifDdjjPendienteService mailTipoConsultarNotifDdjjPendienteService;
	private final MailService mailService;
	private final MailTipoEnvioRegistrarService mailTipoEnvioRegistrarService;
	private final MailTipoConfigValidarCuerpoMail mailTipoConfigValidarCuerpoMail;
	 
	
	
	public MailTipoNotifScheduledServiceImpl(MailTipoConsultarNotifDeudaService deudaNotifMailGetService,
			MailTipoConsultarNotifDdjjPendienteService mailTipoConsultarNotifDdjjPendienteService,
			MailTipoConfiguracionService mailTipoConfiguracionService, 
			MailService mailService,
			MailTipoEnvioRegistrarService mailTipoEnvioRegistrarService,
			MailTipoConfigValidarCuerpoMail mailTipoConfigValidarCuerpoMail) {
		super();
		this.mailTipoConsultarDeudaNotifService = deudaNotifMailGetService;
		this.mailTipoConsultarNotifDdjjPendienteService = mailTipoConsultarNotifDdjjPendienteService;
		this.mailTipoConfiguracionService = mailTipoConfiguracionService;
		this.mailService = mailService;
		this.mailTipoEnvioRegistrarService = mailTipoEnvioRegistrarService;
		this.mailTipoConfigValidarCuerpoMail = mailTipoConfigValidarCuerpoMail;
	}


    @Scheduled(cron = "${app.cron.frecuencia}")
	@Override
	public void run() {
		log.debug("Scheduler - INIT");
		procesarMailDeuda();
		procesarMailDDJJPendiente();
		log.debug("Scheduler - FIN");		
	}
    
    
    private void procesarMailDeuda() {
		log.debug("Scheduler - Notificacion Deuda - INIT");
    	Optional<MailTipoConfiguracionBO> mailTipoConfigBO = getConfiguracion(MailEnum.AVISO_DEUDA.getId());		
		if ( mailTipoConfigBO.isEmpty() )
			return;
		
		
		List<List<MailTipoDeudaInfoBO>> lst = consultarRegistrosNotifDeuda();
		if ( lst == null ) {
			log.debug("Scheduler - Notificacion Deuda - registros: NULL" );
			return;
		}
		
		log.debug("Scheduler - Notificacion Deuda - registros: {}", lst.size() );
		//2 recorro cada CUIT y genero mail.-
		for (List<MailTipoDeudaInfoBO> lstEmpresaDeuda : lst) {

			//TODO: hay que definir como se muestra la INFO !!!!
			//TESTING: sumo toda la deuda y la imprimo.-
			MailTipoDeudaInfoBO empresaDeuda = new MailTipoDeudaInfoBO();
			empresaDeuda.setImporte(BigDecimal.ZERO);
			empresaDeuda.setInteres(BigDecimal.ZERO);
			
			for (MailTipoDeudaInfoBO reg : lstEmpresaDeuda) {
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

			//"https://uomaempleadores.org.ar/empleadores/#/login"
			//"https://uomaempleadores.org.ar/empleadores/#/login?redirect=gestiondeuda"
			if ( cuerpoMail.indexOf("{{login}}") > -1 ) {
				cuerpoMail = cuerpoMail.replace("{{login}}", "<a href=\"https://uomaempleadores.org.ar/empleadores/#/login?redirect=gestiondeuda\" rel=\"noopener noreferrer\" target=\"_blank\">link</a>" );
			}
			
			//genero Mail
			MailTipoEnvio mailLog = new MailTipoEnvio();
			try {
				mailService.runMailDdjjPendienteNotif(empresaDeuda.getEmail(), mailTipoConfigBO.get().getAsuntoMail(), cuerpoMail);
				mailLog.setEstado("OK");
			} catch ( Exception e) {
				mailLog.setEstado("ERROR: " + e.toString() );
			}
			
			
			//Log resultado envio Mail			
			mailLog.setCuerpoMail(cuerpoMail);
			mailLog.setCuit(empresaDeuda.getCuit());
			mailLog.setDatos( lstEmpresaDeuda.toString() );
			mailLog.setCuerpoMail(cuerpoMail);
			mailLog.setFechaEnvio( LocalDateTime.now());
			mailLog.setMailTipoConfigId(mailTipoConfigBO.get().getId());
			
			//Registro resultado Envio
			mailTipoEnvioRegistrarService.run(mailLog);						
		}
		log.debug("Scheduler - Notificacion Deuda - FIN");		
    } 

    private void procesarMailDDJJPendiente() {
		log.debug("Scheduler - Notificacion DDJJ Pendiente - INIT");		
    	Optional<MailTipoConfiguracionBO> mailTipoConfigBO = getConfiguracion(MailEnum.DDJJ_PENDIENTE.getId());		
		if ( mailTipoConfigBO.isEmpty() )
			return;

		List<MailTipoDdjjPendienteBO> lst = consultarRegistrosDDJJPendiente();
		if ( lst == null ) {
			log.debug("Scheduler - Notificacion DDJJ Pendiente - registros: NULL" );
			return;
		}
		
		for (MailTipoDdjjPendienteBO reg : lst) {
			String cuerpoMail = mailTipoConfigBO.get().getCuerpoMail().replace("{{periodo}}", reg.getPeriodo() );
			cuerpoMail = cuerpoMail.replace("{{cuit}}", reg.getCuit() );
			cuerpoMail = cuerpoMail.replace("{{razon_social}}", reg.getRazonSocial() );

				//"https://uomaempleadores.org.ar/empleadores/#/login"
			//"https://uomaempleadores.org.ar/empleadores/#/login?redirect=gestiondeuda"
			if ( cuerpoMail.indexOf("{{login}}") > -1 ) {
				cuerpoMail = cuerpoMail.replace("{{login}}", "<a href=\"https://uomaempleadores.org.ar/empleadores/#/login?redirect=ddjj/alta\" rel=\"noopener noreferrer\" target=\"_blank\">link</a>" );
			}

			//genero Mail
			MailTipoEnvio mailLog = new MailTipoEnvio();
			try {
				mailService.runMailDeudaNotif(reg.getEmail(), mailTipoConfigBO.get().getAsuntoMail(), cuerpoMail);
				mailLog.setEstado("OK");
			} catch ( Exception e) {
				mailLog.setEstado("ERROR: " + e.toString() );
			}
			
			//Log resultado envio Mail			
			mailLog.setCuerpoMail(cuerpoMail);
			mailLog.setCuit(reg.getCuit());
			mailLog.setDatos( reg.toString() );
			mailLog.setFechaEnvio( LocalDateTime.now());
			mailLog.setMailTipoConfigId(mailTipoConfigBO.get().getId());
			
			//Registro resultado Envio
			mailTipoEnvioRegistrarService.run(mailLog);						

		}

		log.debug("Scheduler - Notificacion DDJJ Pendiente - FIN");		
    }
    
    private Boolean esDiaProceso(MailTipoConfiguracionBO config) {
    	
    	Optional<LocalDate> fechaDesde = mailTipoConfiguracionService.getFechaEnvioDesde(config.getMailId());
    	if ( fechaDesde.isEmpty() )
    		return false;
    	
    	Optional<LocalDate> fechaHasta = mailTipoConfiguracionService.getFechaEnvioHasta();
    	if ( fechaHasta.isEmpty() )
    		return false;
    	
    	return LocalDate.now().isAfter(fechaDesde.get()) && LocalDate.now().isBefore(fechaHasta.get()); 
    }
    
    private Optional<MailTipoConfiguracionBO> getConfiguracion(Integer mailId) {
    	
		Optional<MailTipoConfiguracionBO> mailTipoConfigBO = mailTipoConfiguracionService.consultarVigente(mailId);
		if ( mailTipoConfigBO.isEmpty() ) {			
			log.debug("Scheduler - " +MailEnum.map(mailId).getDescripcion()+ " - SIN PROCESAR - Falta crear Plantilla ");
			return mailTipoConfigBO;
		}
		
		if ( !mailTipoConfigBO.get().getHabilitado() ) {
			log.debug("Scheduler - " +MailEnum.map(mailId).getDescripcion()+ " - SIN PROCESAR - Plantilla deshabilitada ");
			mailTipoConfigBO = Optional.ofNullable(null);
			return mailTipoConfigBO;
		}
				
		if ( !esDiaProceso(mailTipoConfigBO.get()) ) {
			log.debug("Scheduler - " +MailEnum.map(mailId).getDescripcion()+ " - SIN PROCESAR - No es Dia de Proceso - " + mailTipoConfigBO.get().getDiaProceso() );
			mailTipoConfigBO = Optional.ofNullable(null);
			return mailTipoConfigBO;
		}
		
		if( mailTipoConfigValidarCuerpoMail.run(mailId, mailTipoConfigBO.get().getCuerpoMail()) ) {
			log.debug("Scheduler - " +MailEnum.map(mailId).getDescripcion()+ " - SIN PROCESAR - Plantilla de Cuerpo de Mail con variables mal configuradas. Se debe incluir: " + mailTipoConfigValidarCuerpoMail.getVariables(mailId) );			
		}
		
		return mailTipoConfigBO;		
    }
    
    private List<List<MailTipoDeudaInfoBO>> consultarRegistrosNotifDeuda() {
		List<MailTipoDeudaInfoBO> consulta = mailTipoConsultarDeudaNotifService.run();					
		if ( consulta.size() == 0 ) {
			log.debug("Scheduler - Notificacion Deuda - SIN PROCESAR - NO HAY REGISTROS pendentes y con deuda ");
			return null;
		}
		
		
		//1 juntar todos los registros de 1 cuit
		List<List<MailTipoDeudaInfoBO>> lst = new ArrayList<>(
				consulta.stream()
			        .collect(Collectors.groupingBy(
			            MailTipoDeudaInfoBO::getCuit,
			            LinkedHashMap::new,
			            Collectors.toList()))
			        .values()
			);
		log.debug("Scheduler - Notificacion Deuda - registros: {}", lst.size() );
		return lst;
    }
    
    private List<MailTipoDdjjPendienteBO> consultarRegistrosDDJJPendiente(){
    	List<MailTipoDdjjPendienteBO> lst = mailTipoConsultarNotifDdjjPendienteService.run();
    	return lst;
    }
    
}
