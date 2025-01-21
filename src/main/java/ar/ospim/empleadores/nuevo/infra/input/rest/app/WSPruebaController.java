package ar.ospim.empleadores.nuevo.infra.input.rest.app;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.pagomiscuentas.PagoMisCuentasService;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.RedLinkService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public/ws/prueba")
@RequiredArgsConstructor
public class WSPruebaController {
	@Value("${getwayPago.redlink.testIdDeuda}")
	private String testIdDeuda;
	
	private final PagoMisCuentasService pagoMisCuentasService;
	private final RedLinkService redLinkService;
	
	
	@PostMapping("/banelco")
	public ResponseEntity<String>  ejecutar( @RequestBody  BanelcoDto dto) {
		String bep = null;
		BoletaPagoBO boleta = new BoletaPagoBO(); 
		
		EmpresaBO empresa = new EmpresaBO(1);  
		empresa.setCuit( dto.getCuit() );
		boleta.setEmpresa(empresa);
		
		boleta.setSecuencia( dto.getSecuencia() );
		
		AporteBO aporte = new AporteBO();
		aporte.setCodigo( dto.getAporte() );		
		aporte.setEntidad( dto.getEntidad() );
		
		boleta.setAporte(aporte);
		
		boleta.setImporte(  dto.getImporte() );		
		boleta.setIntencionDePago( LocalDate.now().plusDays(15) );
		
		try {
			bep = pagoMisCuentasService.generarBep(boleta);
		} catch (Exception e) {
			bep = e.getMessage();
		}
		
		return ResponseEntity.ok( bep );
	}	 
	
	@PostMapping("/redlink")
	public ResponseEntity<String>  ejecutar2( @RequestBody  RedLinkDto dto) {
	    //private final DDJJConsultarService DDJJConsultar;
	    //DDJJBO ddjj = DDJJConsultar.consultar(boleta.getDdjjId());

		BoletaPagoBO boleta = new BoletaPagoBO();
		AporteBO aporte = new AporteBO();
		aporte.setEntidad(dto.getEntidad());
		aporte.setCodigo(dto.getAporte());
		boleta.setAporte(aporte);
		
		EmpresaBO empresa = new EmpresaBO(1);
		empresa.setCuit(dto.getCuit());
		boleta.setEmpresa(empresa);
		
		boleta.setInteres(BigDecimal.ZERO);
		boleta.setImporte(dto.getImporte());
		boleta.setIntencionDePago( LocalDate.now().plusDays(15) );
		boleta.setDdjjId(-1); //mock
		
		String bep = ""; //testIdDeuda="24119" 
		bep = redLinkService.generarBepSinValidarBoleta(boleta, testIdDeuda);   
		
		return ResponseEntity.ok( bep );
	}
	
	
}
