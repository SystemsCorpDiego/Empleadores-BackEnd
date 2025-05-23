package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ConvenioStorageImpl implements ConvenioStorage {

	 private final ConvenioRepository repository;
	 
	@Override
	public Convenio guardar(Convenio reg) {
		// TODO Auto-generated method stub

		Convenio regNew = repository.save(reg);
		return regNew;
	}

	@Override
	public Convenio get(Integer id) {
		
		return repository.getById(id);
		
	}

}
