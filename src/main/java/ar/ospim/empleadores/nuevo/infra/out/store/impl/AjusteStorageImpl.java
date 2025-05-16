package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.IGestionDeudaAjustes;
import ar.ospim.empleadores.nuevo.infra.out.store.AjusteStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.AjusteMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AjusteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Ajuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Aporte;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AjusteStorageImpl  implements AjusteStorage {
	private MessageSource messageSource;
	
    private final AjusteRepository repository;
    private final AjusteMapper mapper; 
	
	public AjusteStorageImpl(MessageSource messageSource, AjusteRepository ajusteRepository, AjusteMapper mapper) {
        this.	messageSource = messageSource;			
        this.repository = ajusteRepository;
        this.mapper = mapper; 
    }
	
	public AjusteBO save(AjusteBO regBO) {
		Ajuste registro;

		if ( regBO.getId() != null) {
			try {			
				registro = repository.getById(regBO.getId());
				registro.setAporte( new Aporte() );
				registro.setEmpresa( new Empresa() );
				mapper.map(registro, regBO);
			} catch( EntityNotFoundException enf) {
				String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
						String.format(errorMsg, regBO.getId())  );
			} 			 
		} else {
			registro = mapper.map(regBO);
		}
	    
		registro = repository.save(registro);
	
		return mapper.map(registro);
	}
	
	public void deleteById(Integer id) {
		repository.deleteById( id );
	}
	
	public AjusteBO findById(Integer id) {
		AjusteBO reg = null;
		Optional<Ajuste> cons = repository.findById( id );
		if (cons.isPresent() )
			reg = mapper.map(cons.get()); 
		
		return reg;
	}

	public List<AjusteBO> findAll() {
		List<Ajuste> consulta = repository.findAll();
		return mapper.map(consulta); 
	}
	
	public List<AjusteBO> findAllCrud() {
		List<Ajuste> consulta = repository.consultarCrud();
		return mapper.map(consulta); 
	}
	
	public List<AjusteBO> findCrudByCuit(String cuit) {
		List<Ajuste> consulta = repository.consultarCrudPorCuit(cuit);
		return mapper.map(consulta); 
	}
	
	public List<AjusteBO> consultarAportesVigentes(Integer empresaId, String aporte, LocalDate vigencia) {
		List<Ajuste> consulta = repository.consultarAportesVigentes(empresaId, aporte, vigencia);
		return mapper.map(consulta); 
	}
 	
	public BigDecimal getImporteUsado(Integer aporteId) {
		BigDecimal importe = repository.importeUsado(aporteId);
		return importe; 
	}
	
	public void generarAjusteAutomaticoIPF(String p_aporte, Integer p_empresa_id) {
		repository.generarAjusteAutomaticoIPF22(p_aporte, p_empresa_id);		
	}
	
	public List<IGestionDeudaAjustes> getGestionDeudaAjustes(Integer empresaId, String entidad) {
		return repository.getGestionDeudaAjustes(empresaId, entidad);
	}
	
}
