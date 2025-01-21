package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import lombok.Getter;

@Getter
public enum EmpresaExceptionEnum {
	EMPRESA_NULL,
    ERROR,
    CUIT,
    CUIT_FORMAT,
    DOMICILIO,
    DOMICILIO_PLANTA,
    DOMICILIO_FISCAL,
    DOMICILIO_FISCAL_2,
    DOMICILIO_FISCAL_3,
    CONTACTO,
    CONTACTO_DUPLICADO,
    CONTACTO_WHATSAP,
    CONTACTO_WHATSAP_2,
    CONTACTO_MAIL,
    CONTACTO_MAIL_2,
    CONTACTO_MAIL_PREFIJO, 
   // CONTACTO_TEL,
    CONTACTO_TEL_2,
    CONTACTO_TEL_DUPLICADO,
    CONTACTO_TEL_TIPO_MODI,
    CONTACTO_MAIL_DUPLICADO,
    CONTACTO_MAIL_TIPO_MODI,
    CONTACTO_TEL_WHASAP_LENGTH,
    CONTACTO_PPAL_BAJA,
    CUIT_INEXISTENTE
    ;
	
	public String getMsgKey() {
		return "empresa.val." + this.name();
	}	

}
