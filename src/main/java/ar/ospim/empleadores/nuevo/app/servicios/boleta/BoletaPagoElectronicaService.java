package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;

public interface BoletaPagoElectronicaService {
	
	public List<BoletaPagoBO> runAndSave(List<BoletaPagoBO> lstBp);
	public String run(BoletaPagoBO bp);
	public String runAndSave(Integer boletaPagoId);
	
}
