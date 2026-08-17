package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto;

import java.math.BigDecimal;

public interface IMailTipoDeudaInfoDto {

	String getCuit();
	String getEntidad();
	String getMail();
    BigDecimal getCapital();
    BigDecimal getInteres();
    BigDecimal getPago();
    
    
}
