package ar.ospim.empleadores.nuevo.app.dominio;

import java.util.List;

import ar.ospim.empleadores.auth.usuario.app.FuncionalidadAsignada;
import ar.ospim.empleadores.auth.usuario.app.RolAsignado;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper=true)
public class UsuarioInfoBO  extends UsuarioBO {

	private UsuarioEmpresaInfoBO usuarioEmpresaInfoBO;
	private List<RolAsignado> rolesAsignados;
	private List<FuncionalidadAsignada> funcionalidadesAsignadas;
	private UsuarioInternoBO usuarioInternoBO;

}
