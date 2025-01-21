package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.mail.MailHelper;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaContactoValidar;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaExceptionEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaContactoStorage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaContactoValidarImpl implements EmpresaContactoValidar {

	private final EmpresaContactoStorage storage;    
	private final MessageSource messageSource;

	public void run (ContactoBO contacto) {
        
        if ( contacto.esTipoMail() ) {
            if ( !contacto.mailValido() ) {
               	String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_MAIL_PREFIJO.getMsgKey(), null, new Locale("es"));
        		throw new BusinessException(EmpresaExceptionEnum.CONTACTO_MAIL_PREFIJO.name(), errorMsg );        	
            }
	        if ( ! MailHelper.isValid(contacto.getValor()) ) {
		           	String errorMsg = messageSource.getMessage(CommonEnumException.MAIL_FORMATO.getMsgKey(), null, new Locale("es"));
		    		throw new BusinessException(CommonEnumException.MAIL_FORMATO.name(), errorMsg );
	    	}
        } 
        
		if ( !contacto.telefValido() ) {        	
           	String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_TEL_WHASAP_LENGTH.getMsgKey(), null, new Locale("es"));
    		throw new BusinessException(EmpresaExceptionEnum.CONTACTO_TEL_WHASAP_LENGTH.name(), errorMsg );
        }

	}

	public void run (EmpresaBO empresa) {
		if ( empresa.getContactos() == null || empresa.getContactos().size() == 0) { 
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(EmpresaExceptionEnum.CONTACTO.name(), errorMsg);
		}
		
		
		for (ContactoBO reg : empresa.getContactos()) {
			run(reg);
		}
		
		validarExistenciaTelPpal(empresa.getContactos());
		for (ContactoBO reg : empresa.getContactos()) {
			validarUKTelPal(empresa.getContactos(), reg);
		}

		validarExistenciaMailPpal(empresa.getContactos());
		for (ContactoBO reg : empresa.getContactos()) {
			validarUKMailPal(empresa.getContactos(), reg);
		}

		validarExistenciaWhatsap(empresa.getContactos());
		for (ContactoBO reg : empresa.getContactos()) {
			validarUKWhatsap(empresa.getContactos(), reg);
		}

		validarContactoDuplicado(empresa);
		
	}
	
	public void run (EmpresaBO empresa, ContactoBO contacto) {
		 run (empresa.getContactos(), contacto);
	}
		
	public void run (List<ContactoBO> contactos, ContactoBO contacto) {
		run(contacto);
		
		validarTelPpalTipoModi(contacto);
		validarUKTelPal(contactos, contacto);
		
		validarMailPpalTipoModi(contacto);
		validarUKMailPal(contactos, contacto);
		
		validarContactoDuplicado(contactos, contacto);
	}
	
	
	private void validarContactoDuplicado(EmpresaBO empresa) {
		for (ContactoBO contacto : empresa.getContactos()) {	
			validarContactoDuplicado(empresa, contacto);
		}
	}
	
	private void validarContactoDuplicado(EmpresaBO empresa, ContactoBO contacto) {
		validarContactoDuplicado(empresa.getContactos(), contacto);
	}
	
	private void validarContactoDuplicado(List<ContactoBO> contactos, ContactoBO contacto) {
		Integer aux = 1;  
		String errorMsg2 = "";
		for (ContactoBO contacto2 : contactos) {
			if ( iguales(contacto, contacto2) ) {
				if ( !mismoRegistro(contacto, contacto2) ) { //NO ES mismo registro O ES alta 			
					if ( !( (contacto.esWhatsap() && contacto2.esTelefonoPpal()) || (contacto2.esWhatsap() && contacto.esTelefonoPpal()))  ) {
						aux = aux+1;					
					}
				}
			}
		}
		if ( aux>1) {		
			errorMsg2 = " Mail ";
			if ( contacto.esTipoTelefono() ) {
				errorMsg2 = " Tel./WhatsApp ";
			}
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_DUPLICADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(EmpresaExceptionEnum.CONTACTO_DUPLICADO.name(), String.format(errorMsg, errorMsg2 + contacto.getValor()));
		}
	}
	

	private boolean contactoTipoIguales(ContactoBO contacto, ContactoBO contacto2) {
		if ( contacto.esTipoMail()   && contacto2.esTipoMail() ) {
			return true;
		}		
		if ( contacto.esTipoTelefono()  && contacto2.esTipoTelefono() ) {
			return true;
		}
		return false;
	}

	private void validarTelPpalTipoModi(ContactoBO contacto) {
		//TEL PPAL: No se puede cambiar el TIPO 
		if (contacto.esTipoTelefono() && !contacto.esTelefonoPpal() && contacto.getId() != null ) {
    		Optional<ContactoBO> EmpresaContactoBoOld = storage.findById(contacto.getId());
    		if  ( EmpresaContactoBoOld.isPresent() ) {
    			if (EmpresaContactoBoOld.get().esTelefonoPpal() ) {
		           	String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_TEL_TIPO_MODI.getMsgKey(), null, new Locale("es"));
		    		throw new BusinessException(EmpresaExceptionEnum.CONTACTO_TEL_TIPO_MODI.name(), errorMsg );        				
    			}
    		}
		}
	}

	private void validarMailPpalTipoModi(ContactoBO contacto) {
		//TEL PPAL: No se puede cambiar el TIPO 
		if (contacto.esTipoMail() && !contacto.esMailPpal() && contacto.getId() != null ) {
    		Optional<ContactoBO> EmpresaContactoBoOld = storage.findById(contacto.getId());
    		if  ( EmpresaContactoBoOld.isPresent() ) {
    			if (EmpresaContactoBoOld.get().esMailPpal() ) {
		           	String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_TEL_TIPO_MODI.getMsgKey(), null, new Locale("es"));
		    		throw new BusinessException(EmpresaExceptionEnum.CONTACTO_TEL_TIPO_MODI.name(), errorMsg );        				
    			}
    		}
		}
	}

	private void validarExistenciaTelPpal(List<ContactoBO> contactos) {
		//this.tipo.equals(EmpresaContactoTipoEnum.TEL);
		Boolean existe = false;
		for (ContactoBO reg : contactos) {
			if ( reg.esTelefonoPpal() )
				existe = true;
		}
		if ( !existe ) {
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_TEL_2.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(EmpresaExceptionEnum.CONTACTO_TEL_2.name(), errorMsg);
		}
	}
	
	private void validarExistenciaMailPpal(List<ContactoBO> contactos) {
		//this.tipo.equals(EmpresaContactoTipoEnum.TEL);
		Boolean existe = false;
		for (ContactoBO reg : contactos) {
			if ( reg.esMailPpal() )
				existe = true;
		}
		if ( !existe ) {
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_MAIL_2.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(EmpresaExceptionEnum.CONTACTO_MAIL_2.name(), errorMsg);
		}
	}
	
	private void validarExistenciaWhatsap(List<ContactoBO> contactos) {
		//this.tipo.equals(EmpresaContactoTipoEnum.TEL);
		Boolean existe = false;
		for (ContactoBO reg : contactos) {
			if ( reg.esWhatsap() )
				existe = true;
		}
		if ( !existe ) {
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_WHATSAP_2.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(EmpresaExceptionEnum.CONTACTO_WHATSAP_2.name(), errorMsg);
		}
	}
	
	
	private void validarUKTelPal(List<ContactoBO> contactos, ContactoBO contacto) {
        //TEL PPAL: Valida duplicado
		if ( contacto.esTelefonoPpal()) {	        
    		for (ContactoBO reg : contactos) {
    			if ( reg.esTelefonoPpal() ) {
					if (  !mismoRegistro(contacto, reg)  ) {//NO ES mismo registro O ES alta  						
			           	String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_TEL_DUPLICADO.getMsgKey(), null, new Locale("es"));
			    		throw new BusinessException(EmpresaExceptionEnum.CONTACTO_TEL_DUPLICADO.name(), errorMsg );
					}		
    			}
			}
	        
	    }			
	}

	
	private void validarUKMailPal(List<ContactoBO> contactos, ContactoBO contacto) {
        //Mail PPAL: Valida duplicado
		if ( contacto.esMailPpal()) {	        
    		for (ContactoBO reg : contactos) {
    			if ( reg.esMailPpal() ) {
    				if (  !mismoRegistro(contacto, reg) ) {//NO ES mismo registro O ES alta  	
			           	String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_MAIL_DUPLICADO.getMsgKey(), null, new Locale("es"));
			    		throw new BusinessException(EmpresaExceptionEnum.CONTACTO_MAIL_DUPLICADO.name(), errorMsg );
					}		
    			}
			}        
	    }			
	}
	
	private void validarUKWhatsap(List<ContactoBO> contactos, ContactoBO contacto) {
        //Mail PPAL: Valida duplicado
		if ( contacto.esWhatsap()) {
	        if ( contactos.size() > 0 ) {
        		for (ContactoBO reg : contactos) {
        			if ( reg.esWhatsap() ) {
						if ( !(contacto.getId() == null && reg.getId() == null
								&& contacto.getValor().equals(reg.getValor()) ) //Los 2 nulos: es el mismo registro 
								&&
								(contacto.getId() == null || !reg.getId().equals(contacto.getId())) 
							) {
				           	String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CONTACTO_WHATSAP.getMsgKey(), null, new Locale("es"));
				    		throw new BusinessException(EmpresaExceptionEnum.CONTACTO_WHATSAP.name(), errorMsg );
						}		
        			}
				}
	        }
	    }			
	}
	
	private boolean mismoRegistro(ContactoBO contacto1, ContactoBO contacto2) {
		if ( contacto1.getId() == null 
				&& contacto2.getId() == null 
				&& iguales(contacto1, contacto2) ) {
			return true;
		}
		if ( contacto1.getId() != null && contacto1.getId().equals(contacto2.getId()) )
			return true;
			
		return false;	
	}
	

	private boolean iguales(ContactoBO contacto1, ContactoBO contacto2) {
		if ( contactoTipoIguales(contacto1, contacto2)
			&& contacto1.esTipoTelefono()
			&& contacto1.getPrefijo().toUpperCase().equals(contacto2.getPrefijo().toUpperCase())
			&& contacto1.getValor().toUpperCase().equals(contacto2.getValor().toUpperCase())
		) {
			return true;
		} 
		
		if ( contactoTipoIguales(contacto1, contacto2)
				&& contacto1.esTipoMail()
				&& contacto1.getValor().toUpperCase().equals(contacto2.getValor().toUpperCase())
			) {
				return true;
			} 
		
		return false;
	}
}
