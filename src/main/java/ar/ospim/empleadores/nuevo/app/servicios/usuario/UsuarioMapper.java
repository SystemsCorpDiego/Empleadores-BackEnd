package ar.ospim.empleadores.nuevo.app.servicios.usuario;

import org.mapstruct.Mapper;

import ar.ospim.empleadores.nuevo.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.dominio.UsuarioInternoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Usuario;

@Mapper
public interface UsuarioMapper {


	UsuarioBO map( UsuarioInternoBO usuarioInterno);
	
	UsuarioBO map( Usuario reg);
	
}
