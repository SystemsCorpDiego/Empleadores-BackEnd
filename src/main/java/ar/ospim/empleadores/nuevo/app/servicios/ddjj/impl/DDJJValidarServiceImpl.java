package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJStorageEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJValidarNomina;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJValidarService;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.DDJJEstadoEnum;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DDJJValidarServiceImpl implements DDJJValidarService {
	private final MessageSource messageSource;
	private final DDJJValidarNomina validarNomina; 
	private final DDJJStorage storage;
	private final DateTimeProvider dtProvider;
	
	@Override
	public void run(DDJJBO ddjj) {
		validarCabecera(ddjj);		
		validarNomina(ddjj); 		
	}

	private void validarCabecera(DDJJBO ddjj) {
		//Se crean/Actualizar Solo las DDJJ PENDIENTE
		if( !DDJJEstadoEnum.PENDIENTE.getCodigo().equals( ddjj.getEstado() ) ) {
			String errorMsg = messageSource.getMessage(DDJJStorageEnumException.ESTADO_PRESENTADA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(DDJJStorageEnumException.ESTADO_PRESENTADA.name(), errorMsg);
		}

		//Debe Indicar un periodo 
		if ( ddjj.getPeriodo() == null) {
			String errorMsg = messageSource.getMessage( CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Período") );
		}
				
		//el periodo debe ser ANTERIOR al 1ro del mes en curso.-			
		if ( !ddjj.getPeriodo().isBefore( LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).plusMonths(1) ) ) {
			String errorMsg = messageSource.getMessage(DDJJStorageEnumException.PERIODO_FUTURO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(DDJJStorageEnumException.PERIODO_FUTURO.name(), errorMsg);
		}
		
		//validar periodo: Solamente 1 DDJJ PENDIENTE (en proceso de carga) x periodo
		List<Integer> cons = storage.findPeriodoPendiente(ddjj.getEmpresa().getId(), ddjj.getPeriodo());
		if ( cons != null && cons.size()>0) {
			//Si es alta => error
			if ( ddjj.getId() == null ) { 
				String errorMsg = messageSource.getMessage(DDJJStorageEnumException.ESTADO_PENDIENTE_DUP.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(DDJJStorageEnumException.ESTADO_PENDIENTE_DUP.name(), String.format(errorMsg, dtProvider.getPeriodoToString(ddjj.getPeriodo()) ));					
			}
			
			//Si es modi y es otro id => error
			cons = cons.stream().filter(id -> id.equals(ddjj.getId() )).collect(Collectors.toList());
			if ( cons.size()>1 ) {
				String errorMsg = messageSource.getMessage(DDJJStorageEnumException.ESTADO_PENDIENTE_DUP.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(DDJJStorageEnumException.ESTADO_PENDIENTE_DUP.name(), String.format(errorMsg, ddjj.getPeriodo()));					
			} 
		}
	}

	private void validarNomina(DDJJBO ddjj) {
		//dEBE HABER AL MENOS UN AFILIADO.
		if ( ddjj.getEmpleados() == null || ddjj.getEmpleados().size() == 0 ) {
			String errorMsg = messageSource.getMessage(DDJJStorageEnumException.NO_AFI_DETA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(DDJJStorageEnumException.NO_AFI_DETA.name(), errorMsg);
		}
		
		//Los CUILES deben ser validos.-
		if ( !validarNomina.esValida(ddjj)) {
			String errorMsg = messageSource.getMessage(DDJJStorageEnumException.CUILES_INVALIDOS.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(DDJJStorageEnumException.CUILES_INVALIDOS.name(), errorMsg);
		}

		//Afiliacion: por NULO asumo respuesta NO.- 
		for ( DDJJEmpleadoBO ddjjEmpleado : ddjj.getEmpleados() ) {			
			if (ddjjEmpleado.getAmtimaSocio() == null)
				ddjjEmpleado.setAmtimaSocio(false);
			if (ddjjEmpleado.getUomaSocio() == null)
				ddjjEmpleado.setUomaSocio(false);
		}
		
	}
}
