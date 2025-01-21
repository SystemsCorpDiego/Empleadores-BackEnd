package ar.ospim.empleadores.auth.oauth.infra.output;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OAuthUserInfoDto {

    private String sub;

    private String preferred_username;

    private String email;

    private Boolean email_verified;

    private String name;

    private String given_name;

    private String family_name;
}
