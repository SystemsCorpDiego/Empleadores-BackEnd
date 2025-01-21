package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJEmpleadoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DDJJEmpleadoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DDJJEmpleadoStorageImpl implements DDJJEmpleadoStorage {

	private final DDJJEmpleadoRepository repository;
	
	@Transactional
	@Override
	public void borrar(DDJJEmpleadoBO reg) {
		repository.deleteById(reg.getId());
	}

}
