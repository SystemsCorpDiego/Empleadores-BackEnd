package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;

public interface IMailTipoDeudaInfoDto {

	String getCuit();
	String getRazon_social();
	String getEntidad();
	String getMail();
    BigDecimal getCapital();
    BigDecimal getInteres();
    BigDecimal getPago();
    
    
}
