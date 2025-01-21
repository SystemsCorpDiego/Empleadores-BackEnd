package ar.ospim.empleadores.auth.oauth.app.port;

public interface OAuthUserStorage {

    void registerUser(String username, String email, String password);

    void enableUser(String username);

}
