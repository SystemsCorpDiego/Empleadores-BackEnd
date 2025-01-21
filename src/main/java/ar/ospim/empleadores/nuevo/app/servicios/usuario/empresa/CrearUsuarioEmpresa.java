package ar.ospim.empleadores.nuevo.app.servicios.usuario.empresa;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;

public interface CrearUsuarioEmpresa {
	
		public EmpresaBO run(EmpresaBO empresa, String clave);
		
}
