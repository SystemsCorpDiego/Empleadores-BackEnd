package ar.ospim.empleadores.auth.usuario.app.impl;

import java.security.Key;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.usuario.app.TokenGestionUsuario;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.UsuarioBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.RegistrarUsuarioEnumException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenGestionUsuarioImpl implements TokenGestionUsuario {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageSource messageSource;
	
	static final String CLAIM_MAIL = "mail";
	static final String CLAIM_USUARIO = "usuario";
	static final String CLAIM_USUARIO_ID = "usuarioId";
	static final String CLAIM_TOKEN_DFA = "tokenDFA";
	
	@Value("${app.seguridad.activacion-cuenta.jwt-secret}")
	private String jwtSecretPwd;
	private Key signingKey;
	
	
	@PostConstruct
	private void init() {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretPwd); 
        signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());				
	}

	
	@Override
	public String crearParaMail(UsuarioBO usuario, String mail) {
			return crearToken(usuario.getId(), usuario.getDescripcion(), mail, "");
	}
	
	@Override
	public String crearParaMail(UsuarioBO usuario, String mail, String tokenDFA) {
		try {			
			return crearToken(usuario.getId(), usuario.getDescripcion(), mail, tokenDFA);
		} catch (Exception e) {
			logger.error("TokenActivacionUsuario.crearParaMail() - ERROR - mail: " + mail + " - Cause:" + e.getCause() + " - Message: " + e.getMessage() + " - StackTrace: " + e.getStackTrace()  );
			return null;
		}
	}
	
 
	
	@Override
	public String crearParaUsuario(UsuarioBO usuario) {
        try {
        	return crearToken(usuario.getId(), usuario.getDescripcion(), "", "");
		} catch (Exception e) {
			logger.error("TokenActivacionUsuario.crearParaUsuario() - ERROR - usuario: " + usuario + " - Cause:" + e.getCause() + " - Message: " + e.getMessage() + " - StackTrace: " + e.getStackTrace()  );
			return null;
		}
	}

	@Override
	public String crearParaUsuario(UsuarioBO usuario, String tokenDFA) {
		try {
			return crearToken(usuario.getId(), usuario.getDescripcion(), "", tokenDFA);
		} catch (Exception e) {
			logger.error("TokenActivacionUsuario.crearParaMail() - ERROR - usuario: " + usuario + " - Cause:" + e.getCause() + " - Message: " + e.getMessage() + " - StackTrace: " + e.getStackTrace()  );
			return null;
		}
	}
	
	private String crearToken(Integer usuarioId, String usuario, String mail, String tokenDFA) {
		try {
			SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
			
	        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecretPwd); 
	        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	        HashMap<String, Object> claims = new HashMap<>();
	
	        claims.put(CLAIM_USUARIO, usuario );
			claims.put(CLAIM_USUARIO_ID, usuarioId );
			claims.put(CLAIM_MAIL, mail );
			claims.put(CLAIM_TOKEN_DFA, tokenDFA );
			
			
	        //header
	        Map<String, Object> header = new HashMap<>();
	        header.put(JwsHeader.ALGORITHM, signatureAlgorithm);
	        header.put(Header.TYPE, Header.JWT_TYPE);
	
	        
	        JwtBuilder builder = Jwts.builder()
	                .setHeader(header)
	                .setClaims(claims)                
	                .signWith(signatureAlgorithm, signingKey);
	        
	        return builder.compact();
		} catch (Exception e) {
			logger.error("TokenActivacionUsuario.crear() - ERROR - usuario: " + usuario + " - Cause:" + e.getCause() + " - Message: " + e.getMessage() + " - StackTrace: " + e.getStackTrace()  );
			return null;
		}		
	}
	
	private String getClaim(String token, String claim) {
		Claims  claims;
		try {
			claims = Jwts.parser().setSigningKey( this.signingKey ).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			logger.error("TokenActivacionUsuario.getClaim() - Error - 1 - claim: " +claim+ " - token: " + token);
			String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.TOKEN_HABI.getMsgKey(), null, new Locale("es"));
            throw new BusinessException(RegistrarUsuarioEnumException.TOKEN_HABI.name(), errorMsg);
		}

		if ( !claims.containsKey(claim) || claims.get(claim) == null ) {
			logger.error("TokenActivacionUsuario.getClaim() - Error - 2 - claim: " +claim+ " - token: " + token);
			String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.TOKEN_HABI.getMsgKey(), null, new Locale("es"));
            throw new BusinessException(RegistrarUsuarioEnumException.TOKEN_HABI.name(), errorMsg);
		}
		
		try {
			return claims.get(claim).toString();
		} catch (Exception e) {
			logger.error("TokenActivacionUsuario.getClaim() - Error - 3 - claim: " +claim+ " - token: " + token);
			String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.TOKEN_HABI.getMsgKey(), null, new Locale("es"));
            throw new BusinessException(RegistrarUsuarioEnumException.TOKEN_HABI.name(), errorMsg);
		}
	}
	
	@Override
	public String getUsuario(String token) {		
		String aux = getClaim(token, CLAIM_USUARIO);
		return aux;		 
	}

	@Override
	public Integer getId(String token) {
		String sId = getClaim(token, CLAIM_USUARIO_ID);
		try {
			return Integer.valueOf(sId);
		} catch ( Exception e) {
			logger.error("TokenActivacionUsuario.getId() - Error  - token: " + token);
			String errorMsg = messageSource.getMessage(RegistrarUsuarioEnumException.TOKEN_HABI.getMsgKey(), null, new Locale("es"));
            throw new BusinessException(RegistrarUsuarioEnumException.TOKEN_HABI.name(), errorMsg);			
		}				 
	}
	
	@Override
	public String getMail(String token) {
		String mail = getClaim(token, CLAIM_MAIL);
		return mail;
	}
	
	@Override
	public String getTokenDFA(String token) {		
		String aux = getClaim(token, CLAIM_TOKEN_DFA);
		return aux;		 
	}
	
}

