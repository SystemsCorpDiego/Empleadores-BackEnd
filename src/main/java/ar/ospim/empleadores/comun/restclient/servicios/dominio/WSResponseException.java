package ar.ospim.empleadores.comun.restclient.servicios.dominio;

@SuppressWarnings("serial")
public class WSResponseException extends Exception {
    public WSResponseException(String message) {
        super(message);
    }
}
