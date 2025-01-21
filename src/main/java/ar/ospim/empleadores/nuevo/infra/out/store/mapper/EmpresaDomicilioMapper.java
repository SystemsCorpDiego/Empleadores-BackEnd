package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;
import ar.ospim.empleadores.nuevo.app.dominio.LocalidadBO;
import ar.ospim.empleadores.nuevo.app.dominio.ProvinciaBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaDomicilio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Localidad;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Provincia;

@Mapper  
public interface EmpresaDomicilioMapper { 

	@Mapping( target="registro.empresaId", source = "empresaId")  
	void map(@MappingTarget EmpresaDomicilio registro, Integer empresaId, EmpresaDomicilioBO regBO);
	  
    EmpresaDomicilio map( Integer empresaId, EmpresaDomicilioBO domicilios);
    
    EmpresaDomicilio map(EmpresaDomicilioBO domicilio);

    EmpresaDomicilioBO map(EmpresaDomicilio domicilio);
    
    ProvinciaBO map(Provincia provincia);
    Provincia map(ProvinciaBO provincia);

	@Mapping( target="descripcion", source = "detalle")  
    LocalidadBO map(Localidad localidad);  
	@Mapping( target="detalle", source = "descripcion")  
    Localidad map(LocalidadBO localidad);

    List<EmpresaDomicilioBO> map(List<EmpresaDomicilio> domicilio);
}
