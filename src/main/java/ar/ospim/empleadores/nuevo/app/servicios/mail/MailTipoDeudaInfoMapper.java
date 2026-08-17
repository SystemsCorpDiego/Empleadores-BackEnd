package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.dominio.MailTipoDeudaInfoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IMailTipoDeudaInfoDto;

@Mapper
public interface MailTipoDeudaInfoMapper {

    
    @Mapping(target = "importe", source = "capital")
    @Mapping(target = "email", source = "mail")
	MailTipoDeudaInfoBO run(IMailTipoDeudaInfoDto dto);
	List<MailTipoDeudaInfoBO> run(List<IMailTipoDeudaInfoDto> dto);
	
}
