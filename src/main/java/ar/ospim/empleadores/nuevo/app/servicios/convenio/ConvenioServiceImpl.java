package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AfipInteresBO;
import ar.ospim.empleadores.nuevo.app.servicios.afipinteres.AfipInteresService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.deuda.DeudaService;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.CalcularCuotasCalculadaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioActaDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAjusteDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaChequeAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDDJJDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDeudaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioModiDto;
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
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioDdjjDeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioDdjjRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DDJJRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Ajuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuota;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjjDeudaNomina;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;
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
	
	private final EmpresaRepository empresaRepository;
	private final ConvenioStorage storage;
	private final ActaMolinerosRepository actaMolinerosRepository;
	private final ConvenioActaRepository convenioActaRepository;  
	private final ConvenioDdjjRepository convenioDdjjRepository;
	private final ConvenioAjusteRepository convenioAjusteRepository;
	private final ConvenioCuotaRepository convenioCuotaRepository;
	private final ConvenioCuotaChequeRepository convenioCuotaChequeRepository;
	private final DDJJRepository ddjjRepository;
	private final ConvenioDdjjDeudaNominaRepository convenioDdjjDeudaNominaRepository;
	private final DeudaNominaRepository deudaNominaRepository;
	private final AjusteRepository ajusteRepository; 
	private final AfipInteresService afipInteresService;
	private final DeudaService deudaService;
	private final FormaPagoService formaPagoService;
	private final UsuarioInfo usuarioInfo;  
	
	@Override
	@Transactional
	public Convenio actualizar(ConvenioModiDto dto) {
		Convenio convenioOri = storage.get(dto.getConvenioId());

		 
		//TODO: Ver si se validan CHEQUES. Por ahora se borran...
		 
		
		//Borro todos los Detalles del Contrato ...
		convenioActaRepository.deleteByConvenioId(convenioOri.getId());
		convenioAjusteRepository.deleteByConvenioId(convenioOri.getId());
		for ( ConvenioDdjj reg : convenioOri.getDdjjs() ) {
			convenioDdjjRepository.baja( reg.getId() );
		}
		

		
		ConvenioAltaDto dtoAlta = mapper.run(dto);
		dtoAlta.setEntidad(convenioOri.getEntidad());		
		Convenio convenioNew = armarConvenio(dtoAlta);
		convenioNew.setId( convenioOri.getId() );
		convenioNew.setEstado( convenioOri.getEstado() );
		
		convenioNew.setCreatedOn( convenioOri.getCreatedOn() );
		convenioNew.setCreatedBy( convenioOri.getCreatedBy() );
		convenioNew.setUpdatedOn( convenioOri.getUpdatedOn() );
		convenioNew.setUpdatedBy( convenioOri.getUpdatedBy() );
		convenioNew.setDeletedOn( convenioOri.getDeletedOn() );
		convenioNew.setDeletedBy( convenioOri.getDeletedBy() );
		convenioNew.setDeleted( convenioOri.isDeleted() );
		
		//Guardo los Detalles nuevos.-
		guardarConvenioDetalle( convenioNew );
		
		PlanPagoDto planPagoNew = mapper.run2(dto);
		convenioNew.setCuotas(convenioOri.getCuotas());
		actualizarPlanPago( convenioOri, planPagoNew);
		convenioNew.setCuotas(convenioOri.getCuotas());
		
		//storage.actualizarImportes(convenioNew.getId(), convenioNew.getImporteDeuda(), convenioNew.getImporteIntereses(), convenioNew.getImporteSaldoFavor());
		//convenioNew.setAjustes(null);
		//convenioNew.setActas(null);
		//convenioNew.setDdjjs(null);
		//convenioNew.setCuotas(null);
		actualizarTotales(convenioNew);
		convenioNew = storage.guardar(convenioNew);
		

		return convenioNew;		
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
		 if ( convenio.getDdjjs() != null ) { 
			for (ConvenioDdjj cd:  convenio.getDdjjs()) {
				 for (ConvenioDdjjDeudaNomina cddn:  cd.getDdjjDeudaNomina()) {
					 if ( cddn.getAporteImporte() != null )
						 capitalDeuda = capitalDeuda.add(cddn.getAporteImporte());
					 if ( cddn.getInteres()!= null )
						 capitalDeuda = capitalDeuda.add(cddn.getInteres());					 
				 }
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
	 
	@Override
	public Convenio generar(ConvenioAltaDto dto) {
		 
		 Convenio convenio = armarConvenio(dto);
		 calcularCuotas( convenio );		 

		 validarAlta(convenio);

		 convenio = guardarConvenio(convenio);
		 
		 return convenio; 
	}
	
	private Convenio guardarConvenio(Convenio convenio ) {
		convenio = storage.guardar(convenio);
		guardarConvenioDetalle( convenio );
		 return convenio;
	}
	
	private Convenio guardarConvenioDetalle(Convenio convenio ) {
		
		 if ( convenio.getActas() != null ) {
			 for (ConvenioActa ca:  convenio.getActas()) {
				 ca = convenioActaRepository.save(ca);				 
			 }
		 }
		 if ( convenio.getDdjjs() != null ) { 
			for (ConvenioDdjj cd:  convenio.getDdjjs()) {
				 cd = convenioDdjjRepository.save(cd);
				 for (ConvenioDdjjDeudaNomina cddn:  cd.getDdjjDeudaNomina()) {
					 cddn = convenioDdjjDeudaNominaRepository.save(cddn);
				 }
			}
		 }
		 if ( convenio.getAjustes() != null ) { 
				for (ConvenioAjuste caj:  convenio.getAjustes()) {
					caj = convenioAjusteRepository.save(caj);
				}
		}
		
		 if ( convenio.getCuotas() != null ) {
			 for (ConvenioCuota caj:  convenio.getCuotas()) {
					caj = convenioCuotaRepository.save(caj);
				}
		 }
		 
		 return convenio;
	}
	
	private Convenio armarConvenio(ConvenioAltaDto dto) {
		Convenio convenio = new Convenio();
		 
		 Empresa empresa = empresaRepository.getById(dto.getEmpresaId());
		 
		 convenio.setEmpresa(empresa);
		 convenio.setEntidad(dto.getEntidad());
		 convenio.setEstado( ConvenioEstadoEnum.PENDIENTE.getCodigo() );
		 convenio.setEstadoFecha(LocalDateTime.now());
		 
		 //Estos hay que calcularlos segun detalle
		 convenio.setImporteDeuda( BigDecimal.ZERO );
		 convenio.setImporteIntereses( BigDecimal.ZERO );
		 convenio.setImporteSaldoFavor( BigDecimal.ZERO );
		 
		 convenio.setIntencionDePago( dto.getFechaPago() );
		 convenio.setMedioPago( dto.getMedioDePago() );
		 convenio.setCuotasCanti(dto.getCantidadCuota());
		 //convenio.setMedioPago("CHEQUE");
		 
		 armarDetalle(convenio, dto);
		 
		 actualizarTotales(convenio);

		 return convenio;
	}


	private void armarDetalle(Convenio convenio, ConvenioAltaDto dto) {
		List<ConvenioActa> actas = new ArrayList<ConvenioActa>();
		 ConvenioActa aux = null;
		 for(Integer reg : dto.getActas()) {
			 aux = new ConvenioActa(); 
			 aux.setConvenio(convenio);
			 aux.setActa( actaMolinerosRepository.getById(reg) );
			 actas.add(  aux );		     
		 }		 		 
		 convenio.setActas(actas);
		 
		 List<ConvenioDdjj> ddjjs = new ArrayList<ConvenioDdjj>();
		 ConvenioDdjj auxDDJJ = null;
		 for(Integer reg : dto.getDdjjs()) {
			 auxDDJJ = new ConvenioDdjj();
			 auxDDJJ.setConvenio(convenio);
			 auxDDJJ.setDdjj( ddjjRepository.getById(reg) );
			 
			 List<DeudaNomina> lstDeudaNomina = deudaNominaRepository.findByDdjjIdAndEntidadAndActaIdIsNull(reg, convenio.getEntidad() );
			 auxDDJJ.setDdjjDeudaNomina( new ArrayList<ConvenioDdjjDeudaNomina>());
			 ConvenioDdjjDeudaNomina auxCDDN = null;
			 for ( DeudaNomina dn: lstDeudaNomina) {
				 auxCDDN = new ConvenioDdjjDeudaNomina();
				 auxCDDN.setConvenioDdjj(auxDDJJ);
				 
				 auxCDDN.setAporte(dn.getAporte().getCodigo());
				 auxCDDN.setAporteImporte(dn.getImporte());
				 auxCDDN.setInteres(dn.getInteres());
				 auxCDDN.setVencimiento( dn.getVencimiento() );
				 
				 auxDDJJ.getDdjjDeudaNomina().add(auxCDDN);
			 }
			 
			 ddjjs.add( auxDDJJ );
		 }
		 convenio.setDdjjs(ddjjs);
		 
		 
		 List<ConvenioAjuste> ajustes = new ArrayList<ConvenioAjuste>();
		 ConvenioAjuste auxAjuste = null;
		 for(Integer reg : dto.getAjustes()) {
			 auxAjuste = new ConvenioAjuste();
			 auxAjuste.setConvenio(convenio);
			 auxAjuste.setAjuste( ajusteRepository.getById(reg) );
			 
			 BigDecimal importeUtilizado = ajusteRepository.importeUsado(reg);			 
			 auxAjuste.setImporte(BigDecimal.ZERO);
			 auxAjuste.setImporte(auxAjuste.getAjuste().getImporte().add(importeUtilizado) ) ;
			 auxAjuste.setImporte( auxAjuste.getImporte().multiply(BigDecimal.valueOf(-1)) );
			 
			 ajustes.add(auxAjuste);
		 }
		 convenio.setAjustes(ajustes);
	}
	
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
						
			
			//if (importeTotalConvenio.setScale(0, java.math.RoundingMode.DOWN).compareTo(importeTotalCheques.setScale(0, java.math.RoundingMode.DOWN)) != 0) {
			if ( importeTotalConvenio.subtract(importeTotalCheques).abs().compareTo(new BigDecimal(2)) == 1 ) {
				String errorMsg = messageSource.getMessage(ConvenioEnumException.ESTADO_PRESENTADA_IMPCUOTAS_DIF_IMPCHEQUES.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(ConvenioEnumException.ESTADO_PRESENTADA_IMPCUOTAS_DIF_IMPCHEQUES.name(), String.format(errorMsg, importeTotalConvenio, importeTotalCheques));			   			
			}
		}
	}
	
	
	private void calcularCuotas(Convenio convenio) {
		ConvenioCuota cuota = null;
		BigDecimal capital = getCapitalConvenio(convenio);
		BigDecimal totalInteres = BigDecimal.ZERO; 
		Integer cantiCuotas = convenio.getCuotasCanti();
		LocalDate vencimiento = convenio.getIntencionDePago();
		
		/*
		BigDecimal importeCuota = calcularImporteCuota(capital, cantiCuotas, vencimiento);
		convenio.setCuotas( new ArrayList<ConvenioCuota>() );
		for (int cuotaNro = 1; cuotaNro <= cantiCuotas; cuotaNro++) {
			cuota = new ConvenioCuota();
			cuota.setConvenio(convenio);
			cuota.setCuotaNro(cuotaNro);
			cuota.setVencimiento( vencimiento );
			cuota.setImporte( importeCuota );
			convenio.getCuotas().add(cuota);
			vencimiento = vencimiento.plusMonths(1); 
		}
		
		BigDecimal imptotalAPagar = BigDecimal.ZERO;		
		imptotalAPagar = importeCuota.multiply(BigDecimal.valueOf(cantiCuotas));
				
		convenio.setImporteIntereses( imptotalAPagar.subtract( capital ) );
		*/
		List<CalcularCuotasCalculadaDto> lst = calcularCuotas( capital, cantiCuotas, vencimiento);
		convenio.setCuotas( new ArrayList<ConvenioCuota>() );		
		for (CalcularCuotasCalculadaDto cuotaDto : lst) {
			cuota = new ConvenioCuota();
			cuota.setConvenio(convenio);
			cuota.setCuotaNro(cuotaDto.getNumero());
			cuota.setImporte(cuotaDto.getImporte());
			cuota.setInteres( cuotaDto.getInteres() );
			cuota.setVencimiento( cuotaDto.getVencimiento() );
			
			convenio.getCuotas().add(cuota);
			totalInteres = totalInteres.add(cuota.getInteres());
		}
		convenio.setImporteIntereses( totalInteres );
	}
	
	public BigDecimal calcularImporteCuota(BigDecimal capital, Integer cuotas, LocalDate vencimiento ) {
		BigDecimal importeCuota = BigDecimal.ZERO;
		BigDecimal interesCuota1 = null;
		BigDecimal capitalCuota1 = null;		
		
		if ( cuotas.equals(1)) {
			capitalCuota1 = capital;
			interesCuota1 = afipInteresService.calcularInteres(capitalCuota1, LocalDate.now(), vencimiento);
			importeCuota = capitalCuota1.add(interesCuota1) ;
		} else {
			capitalCuota1 =  capital.divide( BigDecimal.valueOf(cuotas), 2, RoundingMode.HALF_EVEN );			
			interesCuota1 = afipInteresService.calcularInteres(capitalCuota1, LocalDate.now(), vencimiento);
			BigDecimal capitalCuotaN = capital.subtract(capitalCuota1);
			
			BigDecimal importeCuotaN =  calcularCuotaImporteSF(capitalCuotaN, (cuotas-1));
			
			importeCuota = importeCuotaN.multiply(BigDecimal.valueOf((cuotas-1)) ).add(capitalCuota1).add(interesCuota1);
			importeCuota = importeCuota.divide(BigDecimal.valueOf(cuotas) , 2, RoundingMode.HALF_EVEN );
		}
		
		return importeCuota;
	}
	
	public List<CalcularCuotasCalculadaDto> calcularCuotas(BigDecimal capital, Integer cuotas, LocalDate vencimiento ) {
		
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
		cuota.setInteres( afipInteresService.calcularInteres(capitalCuota, calculoFechaInicio, calculoFechaVtoCuota) );
		lst.add(cuota);
		
		for ( cuotaNro = 2;  cuotaNro<=cuotas;  cuotaNro++) {
			cuota = new CalcularCuotasCalculadaDto();
			calculoFechaVtoCuota = calculoFechaVtoCuota.plusMonths(1);
			
			cuota.setNumero(cuotaNro);
			cuota.setImporte(capitalCuota);
			cuota.setVencimiento(calculoFechaVtoCuota);
			cuota.setInteres( afipInteresService.calcularInteres(capitalCuota, calculoFechaInicio, calculoFechaVtoCuota) );
			
			lst.add(cuota);
		}
			
		return lst;
	}

	private BigDecimal getCapitalConvenio(Convenio convenio) {
		BigDecimal importe = BigDecimal.ZERO;
		if ( convenio.getImporteDeuda() != null)
			importe = importe.add(convenio.getImporteDeuda());
		
		if ( convenio.getImporteSaldoFavor() != null)
			importe = importe.subtract(convenio.getImporteSaldoFavor());
		return importe;
	}
	
	private BigDecimal calcularCuotaImporteSF(BigDecimal capital, Integer cuotas) {
		BigDecimal importeCuotaSF = BigDecimal.ZERO; 
				
		AfipInteresBO afipInteres = afipInteresService.getContenido( LocalDate.now() );
		BigDecimal interes = afipInteres.getIndice().multiply( BigDecimal.valueOf(30) );
		BigDecimal uno = BigDecimal.valueOf(1);
		BigDecimal auxPaso = null;

		//C = V / ( (1 - ((1/(1+i)) exp N) ) / i)		
		//C = cuota
		//V = capital 
		//i = interes mensual
		//N = nro de cuotas
		auxPaso = uno.divide( uno.add(interes), 2, RoundingMode.HALF_EVEN );
		auxPaso = auxPaso.pow(cuotas);
		auxPaso = uno.subtract( auxPaso  ); 
		auxPaso = auxPaso.divide(interes, 2, RoundingMode.HALF_EVEN);
		importeCuotaSF = capital.divide(auxPaso, 2, RoundingMode.HALF_EVEN );
		
		// V * ( i / ( 1-( 1/ ((1+i)exp c) ) ) )
		auxPaso = uno.add(interes).pow(cuotas); //((1+i)exp c)
		auxPaso = uno.divide(auxPaso, 2, RoundingMode.HALF_EVEN); //( 1/ ((1+i)exp c) )
		auxPaso = uno.subtract(auxPaso); //( 1-( 1/ ((1+i)exp c) ) )
		auxPaso = interes.divide(auxPaso, 2, RoundingMode.HALF_EVEN); //( i / ( 1-( 1/ ((1+i)exp c) ) ) )
		auxPaso = capital.multiply(auxPaso); // V * ( i / ( 1-( 1/ ((1+i)exp c) ) ) )
				
		return importeCuotaSF;
	}
	
	private void validarAlta(Convenio convenio) {
		if ( convenio.getIntencionDePago() == null || convenio.getIntencionDePago().isBefore(LocalDate.now()) ) {			
			String errorMsg = messageSource.getMessage(CommonEnumException.ERROR_FECHA_PASADA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ERROR_FECHA_PASADA.name(), String.format(errorMsg, "Intención de Pago") );			
		}
		
		if (! formaPagoService.existe( convenio.getMedioPago() ) ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.FORMA_PAGO_INEXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.FORMA_PAGO_INEXISTENTE.name(), errorMsg );					
		}
		
		ConvenioConsultaFiltroDto filtro = new ConvenioConsultaFiltroDto();
		filtro.setEmpresaId( convenio.getEmpresa().getId() );
		filtro.setEstado( ConvenioEstadoEnum.PENDIENTE.getCodigo() );
		filtro.setEntidad( convenio.getEntidad() );
		List<Convenio> lst = get( filtro);
		if ( lst != null && lst.size() > 0) {
			String errorMsg = messageSource.getMessage(ConvenioEnumException.ESTADO_PENDIENTE_EXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(ConvenioEnumException.ESTADO_PENDIENTE_EXISTENTE.name(), String.format(errorMsg, dateTimeProvider.getDateToString(convenio.getIntencionDePago())) );			   						
		}
		
		validarActualizacion(convenio);
	}
	
	private void validarActualizacion(Convenio convenio) {
		 
		
	}

	
	public ConvenioDeudaDto getConvenioDeudaDto(Convenio convenio) {
		ConvenioDeudaDto rta = mapper.run3(convenio);
		rta.setDeclaracionesJuradas( convenioDeudaMapper.run(convenio.getDdjjs() ) );
		
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
	
	private void orderByNroCuota(List<ConvenioCuota> lst) {
		 Collections.sort(lst, new Comparator<ConvenioCuota>() {
	            @Override
	            public int compare(ConvenioCuota a, ConvenioCuota b) {
	                return a.getCuotaNro().compareTo(b.getCuotaNro()); // For descending order
	            }
	        });
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
		Integer cantiCuotas = convenio.getCuotasCanti();
		LocalDate vencimiento = convenio.getIntencionDePago();
			
		BigDecimal importeCuota = calcularImporteCuota(capital, cantiCuotas, vencimiento);
		for (ConvenioCuota reg : convenio.getCuotas()) {
			reg.setImporte(importeCuota);
		}
		
		convenio.setImporteIntereses( importeCuota.multiply( BigDecimal.valueOf(cantiCuotas) ).subtract(capital) );		
		
		return convenio;
	}
	
	private Convenio calcularConvenioImportes(Integer convenioId) {
		Convenio convenio = storage.get(convenioId);
		calcularConvenioImportes(convenio);
		return convenio;
	}
	
	private Convenio actualizarConvenioImportes(Integer convenioId) {
		Convenio convenio = storage.get(convenioId);
		//imprimirconvenio( convenio);
		
		convenio = actualizarConvenioImportes(convenio);
		return convenio;
	}
	
	private void imprimirconvenio(Convenio convenio) {
		 if ( convenio != null) {
			 log.debug( "imprimirconvenio() - convenio: " + convenio.toString() );
		 } else {
			 log.debug( "imprimirconvenio() - convenio: NULL");
		 }
	 }
	
	private Convenio actualizarConvenioImportes(Convenio convenio) {
		//Los importes estan en tabla convenio y convenio_cuotas
		
		convenio = calcularConvenioImportes(convenio);
		
		//storage.guardar(convenio);
		for (ConvenioCuota reg :  convenio.getCuotas() ) {			
			convenioCuotaRepository.save(reg);
		}
		storage.actualizarImportes(convenio.getId(), convenio.getImporteDeuda(), convenio.getImporteIntereses(), convenio.getImporteSaldoFavor());
		
		return convenio;
	}

	public  void borrarDDJJ(Integer empresaId, Integer convenioId, Integer ddjjId) {
		
		ConvenioDdjj convenioDdjj = convenioDdjjRepository.findByConvenioIdAndDdjjId(convenioId, ddjjId);
		convenioDdjjRepository.baja(convenioDdjj.getId());
		actualizarConvenioImportes(convenioId);
		
	}
	
	public ConvenioDdjj asignarDDJJ(Integer empresaId, Integer convenioId, Integer ddjjId) {
		ConvenioDdjj rta = null;
		convenioDdjjRepository.alta(convenioId, ddjjId);
		rta = convenioDdjjRepository.findByConvenioIdAndDdjjId(convenioId, ddjjId);
		actualizarConvenioImportes(convenioId);
		
		return rta;
	}

	private Convenio actualizarPlanPago(Convenio convenio, PlanPagoDto planPago) {
		convenio.setIntencionDePago( planPago.getIntencionPago() );
		
		if ( planPago.getCantidadCuota() < convenio.getCuotasCanti() ) {
			//borro las cuotas sobrantes			
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
		
		
		List<ConvenioCuota> lstOri = convenio.getCuotas();
		
		convenio.setCuotasCanti(planPago.getCantidadCuota());		
		calcularCuotas(convenio);		
		List<ConvenioCuota> lstCuotasNew = convenio.getCuotas();
		actualizarCuotas(lstOri, lstCuotasNew);
		
		convenio.setCuotas(lstOri);
		
		if ( convenio.getCuotas() != null ) {
			 for (ConvenioCuota caj:  convenio.getCuotas()) {
					caj = convenioCuotaRepository.save(caj);
			}
		}
		convenioCuotaRepository.flush();
		
		storage.actualizarModoPago(convenio.getId(), convenio.getCuotasCanti(), convenio.getIntencionDePago());
		
		return convenio;

	}
	
	public Convenio actualizarPlanPago(Integer empresaId, Integer convenioId, PlanPagoDto planPago) {
		Convenio convenio = storage.get(convenioId);
		return actualizarPlanPago(convenio, planPago);		 
	}
	
	private void actualizarCuotas(List<ConvenioCuota> lstOri, List<ConvenioCuota> lstNew ) {
		for ( ConvenioCuota reg : lstOri ) {
			for ( ConvenioCuota regNew : lstNew ) {
				if ( regNew.getCuotaNro().equals(reg.getCuotaNro()) ) {
					reg.setImporte(regNew.getImporte());
					reg.setVencimiento(regNew.getVencimiento());
				}
			}			
		}
		
		if ( lstOri.size() < lstNew.size() ) {
			for ( ConvenioCuota regNew : lstNew ) {
				if ( regNew.getCuotaNro() >  lstOri.size() ) {
					lstOri.add(regNew);
				}
			}
		}
	}
	
}
