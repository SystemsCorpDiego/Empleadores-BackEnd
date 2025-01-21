package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import lombok.Getter;

@Getter
public enum EmpresaDomicilioTipoEnum {
	REAL("Planta"),
	FISCAL("Fiscal");
	
    private String descripcion;
    
    private EmpresaDomicilioTipoEnum(String descripcion) {
        this.descripcion = descripcion ;
    }

}
