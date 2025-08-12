package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AporteSeteoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.AporteSeteoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.AporteSeteoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AporteSeteoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteSeteo;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.AporteSeteoVigenteConsultaI;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AporteSeteoStorageImpl implements AporteSeteoStorage {

	private MessageSource messageSource;
	
	private final AporteSeteoRepository repository;
	private final AporteSeteoMapper mapper;
	
	public AporteSeteoBO findVigente(String codigo, LocalDate periodo) {
		return new AporteSeteoBO();
	}

	@Override
	public List<AporteSeteo> getVigentes(LocalDate periodo) {
		List<AporteSeteoVigenteConsultaI> cons = repository.getVigentes(periodo);		
		return mapper.map(cons) ;
	}
	
	public AporteSeteoBO save(AporteSeteoBO regBO) {
		AporteSeteo registro;

		if ( regBO.getId() != null ) {
			Optional<AporteSeteo> regAux = repository.findById(regBO.getId());
			if (regAux.isEmpty()) {
				String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
						String.format(errorMsg, regBO.getId())	);					
			}
		}
		 
		registro = mapper.map(regBO);
		 
		registro = repository.save(registro);
		
		return mapper.map(registro);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById( id );
	}

	@Override
	public Optional<AporteSeteo> findContenido(String entidad, String aporte, LocalDate desde) {
		Optional<AporteSeteo> reg = repository.findContenido( entidad, aporte, desde);
		return reg;
	}
	
	@Override
	public Optional<AporteSeteo> findContenido(String entidad, String aporte, LocalDate desde, Integer id) {
		Optional<AporteSeteo> reg = repository.findContenido( entidad, aporte, desde, id);
		return reg;
	}
}
