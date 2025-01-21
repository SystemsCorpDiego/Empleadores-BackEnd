package ar.ospim.empleadores.auth.dfa.app;

public interface CypherStorage {

	String encrypt(String input);

	String decrypt(String input);

}
