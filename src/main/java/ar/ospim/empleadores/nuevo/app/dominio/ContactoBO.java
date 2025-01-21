package ar.ospim.empleadores.nuevo.app.dominio;

import org.apache.commons.lang3.StringUtils;

import ar.ospim.empleadores.comun.strings.StringHelper;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EmpresaContactoTipoEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContactoBO {
    private Integer id;

    private EmpresaContactoTipoEnum tipo;
    private String prefijo;
    private String valor;
    
    private Integer empresaDomicilioId;
    
    public boolean esMailPpal() {
    	return this.tipo.equals(EmpresaContactoTipoEnum.MAIL);
    }
    
    public boolean esWhatsap() {
    	return this.tipo.equals(EmpresaContactoTipoEnum.WHATSAP);
    }
    
    public boolean esTipoMail() {
		if ( this.tipo.equals(EmpresaContactoTipoEnum.MAIL) 
				|| this.tipo.equals(EmpresaContactoTipoEnum.MAIL2) ) {
			return true;
		}
		return false;
    }
    
    public boolean esTelefonoPpal() {
    	return this.tipo.equals(EmpresaContactoTipoEnum.TEL);
    }
    
    public boolean esTipoTelefono() {
		if (this.tipo.equals(EmpresaContactoTipoEnum.TEL) || this.tipo.equals(EmpresaContactoTipoEnum.TEL2)
				|| this.tipo.equals(EmpresaContactoTipoEnum.WHATSAP) ) {
			return true;
		}
		return false;
    }
    	
	public boolean telefValido() {
		if ( esTipoTelefono() ) {
			if ( StringHelper.isNullOrWhiteSpace(prefijo) ) {
				return false;
			}
			if ( StringHelper.isNullOrWhiteSpace(valor) ) {
				return false;
			}
			if ( !StringUtils.isNumeric(prefijo) ) { 
				return false;
			}
			if ( !StringUtils.isNumeric(valor) ) { 
				return false;
			}
			if ( prefijo.length()+valor.length() != 10) {
				return false;
			}
		} 
		return true;
	}
	
	public boolean mailValido() {
		if ( esTipoMail() ) {
			if ( !StringHelper.isNullOrWhiteSpace(prefijo) ) {
				return false;
			}
			if ( StringHelper.isNullOrWhiteSpace(valor) ) {
				return false;
			}
		}
		return true;
	}
	
	
    public void setTipo(String valor) {
    	switch (valor) {
        case "WHATSAP":
            this.tipo = EmpresaContactoTipoEnum.WHATSAP;
            break;
        case "MAIL":
            this.tipo = EmpresaContactoTipoEnum.MAIL;
            break;
        case "MAIL2":
            this.tipo = EmpresaContactoTipoEnum.MAIL2;
            break;
        case "TEL":
            this.tipo = EmpresaContactoTipoEnum.TEL;
            break;
        case "TEL2":
            this.tipo = EmpresaContactoTipoEnum.TEL2;
            break;
        default:
            this.tipo = EmpresaContactoTipoEnum.TEL;
        }	
    }
}
