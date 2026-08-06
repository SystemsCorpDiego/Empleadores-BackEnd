package ar.ospim.empleadores.nuevo.dominio;

import ar.ospim.empleadores.comun.exception.NotFoundException;

public enum MailEnum {

	AVISO_DEUDA(1, "Notificacion de Deuda")   
	;
	
	private Integer id;
	private String descripcion;
	
	
	MailEnum(Integer id, String descripcion) {
	    this.id = id;
	    this.descripcion = descripcion; 
	}
	
	public Integer getId() {
	    return id;
	}
	public String getDescripcion() {
	    return descripcion;
	} 
	
	public static MailEnum map(java.lang.Integer id) {
	    for(MailEnum e : values()) {
	        if(e.id.equals(id)) return e;
	    }
	    throw new NotFoundException("entidad-not-exists", String.format("La entidad %s no existe", id));
	}

}