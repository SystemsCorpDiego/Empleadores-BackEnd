package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoActaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoActualizarService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoConsultaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJActualizarService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoValidaModiDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoletaPagoActualizarServiceImpl implements BoletaPagoActualizarService {

	private final BoletaPagoConsultaService boletaPagoConsulta;
	private final BoletaPagoDDJJActualizarService boletaPagoDDJJActualizar;
	private final BoletaPagoActaService boletaPagoActaService;
	
	@Override
	public BoletaPagoBO run(BoletaPagoBO bo) {
		BoletaPagoBO reg = boletaPagoConsulta.find(bo.getId()) ;
		if (reg.getDdjjId() != null)
			return boletaPagoDDJJActualizar.run(bo);
		return boletaPagoActaService.guardar(bo);
	}
	
	@Override
	public BoletaPagoValidaModiDto puedeActualizar(BoletaPagoBO bo ) {
		BoletaPagoBO reg = boletaPagoConsulta.find(bo.getId());
		
		BoletaPagoValidaModiDto rta = new BoletaPagoValidaModiDto();
		rta.setBep(reg.getBep()!=null);
		rta.setImpresa(reg.getImpresion()!=null);
		rta.setReemplazar( false );
		
		if (reg.getDdjjId() != null)
			rta.setReemplazar( !boletaPagoDDJJActualizar.puedeActualizar(reg) );
		
		return rta;
	}

}
