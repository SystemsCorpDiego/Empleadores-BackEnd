package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.sql.SQLException;
import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import net.sf.jasperreports.engine.JRException;

public interface ConvenioImprimirService {
	 
	public byte[] run(Convenio convenio, List<ConvenioCuotaConsultaDto>  lstCuotas)  throws JRException, SQLException;
	
}
