package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.servicios.aporte.AporteService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDDJJDeudaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjjDeudaNomina;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioPeriodoDetalle;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConvenioDeudaMapper {
	
	private final AporteService aporteService;
	private final DDJJStorage ddjjStorage;
	
	public  List<ConvenioDDJJDeudaDto> run(List<ConvenioDdjj> ddjjs) {
		List<ConvenioDDJJDeudaDto> rta = new ArrayList<ConvenioDDJJDeudaDto>();
		
		for(ConvenioDdjj ddjj : ddjjs ) {
			for(ConvenioDdjjDeudaNomina deuda : ddjj.getDdjjDeudaNomina() ) {
				ConvenioDDJJDeudaDto reg = new ConvenioDDJJDeudaDto();
				
				reg.setConvenioDdjjId(ddjj.getId());
				
				reg.setId(ddjj.getDdjj().getId());
				reg.setPeriodo(ddjj.getDdjj().getPeriodo());
				reg.setRectificativa(ddjj.getDdjj().getSecuencia());

				reg.setAporteCodigo(deuda.getAporte());
				reg.setAporteDescripcion( aporteService.findByCodigo(deuda.getAporte()).getDescripcion() );
				
				reg.setImporte(deuda.getAporteImporte());
				reg.setIntereses(deuda.getInteres());
				rta.add(reg);
			}
		}
		
		return rta;
	}
	
	  public  List<ConvenioDDJJDeudaDto> run2(List<ConvenioPeriodoDetalle> periodos) {
		  List<ConvenioDDJJDeudaDto> rta = new ArrayList<ConvenioDDJJDeudaDto>();
		  ConvenioDDJJDeudaDto reg = new ConvenioDDJJDeudaDto();
		  for(ConvenioPeriodoDetalle p : periodos ) {
			  reg = new ConvenioDDJJDeudaDto();
			  
			  reg.setId(p.getDeudaNominaId());						//	deuda_nomina.id //convenio_periodo_detalle.id		  
			  reg.setConvenioDdjjId(p.getDeudaNominaId());  //deuda_nomina.id
			  
			  reg.setPeriodo(p.getPeriodo());
			  
			  reg.setRectificativa(null); //Buscar rectificativa desde ddjj_id			  
			  if ( p.getDdjjId() != null ) {
				  Optional<DDJJBO> ddjj = ddjjStorage.findById(p.getDdjjId());
				  if ( ddjj.isPresent() )
					  reg.setRectificativa(ddjj.get().getSecuencia());
			  }
			  
			  reg.setAporteCodigo(p.getAporte());
			  reg.setAporteDescripcion( aporteService.findByCodigo(p.getAporte()).getDescripcion() );
				
			  reg.setImporte(p.getImporte());
			  reg.setIntereses(p.getInteres());
			  rta.add(reg);
		  }
		  return rta; 
	  }
	  
}
