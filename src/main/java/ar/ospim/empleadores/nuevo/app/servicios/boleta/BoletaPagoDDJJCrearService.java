package ar.ospim.empleadores.nuevo.app.servicios.boleta;

import java.util.List;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;

public interface BoletaPagoDDJJCrearService {

	public void run(Integer ddjjId, List<BoletaPagoBO> boletasParam);

}