package ar.ospim.empleadores.auth.usuario.infra.output.oauthuser;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.oauth.infra.output.config.OAuthWSConfig;
import ar.ospim.empleadores.auth.usuario.app.OAuthUsuarioEnumException;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.modelo.OAuthUsuarioBo;
import ar.ospim.empleadores.auth.usuario.dominio.usuario.servicio.OAuthUsuarioManagementStorage;
import ar.ospim.empleadores.auth.usuario.infra.output.oauthuser.config.OAuthRestTemplateAuth;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.restclient.servicios.RestClient;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OAuthUsuarioManagementStorageImpl extends RestClient implements OAuthUsuarioManagementStorage {
    private final OAuthWSConfig oAuthWSConfig;

    public OAuthUsuarioManagementStorageImpl(OAuthRestTemplateAuth restTemplateAuth, OAuthWSConfig oAuthWSConfig) {
        super(restTemplateAuth, oAuthWSConfig);
        this.oAuthWSConfig = oAuthWSConfig;
    }

    @Override
    public void actualizarUsuario(String currentUsername, OAuthUsuarioBo newUserData) {
        log.debug("Input parameter -> currentUsername {}, newUserData {}", currentUsername, newUserData);
        String oAuthUserId = this.getOAuthUserId(currentUsername);
        if (oAuthUserId == null)
            throw new BusinessException(OAuthUsuarioEnumException.ERROR_GETTING_USER.name(), "Error obteniendo usuario en el servidor");
        updateUserData(oAuthUserId, newUserData);
    }

    private String getOAuthUserId(String username) {
        log.debug("Input parameter -> username {}", username);
        String url = oAuthWSConfig.getCreateUser()
                + "?username=" + username
                + "&exact=true";
        try {
            ResponseEntity<OAuthUserBasicData[]> response = exchangeGet(url, OAuthUserBasicData[].class);
            return (response.getBody() != null && response.getBody().length > 0) ? response.getBody()[0].getId() : null;
        } catch (Exception e) {
            log.debug("Error getting user in OAuth Server");
        }
        return null;
    }

    private void updateUserData(String oAuthUserId, OAuthUsuarioBo newUserData) {
        log.debug("Input parameters -> oAuthUserId {}, newUserData {}", oAuthUserId, newUserData);
        String url = oAuthWSConfig.getCreateUser()
                + "/" + oAuthUserId;
        try {
            ResponseEntity<Object> response = exchangePut(url, mapToUserCreationPayload(newUserData), Object.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.debug("User updated successfully in OAuth Server");
            }
        } catch (Exception e) {
            log.debug("Error updating user in OAuth Server");
        }

    }

    @Override
    public void crearUsuario(OAuthUsuarioBo oAuthUserBo) {
        log.debug("Input parameter -> oAuthUserBo {}", oAuthUserBo);
        String url = oAuthWSConfig.getCreateUser();
        try {
            ResponseEntity<Object> response = exchangePost(url, mapToUserCreationPayload(oAuthUserBo), Object.class);
            if (HttpStatus.CREATED.equals(response.getStatusCode())) {
                log.debug("User created successfully in OAuth Server");
            }
        } catch (Exception e) {
            log.debug("Error creating user in OAuth Server");
            throw new BusinessException(OAuthUsuarioEnumException.ERROR_CREATING_USER.name(), "Error creando el usuario en el servidor");
        }

    }

    private OAuthUserRepresentationPayload mapToUserCreationPayload(OAuthUsuarioBo oAuthUserBo) {
        return new OAuthUserRepresentationPayload(oAuthUserBo.getUsuario(),
                oAuthUserBo.getNombre(),
                oAuthUserBo.getApellido(),
                oAuthUserBo.getEmail(),
                "true",
                (oAuthUserBo.getClave() != null) ? List.of(new OAuthUserRepresentationCredentials("password", oAuthUserBo.getClave(), false)) : null);
    }

}
