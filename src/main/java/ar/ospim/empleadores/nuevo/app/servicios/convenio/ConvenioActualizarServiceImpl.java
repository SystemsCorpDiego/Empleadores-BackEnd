package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.comun.auth.usuario.SecurityContextUtils;
import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.CalcularCuotasCalculadaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioModiDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.PlanPagoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioPeriodoDetalle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ConvenioActualizarServiceImpl implements ConvenioActualizarService {
	private final MessageSource messageSource;
	private final ConvenioMapper mapper;
	
	private final ConvenioStorage storage;	
	private final FormaPagoService formaPagoService;
	private final ConvenioInteresService convenioInteresService;
	private final ConvenioCuotasCalcular cuotasCalcular;
	private final ConvenioDetalleService detalleService;
	
	private final ConvenioSeteoService convenioSeteoService;
	private final DateTimeProvider dtProvider;
	
	@Override
	@Transactional
	public Convenio run(ConvenioModiDto dto) {
		Convenio convenio = storage.get(dto.getConvenioId());
		
		validarActualizacion(dto, convenio.getEmpresa().getId(), convenio.getEmpresa().getCuit() );
		
		convenio.setUpdatedBy( SecurityContextUtils.getUserDetails().userId  );
		
		detalleService.runActas(convenio, dto.getActas());
		detalleService.runAjustes(convenio, dto.getAjustes());
		detalleService.runPeriodos(convenio, dto.getDdjjs() );
		actualizarTotales(convenio);
		
		PlanPagoDto planPagoNew = mapper.run2(dto);
		cuotasCalcular.run(convenio, planPagoNew);
		
		convenio = storage.guardar(convenio);		
		return convenio;
	}
	
	 
	private void actualizarTotales(Convenio convenio) {
		BigDecimal capitalDeuda = BigDecimal.ZERO;		
		BigDecimal saldoAFavorDeuda = BigDecimal.ZERO;
		BigDecimal interesDeuda = BigDecimal.ZERO;
		
		 if ( convenio.getActas() != null ) {
			 for (ConvenioActa ca:  convenio.getActas()) {
				 if ( ca.getActa() != null ) {
					 if ( ca.getActa().getCapital() != null )
						 capitalDeuda = capitalDeuda.add(ca.getActa().getCapital());
					 if ( ca.getActa().getInteres() != null )
						 capitalDeuda = capitalDeuda.add(ca.getActa().getInteres());
				 }
			 }
		 }
		 
		 if ( convenio.getPeriodos() != null ) {
			 for (ConvenioPeriodoDetalle cp:  convenio.getPeriodos()) {
				 if ( cp.getImporte()!=null)
					 capitalDeuda = capitalDeuda.add(cp.getImporte());
				 if ( cp.getInteres()!=null)
					 capitalDeuda = capitalDeuda.add(cp.getInteres());
			 }
		 }			 
		 
		if ( convenio.getAjustes() != null ) { 
				for (ConvenioAjuste caj:  convenio.getAjustes()) {
					if ( caj.getImporte()  != null )
						saldoAFavorDeuda = saldoAFavorDeuda.add(caj.getImporte());
				}
		}
		 
		
		 convenio.setImporteSaldoFavor(saldoAFavorDeuda);		 
		 convenio.setImporteDeuda(capitalDeuda);	
		 convenio.setImporteIntereses(interesDeuda);
	}
	
	private void validarActualizacion(ConvenioModiDto dto, Integer empresaId, String cuit) {
		
		if ( empresaId==null ||  !empresaId.equals(dto.getEmpresaId()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.USUARIO_EMPRESA_DIFERENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.USUARIO_EMPRESA_DIFERENTE.name(), errorMsg);			
		}
		
		
		if ( dto.getFechaPago() == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Intención de Pago") );			
		}
		
		//Intencion de Pago: validar fecha Pasada		
		if ( dto.getFechaPago().isBefore(LocalDate.now()) ) {			
			String errorMsg = messageSource.getMessage(CommonEnumException.ERROR_FECHA_PASADA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ERROR_FECHA_PASADA.name(), String.format(errorMsg, "Intención de Pago") );			
		}
		
		//Intencion de Pago: validar que la fecha no sea superior al seteo de config Convenios		
		
				if (  ! convenioSeteoService.validarFechaPago(cuit, dto.getFechaPago() ) ) {
					LocalDate fMaxima = convenioSeteoService.getFechaPagoMaxima(cuit );
					String errorMsg = messageSource.getMessage(ConvenioEnumException.SETEO_FECHAPAGO_CANTMAXDIAS.getMsgKey(), null, new Locale("es"));
					throw new BusinessException(ConvenioEnumException.SETEO_FECHAPAGO_CANTMAXDIAS.name(), String.format(errorMsg, dtProvider.getDateToString(fMaxima) ) );			
				}
		
		if (! formaPagoService.existe( dto.getMedioDePago() ) ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.FORMA_PAGO_INEXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.FORMA_PAGO_INEXISTENTE.name(), errorMsg );					
		}
	}
	

	
	public List<CalcularCuotasCalculadaDto> calcularCuotas(String cuit, BigDecimal capital, Integer cuotas, LocalDate vencimiento ) {
		if ( capital == null || capital.compareTo(BigDecimal.ZERO) < 1 ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_MAYOR_A_CERO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_MAYOR_A_CERO.name(), String.format(errorMsg, "El Capital de las Cuotas ") );			
		}
		if ( cuotas == null || cuotas < 1 ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_MAYOR_A_CERO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_MAYOR_A_CERO.name(), String.format(errorMsg, "La cantida de Cuotas") );			
		}
		

		if ( vencimiento == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Intención de Pago") );			
		}

		if ( vencimiento.isBefore(LocalDate.now()) ) {			
			String errorMsg = messageSource.getMessage(CommonEnumException.ERROR_FECHA_PASADA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ERROR_FECHA_PASADA.name(), String.format(errorMsg, "Intención de Pago") );			
		}
		
		List<CalcularCuotasCalculadaDto> lst = new ArrayList<CalcularCuotasCalculadaDto>();
		CalcularCuotasCalculadaDto cuota = new CalcularCuotasCalculadaDto();
		BigDecimal capitalCuota = capital.divide( BigDecimal.valueOf(cuotas), 2, RoundingMode.HALF_UP);
		LocalDate calculoFechaInicio = LocalDate.now();
		LocalDate calculoFechaVtoCuota = vencimiento;
		int cuotaNro = 1;
		
		cuota.setNumero(cuotaNro);
		cuota.setImporte(capitalCuota);
		cuota.setVencimiento(calculoFechaVtoCuota);
		cuota.setInteres( convenioInteresService.calcularInteres(cuit, capitalCuota, calculoFechaInicio, calculoFechaVtoCuota) );
		lst.add(cuota);
		
		for ( cuotaNro = 2;  cuotaNro<=cuotas;  cuotaNro++) {
			cuota = new CalcularCuotasCalculadaDto();
			calculoFechaVtoCuota = calculoFechaVtoCuota.plusMonths(1);
			
			cuota.setNumero(cuotaNro);
			cuota.setImporte(capitalCuota);
			cuota.setVencimiento(calculoFechaVtoCuota);
			cuota.setInteres( convenioInteresService.calcularInteres(cuit, capitalCuota, calculoFechaInicio, calculoFechaVtoCuota) );
			
			lst.add(cuota);
		}
			
		return lst;	
	}
	
	 
}
