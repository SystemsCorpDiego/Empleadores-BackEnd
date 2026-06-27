package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.DeudaNominaMailConfigBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.DeudaNominaMailConfigService;
import ar.ospim.empleadores.nuevo.infra.out.store.DeudaNominaMailConfigStorage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeudaNominaMailConfigServiceImpl implements DeudaNominaMailConfigService {

	private final DeudaNominaMailConfigStorage storage;

	@Override
	public DeudaNominaMailConfigBO consultar() {
		return storage.findFirst();
	}

	@Override
	public DeudaNominaMailConfigBO crear(DeudaNominaMailConfigBO reg) {
		return storage.save(reg);
	}

	@Override
	public DeudaNominaMailConfigBO actualizar(Long id, DeudaNominaMailConfigBO reg) {
		reg.setId(id);
		return storage.save(reg);
	}

}
