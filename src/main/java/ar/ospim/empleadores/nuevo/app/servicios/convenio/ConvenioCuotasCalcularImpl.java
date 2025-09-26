package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.CalcularCuotasCalculadaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.PlanPagoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioCuotaChequeRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioCuotaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuota;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ConvenioCuotasCalcularImpl implements ConvenioCuotasCalcular {

	private final MessageSource messageSource;
	private final ConvenioCuotaChequeRepository convenioCuotaChequeRepository;
	private final ConvenioCuotaRepository convenioCuotaRepository;
	private final ConvenioStorage storage;
	private final EmpresaRepository empresaRepository;
	private final ConvenioInteresService convenioInteresService;
	
	
	@Override
	public Convenio run(Convenio convenio, PlanPagoDto planPago) {
		boolean esAlta = false;
		if ( convenio.getId() == null) 
			esAlta = true;

		// modificacion: convenio tiene un plan Pago Viejo => se actualiza al NUEVO 
		convenio.setIntencionDePago( planPago.getIntencionPago() );
		convenio.setMedioPago(planPago.getMedioPago());
		
		if ( !esAlta ) {
			if ( convenio.getCuotasCanti() != null && planPago.getCantidadCuota() < convenio.getCuotasCanti() ) {
				//borro las cuotas sobrantes y sus cheques 			
				for (ConvenioCuota reg : convenio.getCuotas()) {
					if ( reg.getCuotaNro() > planPago.getCantidadCuota()) {
						convenioCuotaChequeRepository.deleteByConvenioCuotaId(reg.getId());					
						convenioCuotaRepository.deleteById(reg.getId());					
					}
				}
				convenioCuotaChequeRepository.flush();
				convenioCuotaRepository.flush();
				
				List<ConvenioCuota> filtered = convenio.getCuotas().stream()
		                .filter(c -> c.getCuotaNro() <= planPago.getCantidadCuota() )
		                .collect(Collectors.toList());
				convenio.setCuotas(filtered);
			}
		}
		
		
		convenio.setCuotasCanti(planPago.getCantidadCuota());		
		actualizarCuotas(convenio);		
		
		if ( !esAlta ) 
			convenio = grabar(convenio);
		
		
		return convenio;
	}

	public Convenio grabar(Convenio convenio) {
		
		if ( convenio.getCuotas() != null ) {
			 for (ConvenioCuota caj:  convenio.getCuotas()) {
					caj = convenioCuotaRepository.save(caj);
			}
		}
		convenioCuotaRepository.flush();
		
		storage.actualizarModoPago(convenio.getId(), convenio.getCuotasCanti(), convenio.getIntencionDePago());
		
		return convenio;
	}
	
	@Override
	public Convenio run(Convenio convenio) {
		boolean esAlta = false;
		if ( convenio.getId() == null ) 
			esAlta = true;
		
		// alta: convenio tiene un plan Pago a construir 
		actualizarCuotas(convenio);		
		
		if ( !esAlta )
			convenio = grabar(convenio);
		
		return convenio;
	}


	@Override
	public List<CalcularCuotasCalculadaDto> run(Integer empresaId, BigDecimal capital, Integer cuotas, LocalDate vencimiento ) {
		
		if (empresaId == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, " empresaId") );			
		}
		
		String cuit = null;
		try {
			Empresa empresa = empresaRepository.getById(empresaId);
			cuit = empresa.getCuit();
		} catch (Exception e) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), String.format(errorMsg,  " Empresa ( " + empresaId + ") " ) );			
		}
		
		return run(cuit, capital, cuotas, vencimiento );
	}
	
	@Override
	public List<CalcularCuotasCalculadaDto> run(String cuit, BigDecimal capital, Integer cuotas, LocalDate vencimiento ) {
		if ( capital == null || capital.compareTo(BigDecimal.ZERO) < 1 ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_MAYOR_A_CERO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_MAYOR_A_CERO.name(), String.format(errorMsg, "El Capital de las Cuotas ") );			
		}
		if ( cuotas == null || cuotas < 1 ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_MAYOR_A_CERO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_MAYOR_A_CERO.name(), String.format(errorMsg, "La cantida de Cuotas") );			
		}
		if ( vencimiento == null || vencimiento.isBefore(LocalDate.now()) ) {			
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
	
	private ConvenioCuota setCuota(Convenio convenio, CalcularCuotasCalculadaDto dto) {
		ConvenioCuota cuota = null;
		
		if ( convenio.getCuotas() != null ) {
			for ( ConvenioCuota cuotaReg: convenio.getCuotas()) {
				if  ( cuotaReg.getCuotaNro().equals(dto.getNumero()) ) {
					cuota = cuotaReg;
				}
			}
		}
		if ( cuota == null ) {
			cuota = new ConvenioCuota();
		}
		cuota.setConvenio(convenio);
		cuota.setCuotaNro(dto.getNumero());
		cuota.setImporte(dto.getImporte());
		cuota.setInteres( dto.getInteres() );
		cuota.setVencimiento( dto.getVencimiento() );
		return cuota;
	}
	
	private void actualizarCuotas(Convenio convenio) {
		ConvenioCuota cuota = null;
		BigDecimal capital = getCapitalConvenio(convenio);
		BigDecimal totalInteres = BigDecimal.ZERO; 
		Integer cantiCuotas = convenio.getCuotasCanti();
		LocalDate vencimiento = convenio.getIntencionDePago();
		List<ConvenioCuota> cuotasNew = new ArrayList<ConvenioCuota>();
		
		List<CalcularCuotasCalculadaDto> lst = run(convenio.getEmpresa().getCuit(), capital, cantiCuotas, vencimiento);
		for (CalcularCuotasCalculadaDto cuotaDto : lst) {
			cuota = setCuota(convenio, cuotaDto);
			cuotasNew.add(cuota);
			totalInteres = totalInteres.add(cuota.getInteres());
		}
		convenio.setCuotas(cuotasNew);
		convenio.setImporteIntereses( totalInteres );
	}
	
	private BigDecimal getCapitalConvenio(Convenio convenio) {
		BigDecimal importe = BigDecimal.ZERO;
		if ( convenio.getImporteDeuda() != null)
			importe = importe.add(convenio.getImporteDeuda());
		
		if ( convenio.getImporteSaldoFavor() != null)
			importe = importe.subtract(convenio.getImporteSaldoFavor());
		return importe;
	}

}
