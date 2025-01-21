package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancoMovimientoBO {
	
	   private String tipo;
	    private String descripcion;

		private String convenioCodigo;
		private String convenioCuenta;

		private String bancoDescripcion;
	    private String sucursalCodigo;
	    private String sucursalDescripcion;

}
