package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import lombok.Getter;

@Getter
public enum FormaPagoEnum {
	VENTANILLA( "VENTANILLA", "Ventanilla"),
	REDLINK( "REDLINK", "Red Link"),
	BANELCO( "BANELCO", "Banelco"),			//Es lo mismo que PMCUENTAS !!!
	PMCUENTAS( "PMCUENTAS", "PagoMisCuentas"),
	CHEQUE( "CHEQUE", "Cheque"),
	;

	private String codigo;
    private String descripcion;
    
    FormaPagoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion; 
    }
}
