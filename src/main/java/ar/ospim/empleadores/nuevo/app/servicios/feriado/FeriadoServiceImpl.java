package ar.ospim.empleadores.nuevo.app.servicios.feriado;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.FeriadoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.FeriadoStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FeriadoServiceImpl implements FeriadoService {

	private final MessageSource messageSource;
	private final FeriadoStorage storage;
	private final DateTimeProvider dt;
	
	@Override
	public FeriadoBO guardar(FeriadoBO feriado) {	
		
		Optional<FeriadoBO> aux =  storage.get(feriado.getFecha());
		if ( aux.isPresent() ) {
			if( feriado.getId() == null || !feriado.getId().equals(aux.get().getId() )  ) {
				String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_DUPLICADO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.REGISTRO_DUPLICADO.name(), String.format(errorMsg, dt.getDateToString(feriado.getFecha())  ));				
			}
		}
			 
		return storage.save(feriado);
	}
	
	public void borrar(Integer feriadoId) {
		if (feriadoId == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg);
		}
		
		storage.deleteById(feriadoId);
	}
	
	public List<FeriadoBO> consultar() {
		return storage.findAll();
	}

	@Override
	public LocalDate obtenerSiguienteDiaHabil(LocalDate dia) {
		LocalDate aux = LocalDate.now();
		List<FeriadoBO> feriados = this.consultar();
		while (esFeriadoOFinde(aux, feriados)) {
			dia = dia.plusDays(1L);
		}
		return aux;
	}

	protected boolean esFeriadoOFinde(LocalDate dia, List<FeriadoBO> feriados) {
		if ( dia.getDayOfWeek().equals(DayOfWeek.SATURDAY) ) {
			return true;
		} else if ( dia.getDayOfWeek().equals(DayOfWeek.SUNDAY) ) {
			return true;
		}

		for (FeriadoBO feriado : feriados) {
			LocalDate aux = LocalDate.now();
			if(null!=feriado.getFecha()){				
				if ( feriado.getFecha().getMonth().equals(dia.getMonth()) 
						&& feriado.getFecha().getYear() == dia.getYear() 
						&& feriado.getFecha().getDayOfMonth() ==dia.getDayOfMonth() ) {
					return true;
				}
			}
		}
		return false;
	}

	public List<FeriadoBO> getFeriadosDesde(LocalDate desde) {
		return storage.find(desde);
	}

	@Override
	public void duplicarAnio(Integer anio) {
		LocalDate desde = dt.getAnioNuevo(anio);
		LocalDate hasta = desde.plusYears(1).plusDays(-1) ;
		
		Integer anioActual = LocalDate.now().getYear(); 
		
		if ( anio >= anioActual) {
			String errorMsg = messageSource.getMessage(FeriadoStorageEnumException.ANIO_ORIGEN_FUTURO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(FeriadoStorageEnumException.ANIO_ORIGEN_FUTURO.name(), errorMsg);
		}
		
		Optional<FeriadoBO> cons;
		List<FeriadoBO> lst = storage.find(desde, hasta);
		for (FeriadoBO reg: lst) {
			reg.setId(null);
			reg.setFecha( reg.getFecha().plusYears( anioActual-anio ) );
			cons = storage.get( reg.getFecha() );
			if ( cons.isEmpty() ) {
				storage.save(reg);
			}
		}
	}

	@Override
	public List<FeriadoBO> consultarAnio(Integer anio) {
		// TODO Auto-generated method stub
		LocalDate desde = dt.getAnioNuevo(anio);
		LocalDate hasta = desde.plusYears(1).plusDays(-1) ;
		return storage.find(desde, hasta);
		
	} 
	
}
