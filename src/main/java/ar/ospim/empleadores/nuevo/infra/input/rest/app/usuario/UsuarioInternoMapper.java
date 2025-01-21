package ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioInternoAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.usuario.dto.UsuarioInternoDto;

@Mapper 
public interface UsuarioInternoMapper {

	@Mapping(target = "persona.email", source = "email")
	@Mapping(target = "persona.apellido", source = "apellido")
	@Mapping(target = "persona.nombre", source = "nombre")
	@Mapping(target = "persona.notificaciones", source = "notificaciones")
	@Mapping(target = "rol.id", source = "rolId") 
	UsuarioInternoBO map(UsuarioInternoDto dato);  
	 
	@Mapping(target = "persona.email", source = "email")
	@Mapping(target = "persona.apellido", source = "apellido")
	@Mapping(target = "persona.nombre", source = "nombre")
	@Mapping(target = "persona.notificaciones", source = "notificaciones")
	@Mapping(target = "usuarioClaveBo.clave", source = "clave")
	@Mapping(target = "rol.id", source = "rolId") 
	UsuarioInternoBO map(UsuarioInternoAltaDto dato);
	
	@Mapping(target = "persona.email", source = "dato.email")
	@Mapping(target = "persona.apellido", source = "dato.apellido")
	@Mapping(target = "persona.nombre", source = "dato.nombre")
	@Mapping(target = "persona.notificaciones", source = "dato.notificaciones")
	@Mapping(target = "usuarioClaveBo.clave", source = "dato.clave")
	@Mapping(target = "rol.id", source = "dato.rolId") 
	@Mapping(target = "id", source = "id") 
	UsuarioInternoBO map(Integer id, UsuarioInternoAltaDto dato);

	List<UsuarioInternoDto> map(List<UsuarioInternoBO> lst);
	
	@InheritInverseConfiguration
	UsuarioInternoDto map( UsuarioInternoBO reg);
}
