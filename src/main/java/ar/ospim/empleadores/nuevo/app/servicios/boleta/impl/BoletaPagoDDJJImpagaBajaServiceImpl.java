package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJImpagaBajaService;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoletaPagoDDJJImpagaBajaServiceImpl implements BoletaPagoDDJJImpagaBajaService {
	
	private final BoletaPagoStorage boletaPagoStorage;
	
	@Override
	public void run(Integer empresaId, LocalDate periodo) { 
		
		//TODO: hay que agregar join a pagos para ver q no este paga la boleta.-
		List<Integer> lst = boletaPagoStorage.findByEmpresaIdAndPeriodoImpaga(empresaId, periodo);
		for ( Integer id : lst) {
			boletaPagoStorage.registrarBaja(id);
		}
	}

}
