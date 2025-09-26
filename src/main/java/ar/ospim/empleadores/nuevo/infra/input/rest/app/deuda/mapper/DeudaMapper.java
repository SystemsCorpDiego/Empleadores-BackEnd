package ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.GestionDeudaActaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.GestionDeudaAjustesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.GestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaAjustesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
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
		
	
	//@Mapping(target = "id", source = "ddjjId")
	@Mapping(target = "id", source = "idString")
	@Mapping(target = "periodo", source = "periodo")	
	@Mapping(target = "aporteCodigo", source = "aporte.codigo")	
	@Mapping(target = "importe", source = "importe")		
	@Mapping(target = "intereses", source = "interes")		
	GestionDeudaDDJJDto run(DeudaNomina reg); 
	
	@Mapping(target = "intereses", source = "interes") 		
	GestionDeudaDDJJDto run(IGestionDeudaDDJJDto reg);
	
	List<GestionDeudaDDJJDto> runNomina2(List<IGestionDeudaDDJJDto> lst);
	
	
	List<GestionDeudaAjustesDto> run2( List<IGestionDeudaAjustesDto> lst);
	
	
	@Mapping(target = "id", source = "idString")
	GestionDeudaDDJJDto runCastIdString(GestionDeudaDDJJDto reg);
	List<GestionDeudaDDJJDto> runCastIdString(List<GestionDeudaDDJJDto> lst);
	
	default String convertIntegerToString(Integer value) {
        return String.valueOf(value);
    }
}
