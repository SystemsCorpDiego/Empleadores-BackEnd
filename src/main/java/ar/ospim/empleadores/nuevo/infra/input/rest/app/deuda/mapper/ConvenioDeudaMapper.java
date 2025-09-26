package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.servicios.aporte.AporteService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.ConvenioDDJJDeudaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioPeriodoDetalle;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConvenioDeudaMapper {
	private final DateTimeProvider dtProvider; 
	private final AporteService aporteService;
	private final DDJJStorage ddjjStorage;
	
	 
	
	  public  List<ConvenioDDJJDeudaDto> run2(List<ConvenioPeriodoDetalle> periodos) {
		  List<ConvenioDDJJDeudaDto> rta = new ArrayList<ConvenioDDJJDeudaDto>();
		  ConvenioDDJJDeudaDto reg = new ConvenioDDJJDeudaDto();
		  for(ConvenioPeriodoDetalle p : periodos ) {
			  reg = new ConvenioDDJJDeudaDto();
			  
			  //reg.setId(p.getDeudaNominaId());						//	deuda_nomina.id //convenio_periodo_detalle.id
			  reg.setId( dtProvider.getToStringBEP(p.getPeriodo()) + p.getAporte() ); 
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
