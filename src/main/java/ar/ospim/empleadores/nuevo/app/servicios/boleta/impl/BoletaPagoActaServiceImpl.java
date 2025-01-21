package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.seguridad.UsuarioInfo;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoActaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.entidad.EntidadService;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.out.store.AporteStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoletaPagoActaServiceImpl implements BoletaPagoActaService {
	
	private final MessageSource messageSource;
	private final UsuarioInfo usuarioInfo;
	private final BoletaPagoStorage storage;
	private final AporteStorage aporteStorage;
	private final EntidadService entidadService;
	private final FormaPagoService formaPagoService;
	
	public Optional<BoletaPagoBO> buscar(Integer id) {
		return storage.findById(id);
	}
 	
	@Override
	public BoletaPagoBO guardar(BoletaPagoBO boleta) {
		
		if ( esBoletaPagoActa(boleta) ) {
			boleta.setFormaDePago( formaPagoService.getFormaPagoCodigoVentanillaBanco() ); 
		}
		validarBoletaPagoActa(boleta);
		
		//Busco Aporte segun Entidad.
		AporteBO aporte = aporteStorage.findAporteActaByEntidad(boleta.getAporte().getEntidad());
		boleta.setAporte(aporte);
		validarAporte(boleta);
		
		return storage.guardar(boleta);
	}

	@Override
	public void borrar(Integer empresaId, Integer id) {
		validarBaja(empresaId, id);
		storage.borrar(id);
	}
	
	private void validarBaja(Integer empresaId, Integer id) {
		if (id == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg);
		}
		
		validarEmpresa(empresaId);
		
		Optional<BoletaPagoBO> reg = storage.findById(id);
		if ( reg.isEmpty() ) {		
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), String.format(errorMsg, id)	);
		}
		
		validarEmpresa( reg.get().getEmpresa().getId() );				
		
		if ( !esBoletaPagoActa( reg.get()) ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.DDJJ.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.DDJJ.name(),errorMsg );
		}		
	}
	
	@Override
	public Boolean ddjjConBoletas(Integer ddjjId) {
		List<BoletaPagoBO> lst = storage.findByDDJJId(ddjjId);
		if ( lst != null && lst.size()> 0) {
			return true;
		}
		return false;
	}

	private void validarBoletaPagoActa(BoletaPagoBO boleta) {
		validarEmpresa(boleta);

		if ( !esBoletaPagoActa(boleta) ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.DDJJ.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.DDJJ.name(),errorMsg );
		}
		
		if ( boleta.getAporte() == null || boleta.getAporte().getEntidad() == null ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.name(),
					String.format(errorMsg, "Entidad")	);
		}
		
		if ( !entidadService.validarCodigo(boleta.getAporte().getEntidad()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(), String.format(errorMsg, boleta.getAporte().getEntidad())	);
		}
		
		if ( boleta.getAporte().getCodigo() != null ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.APORTE_EN_ACTA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.APORTE_EN_ACTA.name(),errorMsg );
		}
		
		if ( boleta.getActaNro() == null ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.name(),
					String.format(errorMsg, "Nro de Acta")	);
		}
		
		if ( boleta.getDescripcion() == null ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.name(),
					String.format(errorMsg, "Descripción")	);
		}

		if ( boleta.getVencimiento() != null ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.VTO_EN_ACTA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.VTO_EN_ACTA.name(),errorMsg);
		}
		
		if ( boleta.getInteres() != null && boleta.getInteres().compareTo(BigDecimal.ZERO) > 0 ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.INTERES_EN_ACTA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.INTERES_EN_ACTA.name(),errorMsg);
		}
		
		if ( boleta.getImporte() == null ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.name(),
					String.format(errorMsg, "Importe")	);
		}

		if ( boleta.getIntencionDePago() == null ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.name(),
					String.format(errorMsg, "Fecha de Intención de Pago")	);
		}

		if ( boleta.getIntencionDePago().isBefore(LocalDate.now()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ERROR_FECHA_PASADA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ERROR_FECHA_PASADA.name(),
					String.format(errorMsg, "Fecha de Intención de Pago")	);
			
		}
	}
	
	private void validarAporte(BoletaPagoBO boleta) {
				
		if ( boleta.getAporte() == null || boleta.getAporte().getCodigo() == null  ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.ATRIBUTO_OBLIGATORIO.name(),
					String.format(errorMsg, "Codigo de Aporte")	);
		}

	}
	 
	private boolean esBoletaPagoActa(BoletaPagoBO boleta) {
		if ( boleta.getDdjjId() == null ) {
			return true;
		}
		return false;
	}

    private void validarEmpresa(BoletaPagoBO boleta) {
		if ( boleta.getEmpresa() == null || boleta.getEmpresa().getId() == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.EMPRESA_NO_INFORMADA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.EMPRESA_NO_INFORMADA.name(),  errorMsg);			
		}
		validarEmpresa(boleta.getEmpresa().getId());
	}
	
	private void validarEmpresa(Integer empresaId) {
		if ( empresaId == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.EMPRESA_NO_INFORMADA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.EMPRESA_NO_INFORMADA.name(),  errorMsg);			
		}
				
		if ( !usuarioInfo.validarEmpresa(empresaId) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.USUARIO_EMPRESA_DIFERENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.USUARIO_EMPRESA_DIFERENTE.name(),  errorMsg);			
		}
	}
	
}
