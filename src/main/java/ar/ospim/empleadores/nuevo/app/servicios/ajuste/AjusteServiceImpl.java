package ar.ospim.empleadores.nuevo.app.servicios.ajuste;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.aporte.AporteService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.CodigoDescripDto;
import ar.ospim.empleadores.nuevo.infra.out.store.AjusteStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.AjusteMotivoEnum;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AjusteServiceImpl implements AjusteService {

	private final MessageSource messageSource;
	private final  AjusteStorage storage;
	private final BoletaPagoStorage boletaPagoStorage;
	private final EmpresaService empresaService;
	private final AporteService aporteService;
	private final DateTimeProvider dateTimeProvider;
	
	@Override
	public AjusteBO guardar(AjusteBO reg) {	

		//Valido Atributos OBLIGATOROS
		if ( reg.getVigencia() == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Vigencia Desde"));			
		}

		if ( reg.getPeriodo() == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Período"));			
		}

		if ( reg.getImporte() == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Importe"));			
		}
		
		if ( reg.getAporte() == null || reg.getAporte().getCodigo() == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Aporte"));			
		}
		
		//['DI'-'Devolución de Intereses', 'DPD''Devolución por pago duplicado', 'O'-'Otros', 'AJ'-'Interes retroactivo', 'IPF'-'Interes Pago Fuera Termino']
		if ( reg.getMotivo() != null && !"DI".equals(reg.getMotivo()) && 
				!"DPD".equals(reg.getMotivo()) && 
				!"O".equals(reg.getMotivo()) && 
				!"AJ".equals(reg.getMotivo()) && 
				!"IPF".equals(reg.getMotivo()) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(), String.format(errorMsg, "Motivo"));			
		}
		
		if ( reg.getEmpresa() == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "CUIT"));			
		}
		EmpresaBO empresa = empresaService.validarEmpresa(reg.getEmpresa().getId() , reg.getEmpresa().getCuit());
		reg.setEmpresa(empresa);
				

		if ( reg.getPeriodo().isAfter(dateTimeProvider.getPeriodoMax()) ) {
			String periodo  = dateTimeProvider.getPeriodoToString(dateTimeProvider.getPeriodoMax());
			String errorMsg = messageSource.getMessage(CommonEnumException.PERIODO_FUTURO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.PERIODO_FUTURO.name(), String.format(errorMsg, periodo));			
		}

		AporteBO aporte = aporteService.findByCodigo(reg.getAporte().getCodigo());
		if ( aporte == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(), String.format(errorMsg, "Aporte"));			
		}
		reg.setAporte(aporte);
		
		if ( reg.getId() != null) {
			AjusteBO regAux = storage.findById(reg.getId());
			if ( regAux.getBoletas().size() >0 ) {
				String errorMsg = messageSource.getMessage(BoletaPagoEnumException.AJUSTE_ASIGNADO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(BoletaPagoEnumException.AJUSTE_ASIGNADO.name(), errorMsg );						
			}				
			
			if ( boletaPagoStorage.existeBoletaConAjuste( reg.getId() ) ) {
				//ERROR: No se puede actualizar un Ajuste copn Boletas Pago Asignadas
				String errorMsg = messageSource.getMessage(AjusteStorageEnumException.AJUSTE_CON_BOLETA.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(AjusteStorageEnumException.AJUSTE_CON_BOLETA.name(), errorMsg);
			}
			
		}
		
		//TODO: validar que no se modifique un Ajuste que ya esta asignado a Boleta Pago.
		
		
		return storage.save(reg);
	}
	
	public void borrar(Integer id) {
		if (id == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg);
		}		
		AjusteBO reg = storage.findById(id);
		if ( reg == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), String.format(errorMsg, id) );			
		}
		if ( reg.getBoletas() != null && reg.getBoletas().size()>0 ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.AJUSTE_ASIGNADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.AJUSTE_ASIGNADO.name(), errorMsg );						
		}
		
		storage.deleteById(id);
	}
	
	public List<AjusteBO> consultar() {
		return storage.findAll();
	}
	
	public List<AjusteBO> consultarCrud() {
		return storage.findAllCrud();
	}
	
	public List<AjusteBO> consultarCrudPorCuit(String cuit) {
		return storage.findCrudByCuit(cuit);
	}
	
	public List<AjusteBO> consultarAportesVigentes(Integer empresaId, String aporte, LocalDate vigencia) {
		return storage.consultarAportesVigentes(empresaId, aporte, vigencia);
	}

	public BigDecimal getImporteUsado(Integer ajusteId) {
		return storage.getImporteUsado(ajusteId);
	}
	
	public void generarAjusteAutomaticoIPF(String p_aporte, Integer p_empresa_id) {
		storage.generarAjusteAutomaticoIPF(p_aporte, p_empresa_id);
		
	}

	public List<CodigoDescripDto> getMotivos() {
		List<CodigoDescripDto> lst = new ArrayList<CodigoDescripDto>();
		CodigoDescripDto reg = new CodigoDescripDto();
		reg.setCodigo(AjusteMotivoEnum.AJ.getCodigo());
		reg.setDescripcion(AjusteMotivoEnum.AJ.getDescripcion());
		lst.add(reg);
		
		reg.setCodigo(AjusteMotivoEnum.DI.getCodigo());
		reg.setDescripcion(AjusteMotivoEnum.DI.getDescripcion());
		lst.add(reg);

		reg.setCodigo(AjusteMotivoEnum.DPD.getCodigo());
		reg.setDescripcion(AjusteMotivoEnum.DPD.getDescripcion());
		lst.add(reg);

		reg.setCodigo(AjusteMotivoEnum.IPF.getCodigo());
		reg.setDescripcion(AjusteMotivoEnum.IPF.getDescripcion());
		lst.add(reg);

		reg.setCodigo(AjusteMotivoEnum.O.getCodigo());
		reg.setDescripcion(AjusteMotivoEnum.O.getDescripcion());
		lst.add(reg);
		
		return lst;
	}

	public String getMotivoDescripcion(String codigo) {
		if ( codigo == null || "".equals(codigo.trim()))
			return null;
		try { 
			return AjusteMotivoEnum.map(codigo).getDescripcion();
		} catch ( Exception e) {
			return null;
		}
	}
}
