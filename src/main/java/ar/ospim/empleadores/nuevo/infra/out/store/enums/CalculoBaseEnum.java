package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import ar.ospim.empleadores.comun.exception.NotFoundException;

public enum CalculoBaseEnum {
	PJ("PJ", "Paritaria Jornal"),
	PS("PS", "Paritaria Salarial"),   
	RE("RE", "Remunerativo")
	;
	
    private String codigo;
    private String descripcion;

    CalculoBaseEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion; 
    }
    
    public String getCodigo() {
        return codigo;
    }
    public String getDescripcion() {
        return descripcion;
    } 

	public static CalculoBaseEnum map(java.lang.String codigo) {
        for(CalculoBaseEnum e : values()) {
            if(e.codigo.equals(codigo)) return e;
        }
        throw new NotFoundException("entidad-not-exists", String.format("La entidad %s no existe", codigo));
    }
	
}
