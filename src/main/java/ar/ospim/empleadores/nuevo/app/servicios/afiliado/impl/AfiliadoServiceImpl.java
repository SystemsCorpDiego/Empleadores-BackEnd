package ar.ospim.empleadores.nuevo.app.servicios.afiliado.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.afiliado.AfiliadoService;
import ar.ospim.empleadores.nuevo.infra.out.store.AfiliadoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfiliadoActu;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AfiliadoServiceImpl implements AfiliadoService {

	private final AfiliadoStorage storage;
	
	@Override
	public List<AfiliadoBO> get(String cuil) {
		return storage.findByCuil(cuil);
	}
	
	@Override
	public AfiliadoBO registrar(AfiliadoBO reg) {
		return storage.save(reg);
	}

	@Override
	public AfiliadoActu saveActu(AfiliadoActu reg) {
		return storage.saveActu(reg);
	}
	
}
