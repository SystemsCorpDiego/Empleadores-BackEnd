package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInfoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto.UsuarioInfoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.jwt.dto.UsuarioInfoUsuarioRolDto;

@Mapper
public interface UsuarioInfoDtoMapper {
 
	@Mapping( target="usuario.rol", source = "rolesAsignados") 
	@Mapping( target="usuario.nombre", source = "descripcion") 
	@Mapping( target="empresa.razonSocial", source = "usuarioEmpresaInfoBO.razonSocial") 
	@Mapping( target="empresa.cuit", source = "usuarioEmpresaInfoBO.cuit") 
	@Mapping( target="empresa.actividad_molinera", source = "usuarioEmpresaInfoBO.actividadMolinera") 
	@Mapping( target="empresa.id", source = "usuarioEmpresaInfoBO.empresaId") 
	@Mapping( target="persona", source = "usuarioInternoBO.persona")
	@Mapping( target="usuario.funcionalidad", source = "funcionalidadesAsignadas")	
	UsuarioInfoDto map(UsuarioInfoBO usuarioInfo); 
	
	List<UsuarioInfoUsuarioRolDto> map(List<RolAsignado> lst);
	
	default UsuarioInfoUsuarioRolDto map(RolAsignado r) {
		return new UsuarioInfoUsuarioRolDto(r.getDescripcion());
	}
}
