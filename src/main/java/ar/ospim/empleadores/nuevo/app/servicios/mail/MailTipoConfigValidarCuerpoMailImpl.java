package ar.ospim.empleadores.nuevo.app.servicios.mail;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.dominio.MailEnum;

@Service
public class MailTipoConfigValidarCuerpoMailImpl implements MailTipoConfigValidarCuerpoMail {

	
	@Override
	public boolean run(Integer mailTipo, String asunto, String cuerpo) {

		if ( MailEnum.DDJJ_PENDIENTE.getId().equals(mailTipo)) {
			if ( !contiene(asunto, cuerpo, "{{cuit}}") ||
				 !contiene(asunto, cuerpo, "{{razon_social}}") ||
				 !contiene(asunto, cuerpo, "{{periodo}}") ) {
				return false;
			}
		}

		if ( MailEnum.AVISO_DEUDA.getId().equals(mailTipo)) {
			if ( !contiene(asunto, cuerpo, "{{cuit}}") ||
				 !contiene(asunto, cuerpo, "{{razon_social}}") ||
				 !contiene(asunto, cuerpo, "{{capital}}") ||
				 !contiene(asunto, cuerpo, "{{interes}}") ) {
				return false;
			}
		}

		return true;
	}

	private boolean contiene(String asunto, String cuerpo, String variable) {
		return asunto.indexOf(variable) != -1 || cuerpo.indexOf(variable) != -1;
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
