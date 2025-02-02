package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJActualizarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJAportesCalcularService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJBoMapper;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJValidarService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.DDJJEmpresaController;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DDJJActualizarServiceImpl implements DDJJActualizarService {

	private final DDJJConsultarService consultarService; 
	private final DDJJAportesCalcularService aportesCalcular;
	private final DDJJStorage storage;
	private final DDJJBoMapper mapper;
	private final DDJJValidarService validarService; 
	
	@Override
	public DDJJBO run(DDJJBO newDDJJ) {
		log.debug("DDJJActualizarServiceImpl.run(newDDJJ) - newDDJJ = {} ", newDDJJ );

		DDJJBO ddjj = consultarService.consultar(newDDJJ.getId());
		merge(ddjj, newDDJJ);
		ddjj.setPeriodo( ddjj.getPeriodo().withDayOfMonth(1) );
		
		validarService.run(ddjj);
		
		ddjj = storage.guardar(ddjj);
		
		log.debug("DDJJActualizarServiceImpl.run(newDDJJ) - return ddjj = {} ", ddjj );
		return ddjj;
	}
	
	private void merge(DDJJBO ddjj, DDJJBO newDDJJ) {
		DDJJEmpleadoBO empleadoNew = null;

		ddjj.setPeriodo(newDDJJ.getPeriodo());		
		for ( DDJJEmpleadoBO ddjjEmpleado : ddjj.getEmpleados() ) {			
			if (ddjjEmpleado.getAmtimaSocio() == null)
				ddjjEmpleado.setAmtimaSocio(false);
			if (ddjjEmpleado.getUomaSocio() == null)
				ddjjEmpleado.setUomaSocio(false);
			
			Optional<DDJJEmpleadoBO> existe = buscarEmpleado(newDDJJ, ddjjEmpleado);
			
			if ( existe.isPresent() ) {
				empleadoNew = existe.get();
				if (empleadoNew.getAmtimaSocio() == null)
					empleadoNew.setAmtimaSocio(false);
				if (empleadoNew.getUomaSocio() == null)
					empleadoNew.setUomaSocio(false);
				
				ddjj.getLstEmpleadoAporteBaja().add(ddjjEmpleado);
				mapper.mapSinAportes(ddjjEmpleado, empleadoNew);	
				
				aportesCalcular.run(ddjj.getPeriodo(), ddjjEmpleado);
			} else {
				ddjj.getLstEmpleadoBaja().add(ddjjEmpleado);
			}
		}
		
		for ( DDJJEmpleadoBO baja : ddjj.getLstEmpleadoBaja() ) {
			ddjj.getEmpleados().remove(baja);
		}
				
		for ( DDJJEmpleadoBO newEmpleado : newDDJJ.getEmpleados() ) {
			if (newEmpleado.getAmtimaSocio() == null)
				newEmpleado.setAmtimaSocio(false);
			if (newEmpleado.getUomaSocio() == null)
				newEmpleado.setUomaSocio(false);
			
			Optional<DDJJEmpleadoBO> existe = buscarEmpleado(ddjj, newEmpleado);
			if ( existe.isEmpty() ) {
				newEmpleado.setId(null);
				
				aportesCalcular.run(ddjj.getPeriodo(), newEmpleado);
				ddjj.getEmpleados().add(newEmpleado);
			}
		}
	}
	
	private Optional<DDJJEmpleadoBO> buscarEmpleado(DDJJBO newDDJJ, DDJJEmpleadoBO ddjjEmple) {		 		
		List<DDJJEmpleadoBO> find = newDDJJ.getEmpleados().stream()
				  .filter( c -> c.getAfiliado().equals(ddjjEmple.getAfiliado()) )
				  .collect(Collectors.toList());
		
		if ( find.size()>0) {
			return Optional.of(find.get(0));
		}
		return Optional.empty();
	}
	
}

 