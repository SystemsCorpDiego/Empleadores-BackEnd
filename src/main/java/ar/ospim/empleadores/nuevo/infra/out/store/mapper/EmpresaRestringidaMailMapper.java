package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.dominio.MailTipoEmpresaRestringidaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.MailTipoEmpresaRestringida;

@Mapper
public interface EmpresaRestringidaMailMapper {

	MailTipoEmpresaRestringida map(MailTipoEmpresaRestringidaBO reg);
	MailTipoEmpresaRestringidaBO map(MailTipoEmpresaRestringida reg);

	@Mapping(target = "id", ignore = true)
	void map(MailTipoEmpresaRestringidaBO reg, @MappingTarget MailTipoEmpresaRestringida regNew);

	List<MailTipoEmpresaRestringidaBO> map(List<MailTipoEmpresaRestringida> listado);
}
