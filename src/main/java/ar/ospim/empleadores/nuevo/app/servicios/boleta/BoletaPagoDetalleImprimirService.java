package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;

public interface BoletaPagoDetalleImprimirService {

	public byte[] run(Integer id)  throws JRException, SQLException ;
	
}
