package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.FormaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.ajuste.AjusteService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoConsultaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJConsultaService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJUtils;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.BoletaPagoDtoMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAfiliadoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAjusteDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoletaPagoDDJJConsultaServiceImpl implements BoletaPagoDDJJConsultaService {
	
	private final BoletaPagoConsultaService boletaPagoService;
	private final DDJJConsultarService ddjjService;
	private final BoletaPagoDtoMapper mapper;
	private final FormaPagoService formaPagoService;
	private final AjusteService ajusteService;
	@Override
	public DDJJBoletaArmadoDetalleDto run(Integer boletaId) {
		// TODO Auto-generated method stub
		BoletaPagoBO boletaPago =boletaPagoService.find(boletaId);
		DDJJBoletaArmadoDetalleDto dto = mapper.mapDto(boletaPago);
		FormaPagoBO regFormaPago = formaPagoService.get(dto.getFormaDePago());
		if ( regFormaPago != null )
			dto.setFormaDePagoDescripcion(regFormaPago.getDescripcion());
		
		//casteo descripciones de Ajustes(que son periodos)
		for (DDJJBoletaArmadoDetalleAjusteDto reg: dto.getAjustes() ) {
			try {
				reg.setDescripcion( "Período " +reg.getDescripcion().split("-")[1] + "/" +reg.getDescripcion().split("-")[0] );
			} catch (Exception e) {}
			reg.setMotivoDescripcion( ajusteService.getMotivoDescripcion(reg.getMotivo()) );
		}
		
		//TODO: periodo(de la ddjj), tipoDdjj(de la ddjj)
		DDJJBO ddjj = ddjjService.consultar(boletaPago.getDdjjId());
		dto.setPeriodo(ddjj.getPeriodo());
		dto.setTipoDdjj(DDJJUtils.getTipoDDJJ(ddjj.getSecuencia()));
		 
		
		List<DDJJBoletaArmadoDetalleAfiliadoDto> boletaPagoEmpleados = ddjjService.empleadosPorAporte(boletaPago.getDdjjId(), boletaPago.getAporte().getCodigo() );
		dto.setAfiliados(boletaPagoEmpleados);
		
		return dto;
	}

}
