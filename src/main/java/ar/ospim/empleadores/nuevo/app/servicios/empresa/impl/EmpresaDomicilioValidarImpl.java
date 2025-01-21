package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaDomicilioValidar;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaExceptionEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EmpresaDomicilioTipoEnum;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaDomicilioValidarImpl implements EmpresaDomicilioValidar {

	private final MessageSource messageSource;
	
	public void run(EmpresaDomicilioBO domicilio) {
		//Valido Atributos OBLIGATOROS
		if ( domicilio.getTipo() == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Tipo de Domicilio"));			
		}
		if ( domicilio.getProvincia() == null || domicilio.getProvincia().getId() == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Provincia del Domicilio"));			
		}
		if ( domicilio.getLocalidad()== null || domicilio.getLocalidad().getId()== null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Localidad del Domicilio"));			
		}
		if ( domicilio.getCalle() == null || StringUtils.isBlank(domicilio.getCalle()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Calle"));			
		}
		if ( domicilio.getCalleNro() == null || StringUtils.isBlank(domicilio.getCalleNro()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Altura / Nro"));			
		}
		if ( domicilio.getCp() == null || StringUtils.isBlank(domicilio.getCp()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Código Postal"));			
		}
		if ( domicilio.getPlanta() == null || StringUtils.isBlank(domicilio.getPlanta()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Planta"));			
		}
	
	}
	
	public void run(EmpresaBO empresa) {
		if ( empresa.getDomicilios() != null && empresa.getDomicilios().size()>0  ) {
				run(empresa.getDomicilios());
		}
	}
	
	public void run(EmpresaBO empresa, EmpresaDomicilioBO domicilio) {
		run(empresa.getDomicilios(), domicilio);
	}
	
	public void run ( List<EmpresaDomicilioBO> cons, EmpresaDomicilioBO domicilio) {
		
		run(domicilio);
		
		validarUKDomiFiscal(cons, domicilio);
		validarUKPlanta(cons, domicilio);
		
	}
	
	public void run(List<EmpresaDomicilioBO> domicilios) {
		for(EmpresaDomicilioBO domi : domicilios) {
			run(domi);
		}
		validarDomiFiscal(domicilios);
		validarUKPlanta(domicilios);
	}
	
	private void validarUKPlanta(List<EmpresaDomicilioBO> cons) {
		for(EmpresaDomicilioBO reg : cons) {
			validarUKPlanta(cons, reg);
		}
	}
	
	private void validarUKPlanta(List<EmpresaDomicilioBO> cons, EmpresaDomicilioBO domicilio) {
 		//Planta: validar que no repita				
        if (cons != null ) {
        	for(EmpresaDomicilioBO reg : cons) {
        		if (  !mismoRegistro(domicilio, reg) ) { //Los 2 nulos: es el mismo registro 	        		
	        		if ( reg.getPlanta().toUpperCase().equals(domicilio.getPlanta().toUpperCase()) ) {        			
	    				String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.DOMICILIO_PLANTA.getMsgKey(), null, new Locale("es"));	
	    				throw new BusinessException(EmpresaExceptionEnum.DOMICILIO_PLANTA.name(), errorMsg);        				
	        		}
        		}
        	}
        }
	}
	
	private void validarUKDomiFiscal(List<EmpresaDomicilioBO> cons, EmpresaDomicilioBO domicilio) {
    	//Dom FISCAL: validar que no repita
        if (cons != null ) {
        	if (EmpresaDomicilioTipoEnum.FISCAL.equals(domicilio.getTipo() ) ) {
        		for(EmpresaDomicilioBO reg : cons) {
        			if ( EmpresaDomicilioTipoEnum.FISCAL.equals(reg.getTipo()) 
        					&& !mismoRegistro(domicilio, reg) ) {
        				String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.DOMICILIO_FISCAL.getMsgKey(), null, new Locale("es"));	
        				throw new BusinessException(EmpresaExceptionEnum.DOMICILIO_FISCAL.name(), errorMsg);        				
        			}
        		}
        	}
        }
	}
		
	private void validarDomiFiscal(List<EmpresaDomicilioBO> domicilios) {
		int cont = 0; 
		for(EmpresaDomicilioBO domi : domicilios) {
			if ( EmpresaDomicilioTipoEnum.FISCAL.equals( domi.getTipo()) )
				cont += 1; 
		}
		if ( cont==0) {
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.DOMICILIO_FISCAL_2.getMsgKey(), null, new Locale("es"));	
			throw new BusinessException(EmpresaExceptionEnum.DOMICILIO_FISCAL_2.name(), errorMsg);
		}
		if ( cont>1) {
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.DOMICILIO_FISCAL.getMsgKey(), null, new Locale("es"));	
			throw new BusinessException(EmpresaExceptionEnum.DOMICILIO_FISCAL.name(), errorMsg);
		}
	}
	
	private boolean mismoRegistro(EmpresaDomicilioBO domi1, EmpresaDomicilioBO domi2) {
		if ( domi1.getId() == null 
				&& domi2.getId() == null 
				&& iguales(domi1, domi2) ) {
			return true;
		}
		if ( domi1.getId() != null && domi1.getId().equals(domi2.getId()) )
			return true;
			
		return false;	
	}
	private boolean iguales(EmpresaDomicilioBO domi, EmpresaDomicilioBO domi2) {
		if ( domi.getProvincia().equals(domi2.getProvincia()) &&
				domi.getLocalidad().equals(domi2.getLocalidad()) &&
				domi.getCalle().equals(domi2.getCalle()) &&
				domi.getCp().equals(domi2.getCp()) 
				) {
			return true;						
		}
		return false;		
	}
	
}
