package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.dominio.FormaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoConsultaService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJUtils;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoFiltroDto;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsulta;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class BoletaPagoConsultaServiceImpl implements BoletaPagoConsultaService {
	private final MessageSource messageSource;
	
	private final BoletaPagoStorage storage;
	private final FormaPagoService formaPagoService;
	private final DateTimeProvider dtProvider; 
	
	public BoletaPagoBO find(Integer id) {
		Optional<BoletaPagoBO> cons = storage.findById(id);
		if ( cons.isEmpty() ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), String.format(errorMsg, id) ); 							
		}
		return cons.get();
	}
	
	public List<BoletaPagoDDJJConsulta> runConDDJJ(BoletaPagoFiltroDto filtro) {
		List<BoletaPagoDDJJConsulta> lst = storage.consultarConDDJJ(filtro);	
		
		for ( BoletaPagoDDJJConsulta reg : lst) {
			reg.setTipoDdjj( DDJJUtils.getTipoDDJJ(reg.getSecuenciaDdjj()) );  //convertir secuenciaDdjj => "Original",
			FormaPagoBO fp =  formaPagoService.get( reg.getFormaDePago() ); //buscar descripcion FormaPago
			if ( fp != null )
				reg.setFormaDePagoDescripcion( fp.getDescripcion() );
			if ( reg.getBep() != null ) 
					reg.setFormaDePagoDescripcion( reg.getFormaDePagoDescripcion() + " (BEP)" );
		}
		
		return lst;
	}
	

	public BoletaPagoDDJJConsulta runConDDJJ(Integer boletaId) {
		BoletaPagoDDJJConsulta reg = storage.consultarConDDJJ(boletaId);	
		
		reg.setTipoDdjj( DDJJUtils.getTipoDDJJ(reg.getSecuenciaDdjj()) );  //convertir secuenciaDdjj => "Original",
		FormaPagoBO fp =  formaPagoService.get( reg.getFormaDePago() ); //buscar descripcion FormaPago
		if ( fp != null )
			reg.setFormaDePagoDescripcion( fp.getDescripcion() ); 
		
		return reg;
	}

	public List<BoletaPagoDDJJConsulta> runByDdjjId(Integer ddjjId) {
		List<BoletaPagoDDJJConsulta> lst = storage.consultarByDdjjId(ddjjId);
		for ( BoletaPagoDDJJConsulta reg : lst) {
			reg.setTipoDdjj( DDJJUtils.getTipoDDJJ(reg.getSecuenciaDdjj()) );  //convertir secuenciaDdjj => "Original",
			FormaPagoBO fp =  formaPagoService.get( reg.getFormaDePago() ); //buscar descripcion FormaPago
			if ( fp != null )
				reg.setFormaDePagoDescripcion( fp.getDescripcion() ); 			
		}
		
		return lst;
	}
	
	public List<BoletaPagoBO> runSinDDJJ(Integer empresaId, LocalDate periodoD, LocalDate periodoH) {		
		if ( periodoH != null) {
			periodoH = dtProvider.getUltimoDiaMes(periodoH );
		}
		List<BoletaPagoBO> cons = storage.findByEmpresaIdSinDDJJ(empresaId, periodoD, periodoH);		
		return cons;
	}
	
	public Optional<BoletaPagoBO> findByDdjjIdAndAporte(Integer ddjjId, String aporte) {
		List<BoletaPagoBO> lstBoletas = storage.findByDDJJId(ddjjId);
		for (BoletaPagoBO reg:  lstBoletas) {
			if ( reg.getAporte().getCodigo().equals(aporte) 
					&& reg.getBaja() == null) {
				return Optional.of(reg);
			}
		}
		return Optional.empty();
	}

	@Override
	public Integer getSecuenciaProx(Integer empresaId) {
		return storage.getSecuenciaProx(empresaId);
	}
	
	
}
