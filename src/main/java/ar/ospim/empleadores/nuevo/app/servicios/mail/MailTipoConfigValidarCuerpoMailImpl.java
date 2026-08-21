package ar.ospim.empleadores.nuevo.app.servicios.mail;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.dominio.MailEnum;

@Service
public class MailTipoConfigValidarCuerpoMailImpl implements MailTipoConfigValidarCuerpoMail {

	
	@Override
	public boolean run(Integer mailTipo, String cuerpo) {
		
		if ( MailEnum.DDJJ_PENDIENTE.getId().equals(mailTipo)) {			
			if ( cuerpo.indexOf("{{cuit}}") == -1 || 
				 cuerpo.indexOf("{{razon_social}}") == -1 ||
				 cuerpo.indexOf("{{periodo}}") == -1 ) {
				return false;
			}		
		}
		
		if ( MailEnum.AVISO_DEUDA.getId().equals(mailTipo)) {
			if ( cuerpo.indexOf("{{cuit}}") == -1 || 
				 cuerpo.indexOf("{{razon_social}}") == -1 ||
				 cuerpo.indexOf("{{capital}}") == -1 || 
				 cuerpo.indexOf("{{interes}}") == -1 ) {
				return false;
			}					
		}
		
		return true;		
	}
	
	@Override
	public String getVariables(Integer mailTipo) {
		if ( MailEnum.DDJJ_PENDIENTE.getId().equals(mailTipo)) {
			return "Variables obligatorias: {{cuit}}, {{razon_social}} y {{periodo}}. Variable opcional: {{login}}";
		}
		if ( MailEnum.AVISO_DEUDA.getId().equals(mailTipo)) {
			return "Variables obligatorias: {{cuit}}, {{razon_social}}, {{capital}} e {{interes}}. Variable opcional: {{login}}, {{linkPdfUOMA}}, {{linkPdfAMTIMA}}, {{linkPdfOSPIM}}";
		}
		return "ERROR";
	}
	
}
