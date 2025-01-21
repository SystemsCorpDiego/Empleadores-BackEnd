package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import lombok.Getter;

@Getter
public enum EmpresaContactoTipoEnum {
	WHATSAP("WhatsApp"),
	MAIL("Mail  Principal"),
	MAIL2("Mail"),
	TEL("Teléfono Principal"),	
	TEL2("Teléfono");
	
    private String descripcion;
    
    private EmpresaContactoTipoEnum(String descripcion) {
        this.descripcion = descripcion ;
    }
    
}
