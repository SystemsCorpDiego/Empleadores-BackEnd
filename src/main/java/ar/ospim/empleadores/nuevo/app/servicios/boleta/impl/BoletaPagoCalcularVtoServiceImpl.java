package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.FeriadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularVtoService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.feriado.FeriadoService;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AfipVencimientoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AporteVencimientoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfipVencimiento;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteVencimiento;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoletaPagoCalcularVtoServiceImpl implements BoletaPagoCalcularVtoService {
	
	private final MessageSource messageSource;
	private final AfipVencimientoRepository vtoRepo;
	private final AporteVencimientoRepository vtoPorAporteRepo;
	private final FeriadoService feriadoService; 
	
	@Override
	public LocalDate run(String aporte, String cuit, LocalDate periodo) {
		List<FeriadoBO> feriados = feriadoService.getFeriadosDesde(periodo);
		
		LocalDate fechaVtoOri = getVtoOriginal(aporte, cuit, periodo, feriados);
		 		
		return fechaVtoOri;
	}
	
	public LocalDate getVtoOriginal(String aporte, String cuit, LocalDate periodo) {
		List<FeriadoBO> feriados = feriadoService.getFeriadosDesde(periodo);
		return getVtoOriginal(aporte, cuit, periodo, feriados) ;
	}
	
	private LocalDate getVtoOriginal(String aporte, String cuit, LocalDate periodo, List<FeriadoBO> feriados) {
		Optional<LocalDate> vencimiento = null;
		
		//Busco Vto segun Seteos por aporte.-
		vencimiento = getVtoPorAporte(aporte, periodo, feriados);
		if (vencimiento.isPresent() ) {
			return vencimiento.get();
		}
		
		//Si no existe Seteo por aporte uso Seteos Vto. AFIP			
		vencimiento =  getVtoAFIP(cuit, periodo, feriados);
		if (vencimiento.isPresent() ) {
			return vencimiento.get();
		}
		
		//Genero Error...
		String errorMsg = messageSource.getMessage(BoletaPagoEnumException.VTO_SETEO.getMsgKey(), null, new Locale("es"));
		throw new BusinessException(BoletaPagoEnumException.VTO_SETEO.name(), String.format(errorMsg, "cuit: " +cuit+ ", Aporte: "+aporte+", Periodo: "+periodo) );
	}
	
	private Optional<LocalDate> getVtoAFIP(String cuit, LocalDate periodo, List<FeriadoBO> feriados) {
		Integer diaVtoIni = null;
		LocalDate fechaVtoIni = null;
		List<AfipVencimiento> lstAfipVto = vtoRepo.findByVigenciaBeforeOrderByVigenciaDesc(periodo);
		if ( lstAfipVto == null || lstAfipVto.size() ==0 ) {
			return Optional.empty();
		}			
		diaVtoIni = lstAfipVto.get(0).getDia(); //7 o 9 segun periodo
		
		fechaVtoIni = LocalDate.of(periodo.getYear(), periodo.getMonth(), diaVtoIni);
		fechaVtoIni = fechaVtoIni.plusMonths(1);
		fechaVtoIni = obtenerPrimerDiaHabil(fechaVtoIni, feriados);

		diaVtoIni = getDiaSegunDigitoCuit(cuit,  periodo); //Dia Vto: del 1 al 5 segun digito verificador.-
		for( Integer a =1; a<diaVtoIni; a++) {
			fechaVtoIni = fechaVtoIni.plusDays(1);
			fechaVtoIni = obtenerPrimerDiaHabil(fechaVtoIni, feriados);
		}
		return Optional.of(fechaVtoIni);
	}
	
	
	private Optional<LocalDate> getVtoPorAporte(String aporte, LocalDate periodo, List<FeriadoBO> feriados) {
		//Busco Vto segun Seteos por aporte.-
		Integer diaVto = -1;
		LocalDate fechaVto = null;
		Optional<AporteVencimiento> aporteVto =  vtoPorAporteRepo.findContenido(aporte, periodo );
		if ( aporteVto.isEmpty() ) {
			return Optional.empty();
		}
		diaVto = aporteVto.get().getDia();
		fechaVto = LocalDate.of(periodo.getYear(), periodo.getMonth(), diaVto);
		fechaVto = fechaVto.plusMonths(1);
		fechaVto = obtenerPrimerDiaHabil(fechaVto, feriados);
		
		return Optional.of(fechaVto);
	}
	

	private Integer getDiaSegunDigitoCuit(String cuit, LocalDate periodo) {
		// Ahora segun el digito verificador del cuil/cuit obtengo el
		// vencimiento real
		int dig = Integer.parseInt(cuit.substring(cuit.length() - 1));
		int diaSegunDigito = 0;
		
		//Cambio que se aplicaba desde 2018. Pero lo metimos en 14/01/2019
		if( periodo.isBefore( getDiaCorte() ) ) {
			if (dig == 0 || dig == 1) {
				diaSegunDigito = 1; 
			} else if (dig == 2 || dig == 3) {
				diaSegunDigito = 2;
			} else if (dig == 4 || dig == 5) {
				diaSegunDigito = 3;
			} else if (dig == 6 || dig == 7) {
				diaSegunDigito = 4;
			} else {
				diaSegunDigito = 5;
			}
		}else{
			if (dig == 0 || dig == 1 || dig == 2 || dig == 3) {
				diaSegunDigito = 1;
			} else if (dig == 4 || dig == 5 || dig == 6 ) {
				diaSegunDigito = 2;
			} else if (dig == 7 || dig == 8 || dig == 9) {
				diaSegunDigito = 3;
			} 
		}		
		return  diaSegunDigito;
	}
	
	private LocalDate getDiaCorte() {
		return  LocalDate.of(2017, Month.DECEMBER, 31);
	}

	private LocalDate obtenerPrimerDiaHabil(LocalDate fechaVto, List<FeriadoBO> feriados) {
		LocalDate fechaVtoAux = fechaVto;	 
		while ( esFeriado(fechaVtoAux, feriados) 
				|| DayOfWeek.SATURDAY.equals(fechaVtoAux.getDayOfWeek()) 
				|| DayOfWeek.SUNDAY.equals(fechaVtoAux.getDayOfWeek())
				) {
			fechaVtoAux = fechaVtoAux.plusDays(1L);
		} 
		return fechaVtoAux;
	}
	
	private boolean esFeriado(LocalDate fechaVto, List<FeriadoBO> feriados) {
		for (FeriadoBO reg : feriados) {
			if ( fechaVto.equals(reg.getFecha() ) ) {
				return true;
			}
		}
		return false;
	}

}
