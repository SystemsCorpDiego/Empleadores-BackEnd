package ar.ospim.empleadores.auth.usuario.infra.output.oauthuser;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OAuthUserBasicData implements Serializable {

    private String id;

    private String username;

    private String enabled;

}
