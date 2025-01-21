package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.afiliado.AfiliadoService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJAltaService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJAportesCalcularService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJValidarService;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.DDJJEstadoEnum;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DDJJAltaServiceImpl implements DDJJAltaService {

	private final DDJJStorage storage;
	private final DDJJAportesCalcularService aportesCalcular;
	private final AfiliadoService afiliadoService;
	private final DDJJValidarService validarService; 
	
	@Override
	public DDJJBO run(DDJJBO ddjj) {
		ddjj.setId(null);
		ddjj.setEstado(DDJJEstadoEnum.PENDIENTE.getCodigo());
		ddjj.setSecuencia(null);
		
		if ( ddjj.getPeriodo() != null )
			ddjj.setPeriodo( ddjj.getPeriodo().withDayOfMonth(1) );
		
		//SACAR CUando arregle REACT
		parcheCuilInteDDJJ(ddjj);
		
		validarService.run(ddjj);
		aportesCalcular.run(ddjj);		
		ddjj = storage.guardar(ddjj);
		
		return ddjj;
	}

	private boolean parcheCuilInteDDJJ(DDJJBO ddjj) {
		//PARCHE: Busco inte para los que lo tienen NULL.-
		for ( DDJJEmpleadoBO reg: ddjj.getEmpleados() ) {
			if (reg.getAfiliado() != null && reg.getAfiliado().getCuil() != null && reg.getAfiliado().getInte() == null ) {
				List<AfiliadoBO> lst = afiliadoService.get(reg.getAfiliado().getCuil());
				if ( lst != null && lst.size() > 0 ) {
					reg.getAfiliado().setInte(lst.get(0).getInte()) ;
				}
			}
			if ( reg.getUomaSocio() == null )
				reg.setUomaSocio(false);
			if ( reg.getAmtimaSocio() == null )
				reg.setAmtimaSocio(false);
		}
		
		return true;
	}
	
}
