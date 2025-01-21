package ar.ospim.empleadores.nuevo.app.servicios.aporte;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.out.store.enums.CalculoBaseEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.CalculoTipoEnum;


@Service
public class CalculoServiceImpl implements CalculoService {
	
	@Override
	public boolean validarTipo(String codigo) {
		try {
			CalculoTipoEnum.valueOf(codigo);
		    return true;
		} catch (IllegalArgumentException e) { 
			return false; 
		}		
	}
	
	@Override
	public boolean validarBase(String codigo) {
		try {
			CalculoBaseEnum.valueOf(codigo);
		    return true;
		} catch (IllegalArgumentException e) { 
			return false; 
		}		
	}
	
}
