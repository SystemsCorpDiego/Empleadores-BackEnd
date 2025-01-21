package ar.ospim.empleadores.auth.jwt.app.logindfa;

public enum BadOTPExceptionEnum {
	CODIGO_INVALIDO;
	
	public String getMsgKey() {
		return "auth.otp." + this.name();
	}	
}
