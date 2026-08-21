package ar.ospim.empleadores.auth.jwt.dominio;

public enum TokenTipoEnum {

    NORMAL("normal"),
    REFRESH("refresh"),
    VERIFICACION("verificacion"),
    AUTENTICACION_PARCIAL("autenticacion_parcial"),
    PUBLIC_URL_DOC_DOWNLOAD("public_url_doc_download")
    ;
 
    private String url;
 
    TokenTipoEnum(String envUrl) {
        this.url = envUrl;
    }
 
    public String getUrl() {
        return url;
    }
}
