package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import ar.ospim.empleadores.comun.exception.NotFoundException;

public enum CalculoTipoEnum {
	PO("PO", "Porcentaje"),
	EN("EN", "Entero"),   
	;
	
    private String codigo;
    private String descripcion;

    CalculoTipoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion; 
    }
    
    public String getCodigo() {
        return codigo;
    }
    public String getDescripcion() {
        return descripcion;
    } 

	public static CalculoTipoEnum map(java.lang.String codigo) {
        for(CalculoTipoEnum e : values()) {
            if(e.codigo.equals(codigo)) return e;
        }
        throw new NotFoundException("entidad-not-exists", String.format("La entidad %s no existe", codigo));
    }
	
	
}
