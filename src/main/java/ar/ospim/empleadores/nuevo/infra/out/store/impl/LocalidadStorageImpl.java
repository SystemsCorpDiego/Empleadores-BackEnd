package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.LocalidadBO;
import ar.ospim.empleadores.nuevo.infra.out.store.LocalidadStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.LocalidadMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.LocalidadRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Localidad;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LocalidadStorageImpl implements LocalidadStorage {
    
	private final LocalidadRepository repository;
    private final LocalidadMapper mapper;

	@Override
	public List<LocalidadBO> findAll() {
		List<Localidad> consulta = repository.findAll();
		return mapper.map(consulta); 
	}

	@Override
	public List<LocalidadBO> findAllOrderByDescripcion() {
		List<Localidad> consulta = repository.findAllByOrderByDetalleAsc();
		return mapper.map(consulta); 
	}

	@Override
	public List<LocalidadBO> findByProvinciaId(Integer provinciaId) {
		List<Localidad> consulta = repository.findByProvinciaId(provinciaId);
		return mapper.map(consulta); 
	}
	
	@Override
	public List<LocalidadBO> findByProvinciaIdOrderByDescripcion(Integer provinciaId) {
		List<Localidad> consulta = repository.findByProvinciaIdOrderByDetalleAsc(provinciaId);
		return mapper.map(consulta); 
	}

}
