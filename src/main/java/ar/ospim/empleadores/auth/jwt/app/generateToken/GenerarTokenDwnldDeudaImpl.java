package ar.ospim.empleadores.auth.jwt.app.generateToken;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.auth.jwt.dominio.TokenTipoEnum;
import ar.ospim.empleadores.comun.token.JWTUtils;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.dominio.EmpresaBO;

@Service
public class GenerarTokenDwnldDeudaImpl implements GenerarTokenDwnldDeuda {

    private final String secret;

    private final Duration tokenExpiration;
 
    private final EmpresaService empresaService;

    public GenerarTokenDwnldDeudaImpl(
            @Value("${token.secret}") String secret,
            @Value("${token.expirationDownloads}") Duration tokenExpiration,
            EmpresaService empresaService) {
        this.secret = secret;
        this.tokenExpiration = tokenExpiration;        
        this.empresaService = empresaService;
    }
    
	@Override
	public String run(String cuit, String entidad) {
		
		EmpresaBO empresaBo = empresaService.getEmpresa(cuit);
		
		Map<String, Object> claims = Map.of(
                "empresaId", empresaBo.getId(),
                "cuit", cuit, 
                "entidad", entidad,
                JWTUtils.TOKEN_CLAIM_TYPE, TokenTipoEnum.PUBLIC_URL_DOC_DOWNLOAD
        );
		return JWTUtils.generate(claims, "DownloadDoc", secret, tokenExpiration);		
	}

}
