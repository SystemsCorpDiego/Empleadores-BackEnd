package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraBO;
import ar.ospim.empleadores.nuevo.infra.out.store.CamaraStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.CamaraMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.CamaraRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VCamara;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CamaraStorageImpl implements CamaraStorage {
	
	private final CamaraRepository repository;
	private final CamaraMapper mapper;
	
	@Override
	public List<CamaraBO> findAll() {
		List<VCamara> consulta = repository.findAll();
		return mapper.map(consulta); 		 
	}

	@Override
	public Optional<CamaraBO> findByCodigo(String codigo) {
		Optional<VCamara> reg = repository.findByCodigo(codigo);
		if ( reg.isPresent() )
			return Optional.of(mapper.map(reg.get()) );		
		return Optional.empty();
	}
	
}
