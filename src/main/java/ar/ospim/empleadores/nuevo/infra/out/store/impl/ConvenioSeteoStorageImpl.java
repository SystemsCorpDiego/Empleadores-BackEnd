package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioSeteoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioSeteoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioSeteoStorageImpl implements ConvenioSeteoStorage {
	 
	private final ConvenioSeteoRepository repository;

	@Override
	public ConvenioSeteo save(ConvenioSeteo aporte) {
		return repository.save(aporte);		
	}

	@Override
	public void deleteById(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public ConvenioSeteo findVigente(String codigo, LocalDate periodo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConvenioSeteo> getVigentes(LocalDate periodo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConvenioSeteo get(Integer id) {
		return repository.getById(id);
	}

	@Override
	public List<ConvenioSeteo> getAll() {		
		return repository.findAll();
	}

	@Override
	public Optional<ConvenioSeteo> findContenido(String cuit, LocalDate desde) {
		return  repository.findContenido(cuit, desde);
	}

	@Override
	public Optional<ConvenioSeteo> findContenido(String cuit, LocalDate desde, Integer id) {
		return  repository.findContenido(cuit, desde, id);
	}

	@Override
	public Optional<ConvenioSeteo> findContenido(LocalDate desde) {
		return repository.findContenido(desde);
	}

	@Override
	public Optional<ConvenioSeteo> findContenido(LocalDate desde, Integer id) {
		return repository.findContenido(desde, id);
	}

	@Override
	public Optional<ConvenioSeteo> findContenidoGeneral(LocalDate desde) {
		return repository.findContenidoGeneral(desde);		
	}
	
}
