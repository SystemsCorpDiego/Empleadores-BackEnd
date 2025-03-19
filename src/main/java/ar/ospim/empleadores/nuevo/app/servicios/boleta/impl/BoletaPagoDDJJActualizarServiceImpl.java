package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularInteresService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoConsultaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJActualizarService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoletaPagoDDJJActualizarServiceImpl implements BoletaPagoDDJJActualizarService {

	private final MessageSource messageSource;
	private final BoletaPagoConsultaService boletaPagoConsulta;
	private final BoletaPagoCalcularInteresService calcularInteresService;
	private final BoletaPagoStorage storage;
	private final FormaPagoService formaPagoService;
	//private final BoletaPagoElectronicaService bepService;
	
	@Override
	public BoletaPagoBO run(BoletaPagoBO bo) {
		//String formaPagoOld = null;
		validar(bo);
		
		
		BoletaPagoBO reg = boletaPagoConsulta.find(bo.getId());
		if ( reg.getBaja() != null) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.REGISTRO_BAJA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.REGISTRO_BAJA.name(), errorMsg);
		}
		if ( reg.getDdjjId() == null ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.DDJJ_NO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.DDJJ_NO.name(), errorMsg);
		}
		if( bo.getIntencionDePago().equals(reg.getIntencionDePago()) && bo.getFormaDePago().equals(reg.getFormaDePago())) {
			//No modifico nada !!
			return reg;
		}

		/* 20230319 - Se habilita PagoMisCuentas para AMTIMA
		if ( reg.getAporte().getCodigo().equals("AMTIMACS") && bo.getFormaDePago().equals("PMCUENTAS")  ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.FORMA_PAGO_APORTE_INEXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.FORMA_PAGO_APORTE_INEXISTENTE.name(), errorMsg );										
		}
		 */
		
		if ( puedeActualizar(reg) ) {
			reg = actualizar(reg, bo); 
		} else {
			reg = reemplazar(reg, bo);
		}
		
		
		/*
		Boolean bCambioIdP = !reg.getIntencionDePago( ).equals(bo.getIntencionDePago());
		Boolean bCambioFdP = !bo.getFormaDePago().equals(reg.getFormaDePago());

		reg.setIntencionDePago( bo.getIntencionDePago() );
		*/
		
		/*
		 * 							VENTANILLA	LINK						BALENCO
				VENTANILLA	nada				vep						NB
				LINK					nada				CI=>NS y NB		NB
				BALENCO			nada				NB						CI=>NS y NV
				
		 	  	CI= camio importe
		 	  	NS= nueva_secuencia
		 	  	NB= nuevo_bep
		 */
		/*
		if ( reg.getDdjjId() != null ) {			
			if ( bCambioIdP ) 
				reg.setInteres( calcularInteresService.run(reg.getVencimiento(), reg.getIntencionDePago(), reg.getImporte()) );
			
			if ( bCambioFdP ) {
				//formaPagoOld = reg.getFormaDePago();
				reg.setFormaDePago(bo.getFormaDePago());
			}
			
			if ( formaPagoService.generaVEP( reg.getFormaDePago() ) ) {
				if ( !bCambioFdP &&  bCambioIdP ) {
					//Genero Nueva Secuencia
					reg.setSecuencia( boletaPagoConsulta.getSecuenciaProx(reg.getEmpresa().getId()) );
				}
				//Genero Nuevo BEP
				//reg.setBep( bepService.run(reg) );
			} else {
				//Genero Nueva Secuencia => si Forma de Pago es VENTANILLA y hay cambio en Fecha de Pago
				if ( bCambioIdP && formaPagoService.getFormaPagoCodigoVentanillaBanco().equals(reg.getFormaDePago()) ) {
					reg.setSecuencia( boletaPagoConsulta.getSecuenciaProx(reg.getEmpresa().getId()) );
				}
				reg.setBep( null );
			}
			reg = storage.guardar(reg);
		} else {
			//TODO: Deberia llamar a otro servicio o dar ERROR !!!
			reg.setInteres(BigDecimal.ZERO);
			reg.setFormaDePago( formaPagoService.getFormaPagoCodigoVentanillaBanco() );
		}
		*/
		
		return reg;
	}
	
	
	private BoletaPagoBO reemplazar(BoletaPagoBO regOri, BoletaPagoBO bo) {
		Integer idBaja = regOri.getId();

		regOri.setSecuencia( boletaPagoConsulta.getSecuenciaProx(regOri.getEmpresa().getId()) );
		regOri.setBep(null);
		regOri.setImpresion(null);
		regOri.setFormaDePago( bo.getFormaDePago());
		
		//Calculo Intereses
		if ( !regOri.getIntencionDePago( ).equals(bo.getIntencionDePago()) ) {
			regOri.setIntencionDePago( bo.getIntencionDePago() );
			regOri.setInteres( calcularInteresService.run(regOri.getVencimiento(), regOri.getIntencionDePago(), regOri.getImporte()) );
		}

		//Genero Boleta
		regOri.setId(null);
		regOri = storage.guardar(regOri);
		
		//Anulo id Original
		storage.registrarBaja(idBaja);
		
		return regOri;
	}
	
	private BoletaPagoBO actualizar(BoletaPagoBO regOri, BoletaPagoBO bo) {
		//Calculo Intereses
		if ( !regOri.getIntencionDePago( ).equals(bo.getIntencionDePago()) ) {
			regOri.setIntencionDePago( bo.getIntencionDePago() );
			regOri.setInteres( calcularInteresService.run(regOri.getVencimiento(), regOri.getIntencionDePago(), regOri.getImporte()) );
		}
		
		if ( formaPagoService.generaVEP( regOri.getFormaDePago() ) ) {
			regOri.setImpresion(null);
		}
		regOri.setFormaDePago( bo.getFormaDePago());
		
		regOri = storage.guardar(regOri);
		return regOri;
	}
	
	public Boolean puedeActualizar(BoletaPagoBO reg ) {
		if ( (formaPagoService.generaVEP( reg.getFormaDePago() ) && reg.getBep() != null)
				|| (formaPagoService.getFormaPagoCodigoVentanillaBanco().equals( reg.getFormaDePago() ) && reg.getImpresion() != null ) ) {
			return false;
		}
		return true;
	}

	private void validar(BoletaPagoBO bo) {
		if ( bo.getFormaDePago() == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Forma de Pago")	);
		}
		
		if ( bo.getIntencionDePago() == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Fecha de Intención de Pago")	);
		}		
	}
}
