package ar.ospim.empleadores.nuevo.app.servicios.afiliado.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.cui.CuilUtils;
import ar.ospim.empleadores.nuevo.app.servicios.afiliado.ValidarCUIL;

@Service
public class ValidarCUILImpl implements  ValidarCUIL {

	@Override
	public boolean run(String cuil) {
		if ( cuil == null) {
			return false;
		}
		return CuilUtils.validar(cuil); 
	}

}
