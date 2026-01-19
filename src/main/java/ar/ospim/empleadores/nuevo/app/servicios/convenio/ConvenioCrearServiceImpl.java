package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioConsultaFiltroDto;
import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ConvenioEstadoEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioPeriodoDetalle;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioSeteo;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ConvenioCrearServiceImpl implements ConvenioCrearService {

	private final MessageSource messageSource;
	private final EmpresaRepository empresaRepository;
	private final FormaPagoService formaPagoService;
	private final ConvenioStorage storage;
	private final DateTimeProvider dateTimeProvider;
	private final ConvenioCuotasCalcular cuotasCalcular;
	private final ConvenioDetalleService detalleService;
	private final ConvenioSeteoService convenioSeteoService;
	private final DateTimeProvider dtProvider;
	
	@Override
	public Convenio run(ConvenioAltaDto dto) { 
			 Convenio convenio = armarConvenio(dto);
			 
			 cuotasCalcular.run( convenio );

			 validarAlta(convenio);

			 convenio = guardarConvenio(convenio);
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
		 
		 detalleService.runActas(convenio, dto.getActas());
		 detalleService.runAjustes(convenio, dto.getAjustes());
		 detalleService.runPeriodos(convenio, dto.getDdjjs());
		 actualizarTotales(convenio);

		 return convenio;
	}


	
	private void validarAlta(Convenio convenio) {
		ConvenioConsultaFiltroDto filtro = new ConvenioConsultaFiltroDto();
		filtro.setEmpresaId( convenio.getEmpresa().getId() );
		filtro.setEstado( ConvenioEstadoEnum.PENDIENTE.getCodigo() );
		filtro.setEntidad( convenio.getEntidad() );
		List<Convenio> lst = get( filtro);
		if ( lst != null && lst.size() > 0) {
			String errorMsg = messageSource.getMessage(ConvenioEnumException.ESTADO_PENDIENTE_EXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(ConvenioEnumException.ESTADO_PENDIENTE_EXISTENTE.name(), String.format(errorMsg, dateTimeProvider.getDateToString(convenio.getIntencionDePago())) );			   						
		}
		
		if ( convenio.getIntencionDePago()  == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Intención de Pago") );			
		}

		//Intencion de Pago: validar fecha Pasada		
		if ( convenio.getIntencionDePago().isBefore(LocalDate.now()) ) {			
			String errorMsg = messageSource.getMessage(CommonEnumException.ERROR_FECHA_PASADA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ERROR_FECHA_PASADA.name(), String.format(errorMsg, "Intención de Pago") );			
		}
		
		//Intencion de Pago: validar que la fecha no sea superior al seteo de config Convenios		
		if (  ! convenioSeteoService.validarFechaPago(convenio.getEmpresa().getCuit(), convenio.getIntencionDePago()) ) {
			LocalDate fMaxima = convenioSeteoService.getFechaPagoMaxima(convenio.getEmpresa().getCuit());
			String errorMsg = messageSource.getMessage(ConvenioEnumException.SETEO_FECHAPAGO_CANTMAXDIAS.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(ConvenioEnumException.SETEO_FECHAPAGO_CANTMAXDIAS.name(), String.format(errorMsg, dtProvider.getDateToString(fMaxima) ) );			
		}
		
		
		if (! formaPagoService.existe( convenio.getMedioPago() ) ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.FORMA_PAGO_INEXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.FORMA_PAGO_INEXISTENTE.name(), errorMsg );					
		}
	}

	private Convenio guardarConvenio(Convenio convenio ) {
		convenio = storage.guardar(convenio);
		convenio = detalleService.runGrabar(convenio);
		convenio = cuotasCalcular.grabar(convenio);
		
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
		 
		 convenio.setImporteSaldoFavor(saldoAFavorDeuda);		 
		 convenio.setImporteDeuda(capitalDeuda);
		 convenio.setImporteIntereses(interesDeuda);
	}

	public List<Convenio> get(ConvenioConsultaFiltroDto filtro) {
		List<Convenio> lst = null;		
		lst = storage.get(filtro);		
		return lst;
	}
	
	
}
