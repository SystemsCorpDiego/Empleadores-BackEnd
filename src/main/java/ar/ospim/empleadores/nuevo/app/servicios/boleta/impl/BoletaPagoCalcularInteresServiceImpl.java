package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.AfipInteresBO;
import ar.ospim.empleadores.nuevo.app.servicios.afipinteres.AfipInteresService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularInteresService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularVtoService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class BoletaPagoCalcularInteresServiceImpl implements BoletaPagoCalcularInteresService {	
	private final MessageSource messageSource;
	private final BoletaPagoCalcularVtoService boletaPagoCalcularVtoService;
	private final AfipInteresService afipInteresService;
	
	
	public BigDecimal run(String aporte, String cuit, LocalDate periodo, LocalDate intencionDePago, BigDecimal importeAPagar) {
		LocalDate vencimiento = boletaPagoCalcularVtoService.run(aporte, cuit, periodo);		
		return run( vencimiento,  intencionDePago,  importeAPagar);
	}
 

	public BigDecimal run(LocalDate vencimiento, LocalDate intencionDePago, BigDecimal importeAPagar) {
		log.debug("BoletaPagoCalcularInteresService - vencimiento:" + vencimiento +" - intencionDePago:"+ intencionDePago + " - importeAPagar: " + importeAPagar ); 
		//TODO: cambiar esto x Funcion posgreSql => getIntereses() 
		// 			=> getIntereses(capital, interesesPreCalculados, fechaDesde, FechaHasta)
		//				=> getIntereses(importeAPagar, 0, vencimiento+1, intencionDePago)
		
		BigDecimal importe = BigDecimal.ZERO; 
		LocalDate dia = vencimiento.plusDays(1);
		AfipInteresBO afipInteres = getInteresDia( dia);
		while ( !intencionDePago.isBefore(dia)  ) {//dia menor igual a intencionDePago
			if ( ! isBetween(dia, afipInteres)  ) {
				afipInteres = getInteresDia(dia);
			}
			importe = importe.add(importeAPagar.multiply(afipInteres.getIndice()));	
			importe = importe.setScale(2, RoundingMode.HALF_UP);
			log.debug("BoletaPagoCalcularInteresService - dia:" + dia +" - importe:"+ importe ); 
						
			dia = dia.plusDays(1);
		}
		log.debug("BoletaPagoCalcularInteresService - RTA - importe:" + importe);
		if ( ! BigDecimal.ZERO.equals(importe) ) {
			importe = importe.divide(new BigDecimal(100));
		}
		return importe;		
	}
	
	private Boolean isBetween(LocalDate dia, AfipInteresBO afipInteres) {
		if ( ( !dia.isBefore(afipInteres.getDesde()) && //No es Anterior al DESDE 
				(  afipInteres.getHasta()==null  ||  //No es Posterior al HASTA
				  !dia.isAfter(afipInteres.getHasta()))) ) {    //o el HASTA es NULO 
			return true;
		} else {
			return false;			
		}
	}
	
	private AfipInteresBO getInteresDia(LocalDate dia) {
		AfipInteresBO afipInteres = afipInteresService.getContenido(dia);
		if ( afipInteres == null) {
			log.debug( "getInteresDia - dia:" + dia + " - afipInteres: NULL " ); 
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.AFIP_INTERES_INEXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.AFIP_INTERES_INEXISTENTE.name(), errorMsg);
		}
		log.debug( "getInteresDia - dia:" + dia + " - afipInteres: " + afipInteres ); 
		return afipInteres;
	}
	
}
