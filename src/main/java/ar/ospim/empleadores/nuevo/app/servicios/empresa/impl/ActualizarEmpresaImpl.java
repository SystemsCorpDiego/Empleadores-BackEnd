package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.ActualizarEmpresa;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaStorage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActualizarEmpresaImpl implements ActualizarEmpresa {
	
	private static final Logger LOG = LoggerFactory.getLogger(ActualizarEmpresaImpl.class);
	private final EmpresaStorage storage;
	
	@Override
	public EmpresaBO run(EmpresaBO empresa) {

		if (empresa.getRazonSocial()!=null)
			empresa.setRazonSocial( empresa.getRazonSocial().toUpperCase() );

		return  storage.save(empresa);
		
	}
	
}
