package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import ar.ospim.empleadores.comun.exception.NotFoundException;

public enum ERol {

	ROOT(1, "ROOT", ERoleLevel.LEVEL0),
    ADMINISTRADOR(2, "ADMINISTRADOR", ERoleLevel.LEVEL0),
    AUTENTICACION_PARCIAL(3, "AUTENTICACION_PARCIAL", ERoleLevel.LEVEL0),
    EMPLEADOR(4, "EMPLEADOR", ERoleLevel.LEVEL1),
    
    //OSPIM_EMPLEADO(3, "OSPIM_EMPLEADO", ERoleLevel.LEVEL1),
    //API_CONSUMER(5, "API_CONSUMER", ERoleLevel.LEVEL0),
	;

    private Short id;
    private String value;
    private ERoleLevel level;
 
    ERol(Number id, String value, ERoleLevel level) {
        this.id = id.shortValue();
        this.value = value;
        this.level = level;
    }
 
    public String getValue() {
        return value;
    }
    public Short getId() {
        return id;
    }
    public ERoleLevel getLevel() {
		return level;
	}

	public static ERol map(java.lang.Short id) {
        for(ERol e : values()) {
            if(e.id.equals(id)) return e;
        }
        throw new NotFoundException("role-not-exists", String.format("El rol %s no existe", id));
    }
}
