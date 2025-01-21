package ar.ospim.empleadores.auth.usuario.infra.output.oauthuser;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OAuthUserRepresentationPayload implements Serializable {

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String enabled;

    private List<OAuthUserRepresentationCredentials> credentials;

}
