package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoActaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJCrearValidacionesService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJUtils;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJSecuencia;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoletaPagoDDJJCrearValidacionesServiceImpl implements BoletaPagoDDJJCrearValidacionesService {

	private final MessageSource messageSource;
	private final BoletaPagoActaService boletaPagoService;
	private final DDJJConsultarService ddjjConsultarService;
	
	@Override
	public void run(Integer empresaId, Integer ddjjId) {
		validarExistenciaBoletas(ddjjId);
		validarExistenciaDDJJPosterior(empresaId, ddjjId);		
	}
	
	private void validarExistenciaDDJJPosterior(Integer empresaId, Integer ddjjId) {
		DDJJSecuencia dto = null;
		List<DDJJSecuencia> lst = ddjjConsultarService.buscarSecuenciasPosterioresEnElPeriodo(empresaId, ddjjId);
		if ( lst != null && lst.size()>0 ) {
			dto = lst.get(0);			
			if ( dto == null ) { //DDJJ con estado PENDIENTE.-				
				String errorMsg = messageSource.getMessage(BoletaPagoEnumException.DDJJ_NUEVA_PENDIENTE.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(BoletaPagoEnumException.DDJJ_NUEVA_PENDIENTE.name(), errorMsg	); //String.format(errorMsg, boleta.getAporte().getCodigo())							
			}
			
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.DDJJ_NUEVA.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.DDJJ_NUEVA.name(), String.format(errorMsg, DDJJUtils.getTipoDDJJ(dto.getSecuencia()) ));
		}		
	}

	private void validarExistenciaBoletas(Integer ddjjId) {
		if ( boletaPagoService.ddjjConBoletas(ddjjId) ) {
			String errorMsg = messageSource.getMessage(BoletaPagoEnumException.DDJJ_CON_BOLETAS.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BoletaPagoEnumException.DDJJ_CON_BOLETAS.name(),
					errorMsg	); //String.format(errorMsg, boleta.getAporte().getCodigo())							
		}
	}
	
}
