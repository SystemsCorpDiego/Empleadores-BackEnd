package ar.ospim.empleadores.auth.jwt.app.refrescartoken;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import ar.ospim.empleadores.auth.jwt.app.BadRefreshTokenException;
import ar.ospim.empleadores.auth.jwt.app.generateToken.GenerateToken;
import ar.ospim.empleadores.auth.jwt.app.login.LoginEnumException;
import ar.ospim.empleadores.auth.jwt.dominio.JWTokenBo;
import ar.ospim.empleadores.auth.jwt.dominio.TokenTipoEnum;
import ar.ospim.empleadores.auth.jwt.dominio.usuario.UsuarioInfoStorage;
import ar.ospim.empleadores.auth.jwt.infra.output.token.TokenUtils;
import ar.ospim.empleadores.auth.oauth.app.RefrescarOAuthToken;

@Service
public class RefrescarTokenImpl implements RefrescarToken {

	private final MessageSource messageSource;
	
    private final UsuarioInfoStorage userInfoStorage;

    private final GenerateToken generateToken;

    private final RefrescarOAuthToken refreshOAuthToken;

    private final String secret;

    @Value("${ws.oauth.enabled:false}")
    private boolean oAuthServiceEnabled;

    public RefrescarTokenImpl(@Value("${token.secret}") String secret,
    		UsuarioInfoStorage userInfoStorage,
    		GenerateToken generateToken,
    		RefrescarOAuthToken refreshOAuthToken,
    		MessageSource messageSource) {
        this.userInfoStorage = userInfoStorage;
        this.generateToken = generateToken;
        this.refreshOAuthToken = refreshOAuthToken;
        this.secret = secret;
        this.messageSource = messageSource;
    }

    @Override
    public JWTokenBo execute(String refreshToken) throws BadRefreshTokenException {
		String errorMsg = messageSource.getMessage(LoginEnumException.ERROR_TOKEN_REFRESH.getMsgKey(), null, new Locale("es"));			
		BadRefreshTokenException badRefreshTokenException = new BadRefreshTokenException(LoginEnumException.ERROR_TOKEN_REFRESH.name(), errorMsg);

		if (ObjectUtils.isEmpty(refreshToken)) {
			throw  badRefreshTokenException;
        }
        if (oAuthServiceEnabled) {
            return refreshOAuthToken.run(refreshToken).orElseThrow( () -> {return badRefreshTokenException;} );
        }
        return TokenUtils.parseToken(refreshToken, secret, TokenTipoEnum.REFRESH)
                .map(tokenData -> userInfoStorage.getUsuario(tokenData.usuario))
                .map(user -> generateToken.generateTokens(user.getId(), user.getNombre()))
                .orElseThrow( () -> {return badRefreshTokenException;} );
    }
}
