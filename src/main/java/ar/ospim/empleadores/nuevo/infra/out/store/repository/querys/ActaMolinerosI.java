package ar.ospim.empleadores.nuevo.infra.out.store.repository.querys;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ActaMolinerosI {

    public Integer getId();
    public String getNumero();
    public String getEntidad();
	public String getCuit();
	public String getEstado();
	public LocalDate getFecha();	
	public BigDecimal getCapital();
	public BigDecimal getInteres();	
	public BigDecimal getInteres_Empleadores();	
	public Integer getConvenio_id();
	public BigDecimal getOtros();
    public BigDecimal getPago();
    public String getPeriodos();
    
}
