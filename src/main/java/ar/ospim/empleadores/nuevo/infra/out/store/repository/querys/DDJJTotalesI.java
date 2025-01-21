package ar.ospim.empleadores.nuevo.infra.out.store.repository.querys;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DDJJTotalesI {
	Integer getId();
	String getEstado();
	LocalDate getPresentada();
	Integer getEmpresa_id();  
	String getCuit();  
	String getRazon_Social();
	String getDdjjVigente();
	LocalDate getPeriodo();
	Integer getSecuencia();
	String getAporte();
	BigDecimal getImporte();
}
