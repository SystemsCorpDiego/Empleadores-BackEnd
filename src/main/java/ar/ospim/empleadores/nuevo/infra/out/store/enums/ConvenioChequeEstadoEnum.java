package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import ar.ospim.empleadores.comun.exception.NotFoundException;

public enum ConvenioChequeEstadoEnum {

	PENDIENTE("PENDIENTE", "Pendiente"),
	CARGADO("CARGADO", "Cargado"),   
	RECIBIDO("RECIBIDO", "Recibido"),
	RECHAZADO("RECHAZADO", "Rechazado")
	;
	
    private String codigo;
    private String descripcion;

    ConvenioChequeEstadoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion; 
    }
    
    public String getCodigo() {
        return codigo;
    }
    public String getDescripcion() {
        return descripcion;
    } 

	public static ConvenioChequeEstadoEnum map(java.lang.String codigo) {
        for(ConvenioChequeEstadoEnum e : values()) {
            if(e.codigo.equals(codigo)) return e;
        }
        throw new NotFoundException("entidad-not-exists", String.format("El estado %s no existe", codigo));
    }
	
}
