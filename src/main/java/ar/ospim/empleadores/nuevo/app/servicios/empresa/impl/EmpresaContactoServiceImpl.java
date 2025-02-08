package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoTipoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaContactoService;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaContactoValidar;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaExceptionEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaContactoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EmpresaContactoTipoEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaContactoServiceImpl implements EmpresaContactoService {

   private static final Logger LOG = LoggerFactory.getLogger(EmpresaContactoServiceImpl.class);
    public static final String OUTPUT = "Output -> {}";

    private final MessageSource messageSource;
    private final EmpresaStorage empresaStorage;
    private final EmpresaContactoStorage storage;
    private final EmpresaContactoValidar validador;
    

    @Override
    public ContactoBO guardar(Integer empresaId, ContactoBO contacto) {
        LOG.debug("Vamos a guardar -> {}", contacto);
        
        Optional<EmpresaBO> regEmpreBo = empresaStorage.findById(empresaId);
        
        if ( regEmpreBo.isEmpty() ) {
        	String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
				String.format(errorMsg, empresaId)	);
        }
        
        EmpresaBO empresa = regEmpreBo.get();        
        empresa.setContactos(storage.findByEmpresaId(empresaId));
        
        validador.run(empresa, contacto);
        
        ContactoBO empresaContactoBO = storage.save(empresaId, contacto);
        
        LOG.debug("Empresa guardada -> {}", empresaContactoBO);
        return empresaContactoBO;
    }	
	
    @Override
    public List<ContactoBO> consultar(Integer empresaId) {
		return storage.findAll(empresaId); 
	}
    
    @Override
    public String consultarMailPpal(Integer empresaId) {
    	List<ContactoBO> lst = storage.findAll(empresaId);
    	for (ContactoBO reg: lst) {
    		if ( reg.esMailPpal() )
    			return reg.getValor();
    	}
    	return null;
	}
    
    
    @Override
    public List<ContactoTipoBO> consultarTipos() {
    	List<ContactoTipoBO> lst = new ArrayList<ContactoTipoBO>();
    	ContactoTipoBO reg = new ContactoTipoBO();
    	reg.setCodigo(EmpresaContactoTipoEnum.MAIL.name());
    	reg.setDescripcion(EmpresaContactoTipoEnum.MAIL.getDescripcion());
    	lst.add(reg);
    	
    	reg = new ContactoTipoBO();
    	reg.setCodigo(EmpresaContactoTipoEnum.MAIL2.name());
    	reg.setDescripcion(EmpresaContactoTipoEnum.MAIL2.getDescripcion());
    	lst.add(reg);
    	
    	reg = new ContactoTipoBO();
    	reg.setCodigo(EmpresaContactoTipoEnum.TEL.name());
    	reg.setDescripcion(EmpresaContactoTipoEnum.TEL.getDescripcion());
    	lst.add(reg);
    	
    	reg = new ContactoTipoBO();
    	reg.setCodigo(EmpresaContactoTipoEnum.TEL2.name());
    	reg.setDescripcion(EmpresaContactoTipoEnum.TEL2.getDescripcion());
    	lst.add(reg);

    	reg = new ContactoTipoBO();
    	reg.setCodigo(EmpresaContactoTipoEnum.WHATSAP.name());
    	reg.setDescripcion(EmpresaContactoTipoEnum.WHATSAP.getDescripcion());
    	lst.add(reg);
    	
    	return lst;
    	
    }

    @Override
    public void borrar(Integer empresaId, Integer contactoId) {
		
        Optional<ContactoBO> reg = storage.findByEmpresaIdAndId(empresaId, contactoId); 
        if ( reg.isEmpty()  ) {
        	String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es") );
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
					String.format(errorMsg, empresaId)  );
        }
		
         
		if ( reg.get().esMailPpal() || reg.get().esTelefonoPpal() ) {			
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_PPAL_BAJA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(EmpresaExceptionEnum.CONTACTO_PPAL_BAJA.name(), errorMsg );			
		}
		
		storage.deleteById(contactoId);
	}
    
}
