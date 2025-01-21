package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.exception.NotFoundException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.ActualizarEmpresa;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.ConsultarEmpresa;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.CrearEmpresa;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpresaServiceImpl.class);
    public static final String OUTPUT = "Output -> {}";
    private final MessageSource messageSource;
    
    private final CrearEmpresa crearEmpresa;
    private final ConsultarEmpresa consultarEmpresa;
    private final ActualizarEmpresa actualizarEmpresa;
    
    public EmpresaServiceImpl(MessageSource messageSource, CrearEmpresa crearEmpresa, ConsultarEmpresa consultarEmpresa, ActualizarEmpresa actualizarEmpresa) {
        super();
        this.messageSource = messageSource;
        this.crearEmpresa = crearEmpresa;
        this.consultarEmpresa = consultarEmpresa;
        this.actualizarEmpresa = actualizarEmpresa;
    }

    @Override 
    public EmpresaBO addEmpresa(EmpresaBO empresa) {
        LOG.debug("Vamos a guardar -> {}", empresa);
        
        EmpresaBO empresaCreada = crearEmpresa.run(empresa);
        
        LOG.debug("Empresa guardada -> {}", empresaCreada);
        return empresaCreada;
    }

    @Override 
    public EmpresaBO updateEmpresa(EmpresaBO empresa) {
    	LOG.debug("Vamos a actualizar-> {}", empresa); 
    	EmpresaBO empresaCreada = actualizarEmpresa.run(empresa);
    	LOG.debug("Empresa guardada -> {}", empresaCreada);        
    	return empresaCreada;
    }
    
    @Override
    public EmpresaBO getEmpresa(Integer id) {
        return consultarEmpresa.getEmpresa(id);
    }
    
    @Override
    public EmpresaBO getEmpresa(String cuit) {
        return consultarEmpresa.getEmpresa(cuit);
    }
    
    @Override
    public Optional<EmpresaBO> findEmpresa(Integer id) {
        LOG.debug("Going to get person -> {}", id);
        Optional<EmpresaBO> result = consultarEmpresa.findEmpresa(id);
        LOG.debug("Person gotten-> {}", result);
        return result;
    }
    
    @Override 
    public EmpresaBO validarEmpresa(Integer id, String cuit) {
    	LOG.debug("validarEmpresa -> {}", id);
    	EmpresaBO result = null;
    	if ( id == null && cuit == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "CUIT"));			
    	}
    	
    	if ( id != null) {
    		result = this.getEmpresa(id);    		
    	} else {
    		result = this.getEmpresa(cuit);    		
    	}
    	
    	if ( id != null && cuit != null ) {
    		if ( !result.getCuit().equals(cuit) ) {
    			String errorMsg = messageSource.getMessage(CommonEnumException.ID_DIFIERE_DE_ATRIBUTO.getMsgKey(), null, new Locale("es"));
    			throw new BusinessException(CommonEnumException.ID_DIFIERE_DE_ATRIBUTO.name(), String.format(errorMsg, id, "CUIT", cuit ));			   			
    		}
    	}
    	 
        LOG.debug("validarEmpresa-> {}", result);
        return result;
    }
    
    public List<EmpresaBO> findAll() {
    	return consultarEmpresa.findAll();
    }
    
    private Supplier<NotFoundException> empresaNotFound(Integer empresaId) {
        return () -> new NotFoundException("empresa-not-exists", String.format("La empresa %s no existe", empresaId));
    }
}
