package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;

public interface DDJJImprimirService {

	public byte[] run(Integer id)  throws JRException, SQLException ;
	public byte[] run2(Integer id)  throws JRException, SQLException ;
	
}
