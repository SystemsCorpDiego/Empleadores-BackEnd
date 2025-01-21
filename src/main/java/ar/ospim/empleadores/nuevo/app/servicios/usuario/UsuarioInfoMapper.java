package ar.ospim.empleadores.nuevo.app.servicios.usuario;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.auth.usuario.app.FuncionalidadAsignada;
import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioEmpresaInfoBO;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioInfoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.FuncionalidadConsultaI;

@Mapper
public interface UsuarioInfoMapper {
 
		@Mapping( target="id", source = "usuarioBO.id")   
		@Mapping( target="previoLogin", source = "usuarioBO.previoLogin")
		UsuarioInfoBO map(UsuarioBO usuarioBO, List<RolAsignado> rolesAsignados, UsuarioEmpresaInfoBO usuarioEmpresaInfoBO);
		
		
		FuncionalidadAsignada map ( FuncionalidadConsultaI reg);
		List<FuncionalidadAsignada> map ( List<FuncionalidadConsultaI> reg);
}
