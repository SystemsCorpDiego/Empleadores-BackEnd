package ar.ospim.empleadores.auth.usuario.infra.output.oauthuser;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class OAuthUserRepresentationCredentials implements Serializable {

    private String type;

    private String value;

    private boolean temporary;
  
}
