package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.afiliado.AfiliadoEnumException;
import ar.ospim.empleadores.nuevo.infra.out.store.AfiliadoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.AfiliadoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AfiliadoActuRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AfiliadoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Afiliado;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Afiliado.AfiliadoId;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfiliadoActu;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AfiliadoStorageImpl implements AfiliadoStorage {

	private MessageSource messageSource;
	private final UsuarioInfo usuarioInfo;
	
	private final AfiliadoActuRepository actuRepository;
	private final AfiliadoRepository repository;
	private final AfiliadoMapper mapper;
	
	@Override
	public List<AfiliadoBO> findByCuil(String cuil) {
		List<Afiliado> afiliado = repository.findByCuil(cuil);
		return mapper.map( afiliado) ; 
	}
	
	@Override
	public AfiliadoBO save(AfiliadoBO regBO) {
		Afiliado registro;

		if ( regBO.getCuil() == null) {
			String errorMsg = messageSource.getMessage(AfiliadoEnumException.CUIL_OBLIGATORIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(AfiliadoEnumException.CUIL_OBLIGATORIO.name(), errorMsg );
		}
		
		
		AfiliadoId id = new AfiliadoId();
		id.setCuil_titular(regBO.getCuil());
		id.setInte(0);
		Optional<Afiliado> regAux = repository.findById(id); 
		if (regAux.isPresent()) {
			String errorMsg = messageSource.getMessage(AfiliadoEnumException.CUIL_ALTA_EXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(AfiliadoEnumException.CUIL_ALTA_EXISTENTE.name(), String.format(errorMsg, regBO.getCuil()) );
		}
		
		registro = mapper.map(regBO);
		registro.setAlta_fecha(new Date());
		if ( usuarioInfo.getUsuarioLogeadoEmpresaId() != null )
			registro.setAlta_usr( usuarioInfo.getUsuarioLogeadoEmpresaId().toString() );
		
		registro = repository.save(registro);
		
		return mapper.map(registro);		
	}

	@Override
	public AfiliadoActu saveActu(AfiliadoActu reg) {
		// TODO Auto-generated method stub
		AfiliadoActu regNew = actuRepository.save(reg);
		return regNew;
	}
	
	 

}
