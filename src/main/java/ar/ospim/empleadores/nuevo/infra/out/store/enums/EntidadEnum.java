package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import ar.ospim.empleadores.comun.exception.NotFoundException;

public enum EntidadEnum {

	UOMA("UOMA", "Union Obrera Molinera Argentina", "30531143856"),
	AMTIMA("AMTIMA", "Asociación Mutual de los Trabajadores de la Industria Molinera", "30604119568"),
	OSPIM("OSPIM", "Obra Social del Personal de la Industria Molinera", "30629138567"),   
	;

    private String codigo;
    private String descripcion;
    private String cuit;
    
 
    EntidadEnum(String codigo, String descripcion, String cuit) {
        this.codigo = codigo;
        this.descripcion = descripcion; 
        this.cuit = cuit;
    }
 
    public String getCodigo() {
        return codigo;
    }
    public String getDescripcion() {
        return descripcion;
    } 
    public String getCuit() {
        return cuit;
    }

	public static EntidadEnum map(java.lang.String codigo) {
        for(EntidadEnum e : values()) {
            if(e.codigo.equals(codigo)) return e;
        }
        throw new NotFoundException("entidad-not-exists", String.format("La entidad %s no existe", codigo));
    }
}
