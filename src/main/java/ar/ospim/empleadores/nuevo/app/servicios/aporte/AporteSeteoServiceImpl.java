package ar.ospim.empleadores.nuevo.app.servicios.aporte;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.AporteSeteoBO;
import ar.ospim.empleadores.nuevo.app.servicios.camara.CamaraService;
import ar.ospim.empleadores.nuevo.app.servicios.entidad.EntidadService;
import ar.ospim.empleadores.nuevo.infra.out.store.AporteSeteoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteSeteo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AporteSeteoServiceImpl implements AporteSeteoService {

	private MessageSource messageSource;
	
	private final AporteSeteoStorage storage; 
	private final AporteService aporteService;
	private final EntidadService entidadService;
	private final CalculoService calculoService;
	private final CamaraService camaraService;  
	
	
	@Override
	public AporteSeteoBO guardar(AporteSeteoBO reg) {		
		
		validarFK(reg);
		validarNulos(reg);
		
		//Validaciones
		//Aporte obligatorio
		//desde obligatorio
		//calculo tipo y valor deben existir
		//si calculoTipo=PO =>debe existir calculoBase
			//si calculoBase=PJ o PS => debe existir camara, camara_categoria y camara_antiguedad
		

		
		return storage.save(reg);
	}
	
	private void validarNulos(AporteSeteoBO reg) {
		if ( reg == null  ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE.name(), errorMsg);
		}
		
		if ( reg.getAporte() == null || StringUtils.deleteWhitespace(reg.getAporte()).equals("")   ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Aporte"));
		}

		if ( reg.getDesde() == null  ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Vigencia Desde"));
		}
		if ( reg.getCalculo() == null || reg.getCalculo().getTipo() == null  ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Tipo de Calculo"));
		}
		if ( reg.getCalculo().getValor() == null  ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Valor de Calculo"));
		}		

		//si calculoTipo=PO =>debe existir calculoBase
		if ( reg.getCalculo().getTipo() != null  && reg.getCalculo().getTipo().equals("PO") ) {
			if ( reg.getCalculo().getBase() == null ) {
				String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Base de Calculo"));
			}			
		}

		//si calculoBase=PJ o PS => debe existir camara, camara_categoria y camara_antiguedad
		if ( reg.getCalculo().getBase() != null  && (reg.getCalculo().getBase().equals("PJ")  || reg.getCalculo().getBase().equals("PS") ) ) {
			if ( reg.getCamara() == null ) {
				String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Camara"));
			}	
			if ( reg.getCategoria() == null ) {
				String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Categoria"));
			}	
			if ( reg.getAntiguedad() == null ) {
				String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Antiguedad"));
			}
		}
		
		if( reg.getCategoria() != null && (reg.getCamara() == null || reg.getCamara().getCodigo() == null ) ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Camara"));
		}
		
		if( reg.getAntiguedad() != null && reg.getCategoria() == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Categoria"));
		}
		if( reg.getAntiguedad() != null && (reg.getCamara() == null || reg.getCamara().getCodigo() == null )  ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg, "Camara"));
		}
		
	}
	
	private void validarFK(AporteSeteoBO reg) {
		if ( reg != null ) {
			if ( reg.getAporte() != null ) {
				AporteBO aporteBO = aporteService.findByCodigo(reg.getAporte());
				if ( aporteBO == null) {
					String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
					throw new BusinessException(CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(), String.format(errorMsg, "Aporte"));
				}
			}
			if (reg.getEntidad() != null) {
				if ( !entidadService.validarCodigo( reg.getEntidad().getCodigo() ) ) {
					String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
					throw new BusinessException(CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(), String.format(errorMsg, "Entidad"));
				}
			}
			if (reg.getCalculo().getTipo() != null) {				
				if ( !calculoService.validarTipo( reg.getCalculo().getTipo()  ) ) {
					String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
					throw new BusinessException(CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(), String.format(errorMsg, "Tipo de Calculo"));
				}
			}
			
			if (reg.getCalculo().getBase() != null) {				
				if ( !calculoService.validarBase( reg.getCalculo().getBase()  ) ) {
					String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
					throw new BusinessException(CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(), String.format(errorMsg, "Base de Calculo"));
				}
			}
			
			if ( reg.getCamara() != null && reg.getCamara().getCodigo() != null) {
				if ( !camaraService.validar(reg.getCamara().getCodigo()) ) {
					String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
					throw new BusinessException(CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(), String.format(errorMsg, "Camara"));					
				}
			}	
			
			if ( reg.getCamara() != null && reg.getCamara().getCodigo() != null && reg.getCategoria() != null) {
				if ( !camaraService.validarCategoria(reg.getCamara().getCodigo(), reg.getCategoria()) ) {
					String errorMsg = messageSource.getMessage(CommonEnumException.CODIGO_INVALIDO_NOMBRE.getMsgKey(), null, new Locale("es"));
					throw new BusinessException(CommonEnumException.CODIGO_INVALIDO_NOMBRE.name(), String.format(errorMsg, "Categoria"));					
				}
			}
						
		}
	}
	
	@Override
	public void borrar(Integer id) {
		if (id == null ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg);
		}
		
		storage.deleteById(id);
	}

	public List<AporteSeteo> findVigentes( LocalDate periodo ){
		return storage.getVigentes(periodo);
	}
	
}
