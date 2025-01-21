package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.DomicilioTipoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaDomicilioService;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaDomicilioValidar;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaExceptionEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaDomicilioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EmpresaDomicilioTipoEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaDomicilioServiceImpl implements EmpresaDomicilioService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpresaDomicilioServiceImpl.class);
    public static final String OUTPUT = "Output -> {}";

    private final MessageSource messageSource;
    private final EmpresaStorage empresaStorage;
    private final EmpresaDomicilioStorage storage;
    private final EmpresaDomicilioValidar validador;

    @Override
    public EmpresaDomicilioBO guardar(Integer empresaId, EmpresaDomicilioBO domicilio) {
        LOG.debug("Vamos a guardar -> {}", domicilio);
                
        Optional<EmpresaBO> regEmpreBo = empresaStorage.findById(empresaId);
        if ( regEmpreBo.isEmpty() ) {
        	String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
				String.format(errorMsg, empresaId)	);
        }
        
        List<EmpresaDomicilioBO> cons = storage.findByEmpresaId(empresaId);
        validador.run(cons, domicilio);
        
       
        //Uppercase de atributos
        if ( domicilio.getCalle() != null ) {
        	domicilio.setCalle(domicilio.getCalle().toUpperCase()); 
        }
        if ( domicilio.getDepto() != null ) {
        	domicilio.setDepto(domicilio.getDepto().toUpperCase()); 
        }
        if ( domicilio.getPlanta() != null ) {
        	domicilio.setPlanta(domicilio.getPlanta().toUpperCase()); 
        }
        
        EmpresaDomicilioBO empresaDomicilioBO = storage.save(empresaId, domicilio);
        
        LOG.debug("Empresa guardada -> {}", empresaDomicilioBO);
        return empresaDomicilioBO;
    }

    @Override
    public List<EmpresaDomicilioBO> consultar(Integer id) {
		return storage.findAll(id);
	}
    
    @Override
    public List<EmpresaDomicilioBO> consultarTipoReales(Integer empresaId) {
    	return storage.findByEmpresaIdAndTipo(empresaId, EmpresaDomicilioTipoEnum.REAL.toString() );
    }
    
    @Override
    public List<DomicilioTipoBO> consultarTipos() {
    	
    	List<DomicilioTipoBO> lst = new ArrayList<DomicilioTipoBO>();
    	DomicilioTipoBO reg = new DomicilioTipoBO();
    	reg.setCodigo(EmpresaDomicilioTipoEnum.FISCAL.name());
    	reg.setDescripcion(EmpresaDomicilioTipoEnum.FISCAL.getDescripcion());
    	lst.add(reg);
    	
    	reg = new DomicilioTipoBO();
    	reg.setCodigo(EmpresaDomicilioTipoEnum.REAL.name());
    	reg.setDescripcion(EmpresaDomicilioTipoEnum.REAL.getDescripcion());
    	lst.add(reg);
    	
    	return lst;
    }
    
    @Override
    public void borrar(Integer empresaId, Integer domicilioId) {
		
        Optional<EmpresaDomicilioBO> regEmpresaDomicilioBO = storage.findByIdAndEmpresaId(empresaId, domicilioId); 
        if ( regEmpresaDomicilioBO.isEmpty()  ) {
        	String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es") );
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
					String.format(errorMsg, empresaId)  );
        }
        
        if ( regEmpresaDomicilioBO.get().esDomicilioFiscal() ) {
        	String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.DOMICILIO_FISCAL_3.getMsgKey(), null, new Locale("es") );
			throw new BusinessException(EmpresaExceptionEnum.DOMICILIO_FISCAL_3.name(),  errorMsg  );
        }
        
        if ( storage.domiDDJJCount(domicilioId) > 0 ) {
           	String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_CON_FK.getMsgKey(), null, new Locale("es") );
    			throw new BusinessException(CommonEnumException.CODIGO_CON_FK.name(), 
    					String.format(errorMsg, " DDJJ ")  );
        }
        
        
        
		storage.deleteById(domicilioId);
	}
    
}
