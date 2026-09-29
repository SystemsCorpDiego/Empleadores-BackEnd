package ar.ospim.empleadores.nuevo.infra.input.rest.app.download;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.auth.jwt.app.generateToken.GenerarTokenDwnldDeuda;
import ar.ospim.empleadores.auth.jwt.dominio.TokenDownloadBo;
import ar.ospim.empleadores.auth.jwt.dominio.TokenTipoEnum;
import ar.ospim.empleadores.auth.jwt.infra.output.token.TokenUtils;
import ar.ospim.empleadores.nuevo.app.servicios.deuda.DeudaImprimirService;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.dominio.EmpresaBO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DownloadPdfDeudaController {


	private final  DeudaImprimirService deudaImprimirService;
	private final GenerarTokenDwnldDeuda generarTokenDwnldDeuda;
	
	@Value("${token.secret}")
	String tokenSecret;

	@PutMapping(value = "/public/doc/download/NotifiDeuda/empresa/cuit/{cuit}/entidad/{entidadCodigo}")
	public ResponseEntity<?> generarToken(@PathVariable("cuit") String cuit, @PathVariable("entidadCodigo") String entidadCodigo ) throws JRException, SQLException {
		
		String token = generarTokenDwnldDeuda.run(cuit, entidadCodigo);
		
		return ResponseEntity.ok().body(token);
		
	}

		
	@GetMapping(value = "/public/doc/download/NotifiDeuda/{tokenDwnld}")
	public ResponseEntity<?> imprimir(@PathVariable("tokenDwnld") String tokenDwnld) throws JRException, SQLException {
		log.debug("DownloadPdfDeudaController.imprimir() - tokenDwnld: " + tokenDwnld);
		
		Optional<TokenDownloadBo> tokenDownloadBo = TokenUtils.parseTokenDownload(tokenDwnld, tokenSecret, TokenTipoEnum.PUBLIC_URL_DOC_DOWNLOAD);
		if ( tokenDownloadBo.isEmpty() )
			return ResponseEntity.badRequest().body("Token invalido o expirado");
		
		byte[] auxPdf = deudaImprimirService.run(tokenDownloadBo.get().empresaId, tokenDownloadBo.get().entidad);

		String contentType = "application/octet-stream";
		String headerValue = "attachment; filename=\"" + "deuda.pdf" + "\"";

		log.debug("FIN");

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
				.body(auxPdf);
	}
	
}
