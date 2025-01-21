package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import lombok.Getter;

@Getter
public enum DDJJEstadoEnum {
	PENDIENTE("PE"),
	PRESENTADA("PR"),
	BOLETA_GENERADA("BG"), 
	NO_VIGENTE("NV");
	
    private String codigo;
    
    private DDJJEstadoEnum(String descripcion) {
        this.codigo = descripcion ;
    }

}
