package ar.ospim.empleadores.nuevo.infra.out.store.enums;

import ar.ospim.empleadores.comun.exception.NotFoundException;

public enum AjusteMotivoEnum {

	AJ("AJ", "Ajuste Retroactivo"),
	DI("DI", "Devolución de Intereses"),
	DPD("DPD", "Devolución por pago duplicado"),   
	IPF("IPF", "Interes por Pago fuera de término"),
	REP("REP", "Reimputación de Pago"),
	O("O", "Otros"),   
	;

    private String codigo;
    private String descripcion;
    
 
    AjusteMotivoEnum(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion; 
    }
 
    public String getCodigo() {
        return codigo;
    }
    public String getDescripcion() {
        return descripcion;
    } 

	public static AjusteMotivoEnum map(java.lang.String codigo) {
        for(AjusteMotivoEnum e : values()) {
            if(e.codigo.equals(codigo)) return e;
        }
        throw new NotFoundException("ajusteMotivo-not-exists", String.format("La entidad %s no existe", codigo));
    }
}
