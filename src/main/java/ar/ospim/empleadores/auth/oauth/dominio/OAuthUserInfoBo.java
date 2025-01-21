package ar.ospim.empleadores.auth.oauth.dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OAuthUserInfoBo {

    private String username;

    private String email;

    private String firstName;

    private String lastName;
}
