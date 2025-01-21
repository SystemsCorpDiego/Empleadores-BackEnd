package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.ProvinciaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.ProvinciaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.ProvinciaMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ProvinciaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Provincia;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProvinciaStorageImpl implements ProvinciaStorage {
	 
    private final ProvinciaRepository repository;
    private final ProvinciaMapper mapper;
		
	public List<ProvinciaBO> findAll() {
		List<Provincia> consulta = repository.findAll();
		return mapper.map(consulta); 
	}
	
	public List<ProvinciaBO> findAllOrderByDetalle() {
		List<Provincia> consulta = repository.findAllByOrderByDetalle();
		return mapper.map(consulta);
	}
}
