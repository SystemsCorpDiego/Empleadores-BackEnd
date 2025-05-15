package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;

@Mapper
public interface DeudaMapper {

	List<GestionDeudaActaDto> run(List<ActaMolineros> lst);	
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "nroActa", source = "numero")
	@Mapping(target = "estadoDeuda", source = "estado")
	@Mapping(target = "fechaActa", source = "fecha")
	@Mapping(target = "importe", source = "capital")
	@Mapping(target = "intereses", source = "interes")
	GestionDeudaActaDto run(ActaMolineros reg);
	
	
	List<GestionDeudaDDJJDto> runNomina(List<DeudaNomina> lst);
		
	@Mapping(target = "id", source = "ddjjId")
	@Mapping(target = "periodo", source = "periodo")	
	@Mapping(target = "aporteCodigo", source = "aporte")	
	@Mapping(target = "importe", source = "importe")		
	@Mapping(target = "intereses", source = "interes")		
	GestionDeudaDDJJDto run(DeudaNomina reg);
	
	List<GestionDeudaDDJJDto> runNomina2(List<IGestionDeudaDDJJDto> lst);
	
}
