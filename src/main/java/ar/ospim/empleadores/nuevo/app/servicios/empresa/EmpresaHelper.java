package ar.ospim.empleadores.nuevo.app.servicios.empresa;

public class EmpresaHelper {
	
	
	public static boolean isValidCUIT(String cuit) {
		String cuit_nro = cuit.replace("-", "");
	    
		if (cuit_nro.length() != 11) return false;
	    
		if (cuit.equals("11111111111") )
			return true;
		
		boolean rv = false;
		int resultado = 0;
		
		String codes = "6789456789";
		int verificador = Character.getNumericValue(cuit_nro.charAt(cuit_nro.length() - 1));
		int x = 0;
			
		while (x < 10) {
			int digitoValidador = Integer.parseInt(codes.substring(x, x+1));
			int digito = Integer.parseInt(cuit_nro.substring(x, x+1));
			int digitoValidacion = digitoValidador * digito;
			resultado += digitoValidacion;
			x++;
		}
		resultado = resultado % 11;
		rv = (resultado == verificador);
		return rv;    
	}
	
}
