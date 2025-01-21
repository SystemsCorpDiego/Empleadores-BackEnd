package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.infra.input.service.BasicDataEmpresaDto;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.ConsultarEmpresa;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaExceptionEnum;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.usuarioempresaalta.UsuarioEmpresaAltaDtoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EmpresaBoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;

@Service
public class ConsultarEmpresaImpl implements ConsultarEmpresa {
    private static final Logger LOG = LoggerFactory.getLogger(ConsultarEmpresaImpl.class);
    public static final String OUTPUT = "Output -> {}";
	private static final String ONE_INPUT_PARAMETER = "Input parameters -> {}";

	private final MessageSource messageSource;
	private final EmpresaRepository empresaRepository;
	private final EmpresaBoMapper empresaMapper;

	private final  UsuarioEmpresaAltaDtoMapper empresaMapper2;

	public ConsultarEmpresaImpl(EmpresaRepository empresaRepository, EmpresaBoMapper empresaMapper,
			UsuarioEmpresaAltaDtoMapper empresaMapper2, MessageSource messageSource) {
		this.empresaRepository = empresaRepository; 
		this.empresaMapper = empresaMapper; 
		this.empresaMapper2 = empresaMapper2;
		this.messageSource = messageSource;
	}
	
	@Override
	public Optional<EmpresaBO> findEmpresa(Integer id)  {
	    LOG.debug("findEmpresa -> {}", id);
	    Optional<Empresa> consulta = empresaRepository.findById(id);
	    return getEmpresaBO(consulta);
	}
	
	@Override
	public Optional<EmpresaBO> findEmpresa(String cuit)  {
	    LOG.debug("findEmpresa -> {}", cuit);
	    Optional<Empresa> consulta = empresaRepository.findByCuit(cuit);
	    return getEmpresaBO(consulta);
	}
	
	private Optional<EmpresaBO> getEmpresaBO( Optional<Empresa> consulta ) {
		Optional<EmpresaBO> result; 
		EmpresaBO empresaBO = null;
	    if (consulta.isEmpty() ) {
	    	result = Optional.ofNullable(empresaBO);
	    	LOG.debug("Empresa rescatada-> {}", empresaBO);
	    	return result;
	    } 
	    
	    empresaBO = empresaMapper.map(consulta.get());
	    result = Optional.of(empresaBO);
	    LOG.debug("Empresa rescatada-> {}", result);
	    return result;
	}
	
	@Override
	public EmpresaBO getEmpresa(Integer id) {
		return findEmpresa(id).orElseThrow(
				() -> { String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
					      return new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), String.format(errorMsg, id) );		 
					    }
				);
	}

	@Override
	public EmpresaBO getEmpresa(String cuit) {
		return findEmpresa(cuit).orElseThrow(
				() -> { String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CUIT_INEXISTENTE.getMsgKey(), null, new Locale("es"));
						 return new BusinessException(EmpresaExceptionEnum.CUIT_INEXISTENTE.name(), errorMsg );			
						}
				);
	}
	
	@Override
	public BasicDataEmpresaDto getBasicDataEmpresa(Integer empresaId) {
		LOG.debug(ONE_INPUT_PARAMETER, empresaId);
		EmpresaBO empresa =  this.getEmpresa(empresaId);

		BasicDataEmpresaDto result = empresaMapper2.basicDataFromEmpresa(empresa);
		
		LOG.debug(OUTPUT, result);
		return result;
	}

	public List<EmpresaBO> findAll() {
		List<Empresa> cons = empresaRepository.findAll();		 
		return empresaMapper.map(cons) ;
	}
	
}
