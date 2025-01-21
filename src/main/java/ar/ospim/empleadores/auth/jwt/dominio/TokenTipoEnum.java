package ar.ospim.empleadores.auth.jwt.dominio;

public enum TokenTipoEnum {

    NORMAL("normal"),
    REFRESH("refresh"),
    VERIFICACION("verificacion"),
    AUTENTICACION_PARCIAL("autenticacion_parcial")
    ;
 
    private String url;
 
    TokenTipoEnum(String envUrl) {
        this.url = envUrl;
    }
 
    public String getUrl() {
        return url;
    }
}
