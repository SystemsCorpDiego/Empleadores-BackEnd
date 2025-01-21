package ar.ospim.empleadores.nuevo.infra.out.store.repository.querys;

import java.math.BigDecimal;

public interface BoletaPagoDDJJEmpleadosConsultaI {

	String getCuil();
	String getApellido();
	String getNombre();
	BigDecimal getRemunerativo();
	BigDecimal getCapital();
    
}
