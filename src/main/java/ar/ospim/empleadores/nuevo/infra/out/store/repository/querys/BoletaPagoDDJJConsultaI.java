package ar.ospim.empleadores.nuevo.infra.out.store.repository.querys;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface BoletaPagoDDJJConsultaI {
	Integer getId(); 
	
	Integer getEmpresaId();  
	String getCuit(); //Concepto
	String getRazonSocial(); 
	
	String getAporte(); //Concepto
    String getAporteDescripcion(); //Concepto descrip

    LocalDate getPeriodo(); 
    BigDecimal getImporte();
    BigDecimal getInteres();
    BigDecimal getAjuste();
    LocalDate getIntencionDePago();
    LocalDate getVencimiento();
    LocalDate getFechaDePago();
    BigDecimal getImporteRecibido();

    String getFormaDePago(); //Descripcion de FormaPago 
    String getBep(); 
    Integer getSecuenciaDdjj(); //"Original",
    Integer getSecuenciaBoleta();
    Integer getDdjjId();
    LocalDate getBaja();

}
