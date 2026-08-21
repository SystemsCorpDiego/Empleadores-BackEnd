package ar.ospim.empleadores.nuevo.app.servicios.deuda;

import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;

public interface DeudaImprimirService {

	public byte[] run(Integer empresaId, String entidad) throws JRException, SQLException;

}
