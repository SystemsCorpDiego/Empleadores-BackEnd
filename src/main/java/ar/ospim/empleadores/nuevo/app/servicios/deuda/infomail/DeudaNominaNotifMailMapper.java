package ar.ospim.empleadores.nuevo.app.servicios.deuda.infomail;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.dominio.DeudaMailInfoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IDeudaNominaNotifMailDto;

@Mapper
public interface DeudaNominaNotifMailMapper {

    
    @Mapping(target = "importe", source = "capital")
    @Mapping(target = "email", source = "mail")
	DeudaMailInfoBO run(IDeudaNominaNotifMailDto dto);
	List<DeudaMailInfoBO> run(List<IDeudaNominaNotifMailDto> dto);
	
}
