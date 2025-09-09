package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BancoMovimientoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.AporteStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.AporteMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AporteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Aporte;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BancoConvenioMovimiento;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AporteStorageImpl implements AporteStorage {
	
	private MessageSource messageSource;
	
    private final AporteRepository repository;
    private final AporteMapper mapper;
	
	public AporteStorageImpl(MessageSource messageSource, AporteRepository aporteRepository, AporteMapper mapper) {
        this.	messageSource = messageSource;			
        this.repository = aporteRepository;
        this.mapper = mapper;
    }
	
	public AporteBO save(AporteBO regBO) {
		Aporte registro;

		if ( regBO.getCodigo() == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.CODIGO_NO_INFORMADO.name(), errorMsg + " [id= " + regBO.getCodigo() + "]" );
		}
			
		Optional<Aporte> regAux = repository.getByCodigo(regBO.getCodigo());
		if (regAux.isPresent()) 
			mapper.map(regBO, regAux.get());
		
		registro = mapper.map(regBO);
		
		registro = repository.save(registro);
		
		return mapper.map(registro);
	}
	
	public void deleteByCodigo(String codigo) {
		repository.deleteByCodigo( codigo );
	}
	
	public AporteBO findByCodigo(String codigo) {
		AporteBO aporte = null;
		Optional<Aporte> cons = repository.getByCodigo( codigo );
		if (cons.isPresent() )
			aporte = mapper.map(cons.get()); 
		
		return aporte;
	}

	public AporteBO findAporteActaByEntidad(String entidad) {
		AporteBO aporte = null;
															      
		Optional<Aporte> cons = repository.getByEntidadAndDdjjAndCodigoNot( entidad, false, "OSPIM");
		if (cons.isPresent() )
			aporte = mapper.map(cons.get()); 
		
		return aporte;
	}

	public List<AporteBO> findAllByOrderByOrdenAsc() {
		List<Aporte> cons =  repository.findAllByOrderByOrdenAsc();
		return mapper.map(cons);		
	}
	
	public List<AporteBO> findAll() {
		List<Aporte> consulta = repository.findAllByOrderByOrdenAsc();
		return mapper.map(consulta); 
	}

	public List<AporteBO> findByDdjj() {
		List<Aporte> consulta = repository.findByDdjjTrueOrderByOrdenAsc();
		return mapper.map(consulta); 
	}
	
	@Override
	public List<AporteBO> getVigentes(LocalDate periodo) {
		List<Aporte> consulta = repository.getVigentes(periodo);
		return mapper.map(consulta); 
	}

	public BancoMovimientoBO findMovimientoByAporte(String codigo) {
		BancoConvenioMovimiento bancocm = repository.getMovimientoBancario(codigo);
		return mapper.map(bancocm);
	}
}
