package ar.ospim.empleadores.nuevo.app.servicios.aporte;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.infra.out.store.AporteStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AporteServiceImpl implements AporteService {
	
	private final MessageSource messageSource;
	private final  AporteStorage storage;

	@Override
	public AporteBO guardar(AporteBO feriado) {		
		return storage.save(feriado);
	}
	
	public void borrar(String  codigo) {
		if (codigo == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg);
		}
		
		storage.deleteByCodigo(codigo);
	}
	
	public List<AporteBO> consultar() {
		return storage.findAll();
	}

	public List<AporteBO> consultarDDJJ() {
		return storage.findByDdjj();
	}
	
	public List<AporteBO> consultarOrderByOrdenAsc() {
		return storage.findAllByOrderByOrdenAsc();
	}
	
	public AporteBO findByCodigo(String codigo) {
		return storage.findByCodigo(codigo);
	}
	
	public AporteBO getAporteBoletaActa(String entidad) {
		return storage.findAporteActaByEntidad(entidad);
	}

}
