package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Data;

@Data
public class BancoMovimientoBO {
	
	   private String tipo;
	    private String descripcion;

		private String convenioCodigo;
		private String convenioCuenta;

		private String bancoDescripcion;
	    private String sucursalCodigo;
	    private String sucursalDescripcion;

}
