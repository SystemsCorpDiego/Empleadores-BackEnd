package ar.ospim.empleadores.auth.dfa.app;

public enum DFAExceptionEnum {
	DFA_HABILITADO_PREVIAMENTE,
	DFA_CODE_NULL
	;
	
	public String getMsgKey() {
		return "auth." + this.name();
	}
}
