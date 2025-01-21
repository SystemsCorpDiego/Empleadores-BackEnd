package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.sql.SQLException;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import net.sf.jasperreports.engine.JRException;

public interface BoletaPagoImprimirService {	
	public byte[] run(Integer id)  throws JRException, SQLException ;
	public byte[] run(BoletaPagoBO boleta)  throws JRException, SQLException;
}
