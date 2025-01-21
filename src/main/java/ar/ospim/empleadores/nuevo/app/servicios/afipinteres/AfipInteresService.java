package ar.ospim.empleadores.nuevo.app.servicios.afipinteres;

import java.time.LocalDate;
import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.AfipInteresBO;

public interface AfipInteresService {

	public List<AfipInteresBO> consultar();   

	public AfipInteresBO guardar(AfipInteresBO reg);	
	
	public void borrar(Integer id);	

	public AfipInteresBO getContenido(LocalDate desde);
	
}
