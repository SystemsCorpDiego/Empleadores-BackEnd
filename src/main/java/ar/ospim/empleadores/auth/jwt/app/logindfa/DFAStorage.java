package ar.ospim.empleadores.auth.jwt.app.logindfa;

public interface DFAStorage {

	Boolean verifyCode(String code);

}
