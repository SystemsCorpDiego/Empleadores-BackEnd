package ar.ospim.empleadores.nuevo.app.servicios.camara;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.CamaraBO;
import ar.ospim.empleadores.nuevo.app.dominio.CamaraCategoriaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.CamaraCategoriaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.CamaraStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CamaraServiceImpl implements  CamaraService {

	private final CamaraStorage camaraStorage;
	private final CamaraCategoriaStorage camaraCategoriaStorage;
	
	@Override
	public List<CamaraBO> getAllCamara() {
		return camaraStorage.findAll();
	}

	@Override
	public List<CamaraCategoriaBO> getAllCategoria() {
		return camaraCategoriaStorage.findAll();
	}

	@Override
	public Boolean validar(String codigo) {
		Optional<CamaraBO>  reg = camaraStorage.findByCodigo(codigo);
		return reg.isPresent();
	}
	
	@Override
	public Boolean validarCategoria(String camara, String categoria) {
		return camaraCategoriaStorage.validar(camara, categoria);
	}
	
}
