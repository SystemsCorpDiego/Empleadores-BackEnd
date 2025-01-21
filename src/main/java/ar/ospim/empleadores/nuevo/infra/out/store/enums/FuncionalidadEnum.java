package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import lombok.Getter;

@Getter
public enum FuncionalidadEnum {
	FERIADO( "FERIADO", "Tabla Feriados")
	;
 
    private String codigo;
    private String descripcion;
    
    FuncionalidadEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion; 
    }
}
