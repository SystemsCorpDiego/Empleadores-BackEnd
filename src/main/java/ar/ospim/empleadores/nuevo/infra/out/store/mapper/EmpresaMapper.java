package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;

@Mapper 
public interface EmpresaMapper { 
  
    @Named("fromEmpresaDto")
    //@Mapping(target = "ramoId", source = "ramo.id") 
    Empresa map(EmpresaBO empresa); 

    @Named("fromEmpresa")
    //@Mapping(target = "ramo.id", source = "ramoId") 
    EmpresaBO map(Empresa empresa);

	@Mapping( target="id", ignore = true)  
	void map(EmpresaBO empresa, @MappingTarget Empresa empresaNew);

    @Named("mapUpdate")
    //@Mapping(target = "ramoId", source = "ramo.id") 
    @Mapping(target = "cuit", ignore = true)
    @Mapping( target="id", ignore = true)
    void mapUpdate(@MappingTarget Empresa empresaNew, EmpresaBO empresa); 

}
