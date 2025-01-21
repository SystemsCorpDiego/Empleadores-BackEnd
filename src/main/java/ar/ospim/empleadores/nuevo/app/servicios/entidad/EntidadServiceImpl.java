package ar.ospim.empleadores.nuevo.app.servicios.entidad;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.EntidadBO;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EntidadEnum;

@Service
public class EntidadServiceImpl implements EntidadService {

	@Override
	public List<EntidadBO> consultar() {
		List<EntidadBO> lst = new ArrayList<EntidadBO>();
		EntidadBO reg = null;
		for (EntidadEnum entidadEnum: EntidadEnum.values()) {
			reg = new EntidadBO() ;
			reg.setCodigo(entidadEnum.getCodigo());
			reg.setDescripcion(entidadEnum.getDescripcion());
			lst.add(reg); 
		}
		return lst;
	}
	
	@Override
	public boolean validarCodigo(String codigo) {
		try {
		       EntidadEnum.valueOf(codigo);
		       return true;
		} catch (IllegalArgumentException e) { 
			return false; 
		}		
	}

}
