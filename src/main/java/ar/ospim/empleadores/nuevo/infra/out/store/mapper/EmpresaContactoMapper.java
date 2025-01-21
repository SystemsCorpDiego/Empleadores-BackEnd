package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.EmpresaContacto;

@Mapper 
public interface EmpresaContactoMapper { 
 
	@Mapping( target="registro.empresaId", source = "empresaId")  
	void map(@MappingTarget EmpresaContacto registro, Integer empresaId, ContactoBO regBO);
	  
    EmpresaContacto map( Integer empresaId, ContactoBO domicilios);
    
    EmpresaContacto map(ContactoBO domicilio);

    ContactoBO map(EmpresaContacto contacto); 

    List<ContactoBO> map(List<EmpresaContacto> contacto);

  
    EmpresaContacto map( ContactoBO contacto, Integer empresaId);

}
