package ar.ospim.empleadores.nuevo.app.servicios.deuda.infomail;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.out.store.DeudaNominaMailConfigStorage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeudaNotifMailGetFechaProcesoVigenteServiceImpl implements DeudaNotifMailGetFechaProcesoVigenteService {

	private final DeudaNominaMailConfigStorage storage;
	
	@Override
	public Optional<LocalDate> run() {
		return storage.getFechaProcesoVigente();
	}
	
}
