package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.cui.CuilUtils;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.CrearEmpresa;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaContactoValidar;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaDomicilioValidar;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaExceptionEnum;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaStorageEnumException;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EmpresaBoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EmpresaContactoMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.EmpresaDomicilioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaContactoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaDomicilioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaContacto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaDomicilio;

@Service
public class CrearEmpresaImpl implements CrearEmpresa {
    private static final Logger LOG = LoggerFactory.getLogger(CrearEmpresaImpl.class);
    public static final String OUTPUT = "Output -> {}";
    private final MessageSource messageSource;
    
    private final EmpresaRepository repository;
    private final EmpresaBoMapper mapper;
    private final EmpresaDomicilioRepository domicilioRepository;
    private final EmpresaDomicilioValidar domicilioValidar;
    private final EmpresaDomicilioMapper domicilioMapper;
    private final EmpresaContactoRepository contactoRepository;
    private final EmpresaContactoValidar contactoValidar;
    private final EmpresaContactoMapper contactoMapper;
    
    
    public CrearEmpresaImpl(EmpresaRepository empresaRepository, EmpresaBoMapper empresaMapper, 
    		EmpresaDomicilioRepository domicilioRepository, 
    		EmpresaDomicilioValidar domicilioValidar,
    		EmpresaDomicilioMapper domicilioMapper,
    		EmpresaContactoMapper contactoMapper,
    		EmpresaContactoRepository contactoRepository,
    		EmpresaContactoValidar contactoValidar,
    		 MessageSource messageSource) {
        super();
        this.messageSource = messageSource;
        this.repository = empresaRepository;
        this.domicilioRepository = domicilioRepository;
        this.domicilioValidar = domicilioValidar;
        this.mapper = empresaMapper;
        this.domicilioMapper = domicilioMapper;
        this.contactoMapper = contactoMapper;
        this.contactoRepository = contactoRepository;
        this.contactoValidar = contactoValidar;
    }	 

	@Override
	public EmpresaBO run(EmpresaBO empresa) {
		if (empresa.getRazonSocial()!=null)
			empresa.setRazonSocial( empresa.getRazonSocial().toUpperCase() );

		validarEmpresa(empresa);
		domicilioValidar.run(empresa);
		contactoValidar.run(empresa); 
		
        LOG.debug("Vamos a guardar -> {}", empresa);
        Empresa empresaNueva = mapper.map(empresa); 
        
        try {
        	empresaNueva = repository.save(empresaNueva);
        } catch (Exception e) {
            throw new BusinessException(EmpresaStorageEnumException.REGISTRO_DUPLICADO.name(),
                    "El cuit de la empresa ya existe");
        }

        
        EmpresaBO empresaBO =  mapper.map(empresaNueva);      
        empresaBO.setDomicilios( new ArrayList<EmpresaDomicilioBO>() );
        empresaBO.setContactos( new ArrayList<ContactoBO>() );


	    EmpresaDomicilio domicilioNuevo;
	    for ( EmpresaDomicilioBO domicilioBO : empresa.getDomicilios() ) {
	    	if ( domicilioBO.getPlanta()!=null )
	    		domicilioBO.setPlanta(domicilioBO.getPlanta().toUpperCase());
	    	if ( domicilioBO.getCalle() != null )
	    		domicilioBO.setCalle(domicilioBO.getCalle().toUpperCase()); 
	        if ( domicilioBO.getDepto() != null ) 
	        	domicilioBO.setDepto(domicilioBO.getDepto().toUpperCase()); 
	        
	    	domicilioNuevo = domicilioMapper.map(empresaNueva.getId(), domicilioBO);
	    	domicilioNuevo = domicilioRepository.save(domicilioNuevo);
        	empresaBO.getDomicilios().add( domicilioMapper.map(domicilioNuevo) );
        }
	    
	    EmpresaContacto contactoNuevo;
	    for ( ContactoBO contactoBO :  empresa.getContactos() ) {
	    	contactoNuevo = contactoMapper.map(contactoBO, empresaNueva.getId());
	    	contactoNuevo = contactoRepository.save(contactoNuevo);
	    	empresaBO.getContactos().add( contactoMapper.map(contactoNuevo) );
	    }
	    
        LOG.debug("Empresa guardada -> {}", empresaBO.toString());
        return empresaBO;
	}

	private void validarEmpresa(EmpresaBO empresa) {
		if ( !CuilUtils.validar(empresa.getCuit()) ) {
		//if ( !EmpresaHelper.isValidCUIT(empresa.getCuit()) ) {
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CUIT_FORMAT.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(EmpresaExceptionEnum.CUIT_FORMAT.name(), errorMsg);			
		}
		
		Optional<Empresa> empreConsul = repository.findByCuit(empresa.getCuit());
		if ( empreConsul.isPresent()) {
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CUIT.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(EmpresaExceptionEnum.CUIT.name(), 
					String.format(errorMsg, empresa.getCuit()));
			
		}
	}
	 
}
