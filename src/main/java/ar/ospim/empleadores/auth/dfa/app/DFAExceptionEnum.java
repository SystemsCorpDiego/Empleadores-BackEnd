package ar.ospim.empleadores.auth.dfa.app;

public enum DFAExceptionEnum {
	DFA_HABILITADO_PREVIAMENTE
	;
	
	public String getMsgKey() {
		return "auth." + this.name();
	}
}
