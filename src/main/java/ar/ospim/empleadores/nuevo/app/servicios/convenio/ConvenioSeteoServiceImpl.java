package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioSeteoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ConvenioSeteoServiceImpl implements ConvenioSeteoService {
	
	private final MessageSource messageSource;
	private final ConvenioSeteoStorage storage;
	private final EmpresaService empresaService;
	@Override
	public ConvenioSeteo guardar(ConvenioSeteo reg) {
		if ( reg.getCuit()!=null && "".equals(reg.getCuit().trim()) ) {
			reg.setCuit(null);
		}
		validarVigenciaSolapada(reg);
		validarCuit(reg);
		
		return storage.save(reg);
	}
	
	@Override
	public void borrar(Integer id) {
		storage.deleteById(id);
	}
	
	@Override
	public List<ConvenioSeteo> findVigentes(LocalDate periodo) {
		return storage.getVigentes(periodo);		
	}

	@Override
	public ConvenioSeteo get(Integer id) {
		return storage.get(id);				
	}

	@Override
	public List<ConvenioSeteo> getAll() {
		return storage.getAll();
	}
	
	private void validarCuit(ConvenioSeteo reg) {
		
		empresaService.getEmpresa(reg.getCuit());
		
	}
	
	private void validarVigenciaSolapada(ConvenioSeteo reg) {
		LocalDate vigencia =  reg.getDesde();		
		validarVigenciaSolapada(reg.getCuit(), vigencia, reg.getId());
		vigencia =  reg.getHasta();
		if ( vigencia == null)
			vigencia = LocalDate.now().plusYears(9000);
		validarVigenciaSolapada(reg.getCuit(), vigencia, reg.getId());
	}
	
	private void validarVigenciaSolapada(String cuit, LocalDate vigencia, Integer id) {
		Optional<ConvenioSeteo> cons;
		if ( cuit == null) {
			if ( id != null) {
				cons = storage.findContenido(vigencia, id);
			} else {
				cons = storage.findContenido(vigencia);
			}
		} else {
			if ( id != null) {
				cons = storage.findContenido(cuit, vigencia, id);
			} else {
				cons = storage.findContenido(cuit, vigencia);
			}
		}
		
		if ( cons.isPresent() ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.FECHA_RANGO_EXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.FECHA_RANGO_EXISTENTE.name(), errorMsg);
		}
	}
	
}
