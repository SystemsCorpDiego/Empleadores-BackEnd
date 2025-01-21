package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import ar.ospim.empleadores.comun.exception.NotFoundException;

public enum EntidadEnum {

	UOMA("UOMA", "Union Obrera Molinera Argentina"),
	AMTIMA("AMTIMA", "Asociación Mutual de los Trabajadores de la Industria Molinera"),
	OSPIM("OSPIM", "Obra Social del Personal de la Industria Molinera"),   
	;

    private String codigo;
    private String descripcion;
    
 
    EntidadEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion; 
    }
 
    public String getCodigo() {
        return codigo;
    }
    public String getDescripcion() {
        return descripcion;
    } 

	public static EntidadEnum map(java.lang.String codigo) {
        for(EntidadEnum e : values()) {
            if(e.codigo.equals(codigo)) return e;
        }
        throw new NotFoundException("entidad-not-exists", String.format("La entidad %s no existe", codigo));
    }
}
