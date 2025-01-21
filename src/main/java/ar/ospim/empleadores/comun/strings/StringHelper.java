package ar.ospim.empleadores.comun.strings;

public class StringHelper {
	private StringHelper() {
		super();
	}
	public static boolean isNullOrWhiteSpace(String str) {
		return (str == null || str.length() == 0 || str.trim().equals(""));
	}

	public static String toString(Object value) {
		return value == null ? null : value.toString();
	}
}
