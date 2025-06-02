package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.servicios.aporte.AporteService;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjjDeudaNomina;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConvenioDeudaMapper {
	
	private final AporteService aporteService;
	
	public  List<ConvenioDDJJDeudaDto> run(List<ConvenioDdjj> ddjjs) {
		List<ConvenioDDJJDeudaDto> rta = new ArrayList<ConvenioDDJJDeudaDto>();
		
		for(ConvenioDdjj ddjj : ddjjs ) {
			for(ConvenioDdjjDeudaNomina deuda : ddjj.getDdjjDeudaNomina() ) {
				ConvenioDDJJDeudaDto reg = new ConvenioDDJJDeudaDto();
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
	
}
