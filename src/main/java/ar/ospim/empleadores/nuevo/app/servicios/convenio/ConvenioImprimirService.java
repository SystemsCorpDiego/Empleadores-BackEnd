package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;

public interface ConvenioImprimirService {
	 
	public byte[] run(Integer id)  throws JRException, SQLException;
	
}
