package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

public final class DDJJUtils {

	public static String  getTipoDDJJ(Integer secuencia) {
		if ( secuencia == null ) {
			return "Pendiente"; 
		}  else if ( secuencia == 0  ) {
			return "Original";
		} else if (secuencia > 0) {
			return "Rectif. Nro " + secuencia;
		} else {
			return "Rectif. Negativa! ";
		}
	}
}
