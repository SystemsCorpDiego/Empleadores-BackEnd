package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.PersonaBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.UsuarioPersona;

@Mapper 
public interface UsuarioPersonaMapper {

	   @Mapping(target = "nombre", source = "persona.nombre")
	   @Mapping(target = "apellido", source = "persona.apellido")
	   @Mapping(target = "email", source = "persona.email")	   
	   @Mapping(target = "notificaciones", source = "persona.notificaciones")
	   @Mapping(target = "usuarioId", source = "id")
	   @Mapping(target = "id", source = "persona.id")
	   UsuarioPersona map(UsuarioInternoBO usuarioPersona);
	    
	   @InheritInverseConfiguration
	   UsuarioInternoBO map(UsuarioPersona usuarioPersona);
	   
	   
	   UsuarioBO mapUsuario(UsuarioInternoBO usuarioPersona);
	   
	   @Mapping(target = "nombre", source = "persona.nombre")
	   @Mapping(target = "apellido", source = "persona.apellido")
	   @Mapping(target = "email", source = "persona.email")	   
	   @Mapping(target = "id", source = "persona.id")
	   PersonaBO mapPersona(UsuarioInternoBO usuarioPersona);
}
