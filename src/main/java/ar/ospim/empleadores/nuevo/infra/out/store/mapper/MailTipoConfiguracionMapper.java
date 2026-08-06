package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.MailTipoConfiguracion;

@Mapper
public interface MailTipoConfiguracionMapper {

	MailTipoConfiguracion map(MailTipoConfiguracionBO reg);
	MailTipoConfiguracionBO map(MailTipoConfiguracion reg);
	
	List<MailTipoConfiguracionBO> map(List<MailTipoConfiguracion> lst);
	

	@Mapping(target = "id", ignore = true)
	void map(MailTipoConfiguracionBO reg, @MappingTarget MailTipoConfiguracion regNew);

}
