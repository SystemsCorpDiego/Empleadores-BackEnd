package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJEmpleadoAporteStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DDJJEmpleadoAporteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DDJJEmpleadoAporteStorageImpl implements DDJJEmpleadoAporteStorage {

	private final DDJJEmpleadoAporteRepository repository;
	
	@Transactional
	@Override
	public void borrarTodos(DDJJEmpleadoBO reg) {
		repository.deleteByDDJJEmpleadoId(reg.getId());
	}	

}
