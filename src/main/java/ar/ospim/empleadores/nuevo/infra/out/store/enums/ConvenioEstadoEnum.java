package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import ar.ospim.empleadores.comun.exception.NotFoundException;

public enum ConvenioEstadoEnum {

	PENDIENTE("PENDIENTE", "Pendiente"),
	PRES("PRES", "Presentada"),
	APROB("APROB", "Aprobado"),
	RECH("RECH", "Rechazado"),
	OBSR("OBSR", "Observado"),
	;

    private String codigo;
    private String descripcion;
    
 
    ConvenioEstadoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion; 
    }
 
    public String getCodigo() {
        return codigo;
    }
    public String getDescripcion() {
        return descripcion;
    } 

	public static ConvenioEstadoEnum map(java.lang.String codigo) {
        for(ConvenioEstadoEnum e : values()) {
            if(e.codigo.equals(codigo)) return e;
        }
        throw new NotFoundException("entidad-not-exists", String.format("El Estado %s no existe", codigo));
    }
	
}
