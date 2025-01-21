package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraCategoriaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.CamaraCategoriaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.CamaraCategoriaMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.CamaraCategoriaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.VCamaraCategoria;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CamaraCategoriaStorageImpl implements CamaraCategoriaStorage {
	
	private final CamaraCategoriaRepository repository;
	private final CamaraCategoriaMapper mapper;
	
	@Override
	public List<CamaraCategoriaBO> findAll() {
		// TODO Auto-generated method stub
		
		List<VCamaraCategoria> consulta = repository.findAll();
		return mapper.map(consulta); 		 
	}
	
	public Boolean validar(String camara, String categoria) {
		List<VCamaraCategoria> consulta =  repository.findAll();
		for ( VCamaraCategoria reg: consulta) {
			if ( reg.getPk().getCamara().equals(camara) 
					&& reg.getPk().getCategoria().equals(categoria)) {
				return true;
			}
		}
		return false;
	}

}
