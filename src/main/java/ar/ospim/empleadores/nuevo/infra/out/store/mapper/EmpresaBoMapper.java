package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;

@Mapper 
public interface EmpresaBoMapper { 
	 
    //@Mapping(target = "ramoId", source = "ramo.id")
    Empresa map(EmpresaBO empresa);

    //@Mapping(target = "ramo.id", source = "ramoId")
    EmpresaBO map(Empresa empresa);

    List<EmpresaBO> map(List<Empresa> cons);
}
