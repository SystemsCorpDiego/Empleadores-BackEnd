package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.deuda.DeudaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.CalcularCuotasCalculadaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioActaDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAjusteDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaChequeAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDDJJDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaAjustesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.PlanPagoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioDeudaMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper.ConvenioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ConvenioChequeEstadoEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ConvenioEstadoEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ActaMolinerosRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AjusteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioActaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioAjusteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioCuotaChequeRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioCuotaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Ajuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuota;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioPeriodoDetalle;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ConvenioServiceImpl implements ConvenioService {
 
	private final MessageSource messageSource;
	private final ConvenioMapper mapper;
	private final ConvenioDeudaMapper convenioDeudaMapper;
	private final DateTimeProvider dateTimeProvider;
	
	private final ConvenioStorage storage;
	private final ActaMolinerosRepository actaMolinerosRepository;
	private final ConvenioActaRepository convenioActaRepository;  	
	private final ConvenioAjusteRepository convenioAjusteRepository;
	private final ConvenioCuotaRepository convenioCuotaRepository;
	private final ConvenioCuotaChequeRepository convenioCuotaChequeRepository;
	private final DeudaNominaRepository deudaNominaRepository;
	private final AjusteRepository ajusteRepository; 
	private final DeudaService deudaService;
	private final UsuarioInfo usuarioInfo;  
	private final ConvenioCuotasCalcular cuotasCalcular;


	public Convenio cambiarEstado(Integer empresaId, Integer convenioId, String estado) {		
		//TODO: faltaria validar EmpresaId
		return cambiarEstado(convenioId, estado);		
	}
	
	public Convenio cambiarEstado(Integer convenioId, String estado) {
		//TODO: falta enviar convenio a molineros !!!
		//TODO: Si estado es APROBADO => mandar mail a GENTE !!!!
		
		Convenio  convenio = storage.getById(convenioId);
		
		validarEstado(estado);
		validarCambioEstado(convenio, estado);

		if ( ConvenioEstadoEnum.PRES.getCodigo().equals(estado) ) {
			convenio.setPresentadoFecha( LocalDateTime.now() );
		}
		
		convenio.setEstado(estado);
		convenio.setEstadoFecha(LocalDateTime.now());
		
		convenio = storage.guardar(convenio);
		 
		
		return convenio;		
	}
	
	public List<CalcularCuotasCalculadaDto> calcularCuotas(Integer empresaId, BigDecimal capital, Integer cuotas, LocalDate vencimiento ) {
		return cuotasCalcular.run(empresaId, capital, cuotas, vencimiento );	
	}

	
	public ConvenioDeudaDto getConvenioDeudaDto(Convenio convenio) {
		ConvenioDeudaDto rta = mapper.run3(convenio);
		
		rta.setDeclaracionesJuradas( convenioDeudaMapper.run2(convenio.getPeriodos() ) );
		
		//Ahora Agrego registros posibles...		
		List<ActaMolineros> lstActas = deudaService.getMolinerosActas(convenio.getEmpresa().getId(), convenio.getEntidad());
		if ( lstActas != null && lstActas.size()>0 ) {
			if ( rta.getActas() == null )
				rta.setActas( new ArrayList<ConvenioActaDeudaDto>() );
			List<ConvenioActaDeudaDto> lstActasPosibles = mapper.run8(lstActas);
			for ( ConvenioActaDeudaDto reg : lstActasPosibles) {
				reg.setConvenioActaId(null);
				rta.getActas().add(reg);
			}
		}
		
		List<IGestionDeudaAjustesDto>  lstAjustes = deudaService.getAjustesDto(convenio.getEmpresa().getId(), convenio.getEntidad());
		if ( lstAjustes != null && lstAjustes.size()>0 ) {
			if ( rta.getSaldosAFavor() == null )
				rta.setSaldosAFavor( new ArrayList<ConvenioAjusteDeudaDto>() );
			List<ConvenioAjusteDeudaDto> lstAjustesPosibles =  mapper.run9(lstAjustes); 
			for ( ConvenioAjusteDeudaDto reg : lstAjustesPosibles) {
				reg.setConvenioAjusteId(null);
				rta.getSaldosAFavor().add(reg);
			}
		}
		
		
		List<IGestionDeudaDDJJDto>  lstDdjjs = deudaService.getDDJJDto(convenio.getEmpresa().getId(), convenio.getEntidad());
		if ( lstDdjjs != null && lstDdjjs.size()>0 ) {
			if ( rta.getDeclaracionesJuradas() == null )
				rta.setDeclaracionesJuradas( new ArrayList<ConvenioDDJJDeudaDto>() );
			
			List<ConvenioDDJJDeudaDto> lstDdjjsPosibles =  mapper.run10(lstDdjjs);
			lstDdjjsPosibles =  mapper.runCastIdString(lstDdjjsPosibles);
			for ( ConvenioDDJJDeudaDto reg : lstDdjjsPosibles) {
				reg.setConvenioDdjjId(null);
				rta.getDeclaracionesJuradas().add(reg);
			}
		}
	
		
		return rta;
	}
	
	public Convenio get(Integer empresaId, Integer convenioId) {	
		Convenio convenio = storage.get(convenioId);		
		if ( convenio.getCuotas() != null)
			orderByNroCuota(convenio.getCuotas());			
		return convenio;
	}
	
	
	public List<Convenio> get(ConvenioConsultaFiltroDto filtro) {
		List<Convenio> lst = null;		
		lst = storage.get(filtro);		
		return lst;
	}
	
	
	public List<ConvenioCuotaConsultaDto> getCuotas(Integer empresaId, Integer convenioId){
		List<ConvenioCuotaConsultaDto> rta = null;
		List<ConvenioCuota> lstCuotas = convenioCuotaRepository.findByConvenioIdOrderByCuotaNro(convenioId);
		rta = mapper.run6(lstCuotas);
		
		String listCheques = "";
		BigDecimal impTotal = BigDecimal.ZERO; 
		List<ConvenioCuotaCheque> lstCheques = null;
		for(ConvenioCuotaConsultaDto convenioCuota: rta) {
			listCheques = "";
			impTotal = BigDecimal.ZERO;
			lstCheques = convenioCuotaChequeRepository.findByConvenioCuotaId(convenioCuota.getId());
			for(ConvenioCuotaCheque cheque: lstCheques) {
				listCheques = listCheques + ", " + cheque.getNumero();
				impTotal = impTotal.add(cheque.getImporte());				
			}
			if ( !listCheques.equals("")) {
				listCheques = listCheques.substring(2);
			}
			convenioCuota.setChequesNro(listCheques);
			convenioCuota.setChequesTotal(impTotal);
		}
		
		return rta;
	}

	public ConvenioCuotaCheque generar(ConvenioCuotaChequeAltaDto cheque) {
		ConvenioCuotaCheque rta = null;
		ConvenioCuotaCheque reg = new ConvenioCuotaCheque();
		
		ConvenioCuota convenioCuota = convenioCuotaRepository.getById(cheque.getCuotaId());
		reg.setConvenioCuota(convenioCuota);
		
		reg.setFecha(cheque.getFecha());
		reg.setImporte(cheque.getImporte());
		reg.setNumero(cheque.getNumero());
		
		if ( reg.getNumero() != null ) {
			reg.setEstado( ConvenioChequeEstadoEnum.PENDIENTE.getCodigo() );
		} else {
			reg.setEstado( ConvenioChequeEstadoEnum.CARGADO.getCodigo() );
		}
				
		validarImporte(reg);
		
		rta = convenioCuotaChequeRepository.save(reg);
		
		return rta;
	}
	
	public ConvenioCuotaCheque actualizarCuotaCheque(ConvenioCuotaChequeAltaDto cheque) {
		ConvenioCuotaCheque reg = convenioCuotaChequeRepository.getById(cheque.getChequeId());
		reg.setNumero(cheque.getNumero());
		reg.setFecha(cheque.getFecha());
		reg.setImporte(cheque.getImporte());
		reg.setEstado(cheque.getEstado());
		
		validarImporte(reg);
		
		reg = convenioCuotaChequeRepository.save(reg);
		return reg;
	}

	
	public List<ConvenioCuotaCheque> getCheques(Integer empresaId, Integer convenioId, Integer cuotaId) {
		 List<ConvenioCuotaCheque> rta = null;
		 rta = convenioCuotaChequeRepository.findByConvenioCuotaConvenioIdAndConvenioCuotaId(convenioId, cuotaId);
		 return rta;
	}

	public ConvenioCuotaCheque getCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId) {
		 ConvenioCuotaCheque rta = null;
		 rta = convenioCuotaChequeRepository.getById(chequeId);
		 return rta;
	}
	
	public void borrarCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId) {		
		convenioCuotaChequeRepository.deleteById(chequeId);
	}
		
	public  void borrarActa(Integer empresaId, Integer convenioId, Integer actaId) {
		convenioActaRepository.deleteById(actaId);
		actualizarConvenioImportes(convenioId);
	}
	
	public  ConvenioActa asignarActa(Integer empresaId, Integer convenioId, Integer actaId) {
		ConvenioActa acta = new ConvenioActa();
		
		Convenio convenio = storage.get(convenioId);
		acta.setConvenio(convenio);
		
		ActaMolineros actaMolineros = actaMolinerosRepository.getById(actaId);
		acta.setActa(actaMolineros);
		
		acta = convenioActaRepository.save(acta);
		if( convenio.getActas() == null)
			convenio.setActas( new ArrayList<ConvenioActa>() );		
		convenio.getActas().add(acta);

		actualizarConvenioImportes(convenio);
		
		return acta;
	}

	public  void borrarAjuste(Integer empresaId, Integer convenioId, Integer ajusteId) {
		convenioAjusteRepository.deleteById(ajusteId);
		actualizarConvenioImportes(convenioId);
	}
	
	public  ConvenioAjuste asignarAjuste(Integer empresaId, Integer convenioId, Integer ajusteId) {
		ConvenioAjuste ajuste = new ConvenioAjuste();
		
		Convenio convenio = storage.get(convenioId);
		//imprimirconvenio(convenio); 
		ajuste.setConvenio(convenio);
		
		Ajuste ajusteAux = ajusteRepository.getById(ajusteId);
		ajuste.setAjuste(ajusteAux);
		
		 BigDecimal importeUtilizado = ajusteRepository.importeUsado(ajusteId);			 
		 ajuste.setImporte(BigDecimal.ZERO);
		 ajuste.setImporte( ajuste.getAjuste().getImporte().add(importeUtilizado) ) ;
		 ajuste.setImporte( ajuste.getImporte().multiply(BigDecimal.valueOf(-1)) );

		 
		ajuste = convenioAjusteRepository.save(ajuste);
		if( convenio.getAjustes() == null)
			convenio.setAjustes( new ArrayList<ConvenioAjuste>() );		
		convenio.getAjustes().add(ajuste);

		actualizarConvenioImportes(convenio);
		return ajuste;
	}
	
	
	private Convenio calcularConvenioImportes(Convenio convenio) {
		//Recalcula los importes del convenio ( tabla convenio y convenio_cuota
		actualizarTotales(convenio);
		 
		BigDecimal capital = getCapitalConvenio(convenio);
		BigDecimal interes = BigDecimal.ZERO; 
		Integer cantiCuotas = convenio.getCuotasCanti();
		LocalDate vencimiento = convenio.getIntencionDePago();
			
		//TODO: aca hay que usar Intereses Seteo de Convenios 
 
		List<CalcularCuotasCalculadaDto> lstCuotas = cuotasCalcular.run(convenio.getEmpresa().getCuit(), capital, cantiCuotas,  vencimiento );
		for (CalcularCuotasCalculadaDto regCuotaNew : lstCuotas) {
			for (ConvenioCuota regCuota : convenio.getCuotas()) {
				if ( regCuota.getCuotaNro().equals(regCuotaNew.getNumero())  ) {
					regCuota.setImporte(regCuotaNew.getImporte());
					regCuota.setInteres(regCuotaNew.getInteres());
					interes = interes.add(regCuotaNew.getInteres());
				}
			}
		}
		
		convenio.setImporteIntereses( interes );		
		
		return convenio;
	}
	
	private Convenio actualizarConvenioImportes(Integer convenioId) {
		Convenio convenio = storage.get(convenioId);
		//imprimirconvenio( convenio);
		convenio = actualizarConvenioImportes(convenio);
		return convenio;
	}	 
	
	private Convenio actualizarConvenioImportes(Convenio convenio) {
		//Actualizar importes de Cuotas cada vez que se modifican los detalles de Actas, DDJJ y Ajustes.-
		//Los importes estan en tabla convenio y convenio_cuotas
		
		convenio = calcularConvenioImportes(convenio);
		
		//storage.guardar(convenio);
		for (ConvenioCuota reg :  convenio.getCuotas() ) {			
			convenioCuotaRepository.save(reg);
		}
		storage.actualizarImportes(convenio.getId(), convenio.getImporteDeuda(), convenio.getImporteIntereses(), convenio.getImporteSaldoFavor());
		
		return convenio;
	}
	
	public Convenio actualizarPlanPago(Integer empresaId, Integer convenioId, PlanPagoDto planPago) {
		Convenio convenio = storage.get(convenioId);
		convenio = cuotasCalcular.run(convenio, planPago);
		return convenio;		 
	}
	 

	private void actualizarTotales(Convenio convenio) {
		BigDecimal capitalDeuda = BigDecimal.ZERO;
		BigDecimal interesDeuda = BigDecimal.ZERO;
		BigDecimal saldoAFavorDeuda = BigDecimal.ZERO;
		
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
		
		if ( convenio.getCuotas() != null ) {
			for (ConvenioCuota cuota:  convenio.getCuotas()) {
				if ( cuota.getInteres()!=null )
					interesDeuda = interesDeuda.add(cuota.getInteres());					 
			}
		}		
		 
		 convenio.setImporteSaldoFavor(saldoAFavorDeuda);		 
		 convenio.setImporteDeuda(capitalDeuda);
		 convenio.setImporteIntereses(interesDeuda);
	}

	
	private BigDecimal getCapitalConvenio(Convenio convenio) {
		BigDecimal importe = BigDecimal.ZERO;
		if ( convenio.getImporteDeuda() != null)
			importe = importe.add(convenio.getImporteDeuda());
		
		if ( convenio.getImporteSaldoFavor() != null)
			importe = importe.subtract(convenio.getImporteSaldoFavor());
		return importe;
	}
	
	private void orderByNroCuota(List<ConvenioCuota> lst) {
		 Collections.sort(lst, new Comparator<ConvenioCuota>() {
	            @Override
	            public int compare(ConvenioCuota a, ConvenioCuota b) {
	                return a.getCuotaNro().compareTo(b.getCuotaNro()); // For descending order
	            }
	        });
	}
	
	
	private void validarEstado(String estado) {
		ConvenioEstadoEnum.map(estado);
	}
	
	private void validarCambioEstado(Convenio  convenio, String estadoNew) {
		ConvenioEstadoEnum eEstadoNew = ConvenioEstadoEnum.map(estadoNew);
		ConvenioEstadoEnum eEstado = ConvenioEstadoEnum.map(convenio.getEstado());
		
		// Presentado => solo puede hacerlo la empresa
		if ( !usuarioInfo.validarEmpresa(convenio.getEmpresa().getId()) 
				&& eEstadoNew.equals( ConvenioEstadoEnum.PRES) ) {
			String errorMsg = messageSource.getMessage(ConvenioEnumException.ESTADO_CAMBIO_PRESENTAR_SOLO_EMPLEADOR.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(ConvenioEnumException.ESTADO_CAMBIO_PRESENTAR_SOLO_EMPLEADOR.name(), errorMsg );			   						
		}
		
		
		if ( ! eEstado.equals(eEstadoNew) ) {
			if ( ( eEstadoNew.equals( ConvenioEstadoEnum.APROB ) ||
					eEstadoNew.equals( ConvenioEstadoEnum.OBSR ) ||
					eEstadoNew.equals( ConvenioEstadoEnum.RECH ) ) &&
					convenio.getPresentadoFecha() == null
				) {				
				String errorMsg = messageSource.getMessage(ConvenioEnumException.ESTADO_CAMBIO_SIN_PRESENTAR.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(ConvenioEnumException.ESTADO_CAMBIO_SIN_PRESENTAR.name(), errorMsg );			   			
			}
		}
		 
		
		if ( eEstadoNew.getCodigo().equals(ConvenioEstadoEnum.PRES.getCodigo())
				|| eEstadoNew.getCodigo().equals(ConvenioEstadoEnum.APROB.getCodigo())
				) {
			
			//La fecha de Intencion de PAgo debe ser futura.-
			if ( convenio.getIntencionDePago() == null ||
					convenio.getIntencionDePago().isBefore(LocalDate.now())
					) {
				String errorMsg = messageSource.getMessage(ConvenioEnumException.ESTADO_PRESENTADA_FECHAPAGO_VENCIDA.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(ConvenioEnumException.ESTADO_PRESENTADA_FECHAPAGO_VENCIDA.name(), String.format(errorMsg, dateTimeProvider.getDateToString(convenio.getIntencionDePago())) );			   			
			}
			
			//Deben estar cargados TODOS los cheques.-
			BigDecimal importeTotalConvenio = convenio.getImporteDeuda();
			if ( convenio.getImporteSaldoFavor() != null ) 
				importeTotalConvenio = importeTotalConvenio.subtract(convenio.getImporteSaldoFavor());
			if ( convenio.getImporteIntereses() != null ) 
				importeTotalConvenio = importeTotalConvenio.add(convenio.getImporteIntereses());
			
			BigDecimal importeTotalCheques = deudaNominaRepository.countChequesImporteTotal(convenio.getId() );
			//BigDecimal.ROUND_UNNECESSARY
						
			
			if ( importeTotalConvenio.subtract(importeTotalCheques).abs().compareTo(new BigDecimal(2)) == 1 ) {
				String errorMsg = messageSource.getMessage(ConvenioEnumException.ESTADO_PRESENTADA_IMPCUOTAS_DIF_IMPCHEQUES.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(ConvenioEnumException.ESTADO_PRESENTADA_IMPCUOTAS_DIF_IMPCHEQUES.name(), String.format(errorMsg, importeTotalConvenio, importeTotalCheques));			   			
			}
		}
	}
	
	private void validarImporte(ConvenioCuotaCheque reg) {		
		BigDecimal ImporteTotalCuota = reg.getConvenioCuota().getImporteTotalCuota();
		BigDecimal ImporteTotalCheques = reg.getImporte();
		
		if (reg.getImporte() == null || reg.getImporte().compareTo(BigDecimal.ZERO) < 1 ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.IMPORTE_NEGATIVO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.IMPORTE_NEGATIVO.name(), String.format(errorMsg, "Importe"));			
		}		
		
		List<ConvenioCuotaCheque> lst = convenioCuotaChequeRepository.findByConvenioCuotaId(reg.getId());
		for (ConvenioCuotaCheque cheque: lst) {
			if ( reg.getId() == null || !cheque.getId().equals(reg.getId()) ) {
				ImporteTotalCheques = ImporteTotalCheques.add(cheque.getImporte());
			} 
		}
		if ( ImporteTotalCheques.compareTo(ImporteTotalCuota) == 1 ) {
			String errorMsg = messageSource.getMessage(ConvenioEnumException.CUOTA_IMPORTE_MENOR_TOTAL_CHEQUES.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(ConvenioEnumException.CUOTA_IMPORTE_MENOR_TOTAL_CHEQUES.name(), String.format(errorMsg, ImporteTotalCheques, ImporteTotalCuota));			   			
		}
	}
}
