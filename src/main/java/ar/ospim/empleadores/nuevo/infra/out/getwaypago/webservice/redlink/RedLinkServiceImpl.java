package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.message.PrefixedQName;
import org.apache.axis.message.SOAPHeaderElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.exception.WebServiceException;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.BepBoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WsAbarcativa;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WsAltaDeDeudasRequest;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WsAltaDeDeudasResponse;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WsDeuda;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WsHeaderRequest;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WsResultadoAlta;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.WsVencimiento;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.impl.WSAltaDeudaServicesImplServiceLocator;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.linkpagos.altadeuda.impl.WSAltaDeudaServicesImplServiceSoapBindingStub;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EntidadEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedLinkServiceImpl implements RedLinkService {   

	private final MessageSource messageSource;
	//private final DDJJConsultarService DdjjService;
	
	@Value("${getwayPago.redlink.wsseSecurity.amtima.usuario}")
	private String usuarioAMTIMA;//="PAGOSSERVICES_DK2";	
	@Value("${getwayPago.redlink.wsseSecurity.amtima.clave}")
	private String passwordAMTIMA; // = "Y8*S3h-J";
	
	@Value("${getwayPago.redlink.wsseSecurity.uoma.usuario}")
	private String usuarioUOMA; //="PAGOSSERVICES_DJU";
	@Value("${getwayPago.redlink.wsseSecurity.uoma.clave}")
	private String passwordUOMA; // = "i-5me*P1";
	
	
	@Value("${getwayPago.redlink.requestHeader.ipCliente}")
	private String IP_CLIENTE; //="181.168.85.133"; // Salida a Internet de uomaempleadores.org.ar
	@Value("${getwayPago.redlink.requestHeader.canal}")
	private String REQUEST_CANAL; // = "SITIO WEB";
	
	@Value("${getwayPago.redlink.requestBody.ente.uoma}")
	private String altaDeDeudasEnteUOMA; //="DJU";
	@Value("${getwayPago.redlink.requestBody.ente.amtima}")
	private String altaDeDeudasEnteAMTIMA; //="DK2";
	
	private final String cabeceraIdEntidadUOMA="UOMA";
	private final String cabeceraIdEntidadAMTIMA="AMTI";

	
	@Value("${getwayPago.redlink.conceptos.UOMACS}")
	private String conceptoUOMACS; 
	@Value("${getwayPago.redlink.conceptos.UOMAAS}")
	private String conceptoUOMAAS;
	@Value("${getwayPago.redlink.conceptos.UOMACU}")
	private String conceptoUOMACU;
	@Value("${getwayPago.redlink.conceptos.ART46}")
	private String conceptoART46;
	@Value("${getwayPago.redlink.conceptos.AMTIMACS}")
	private String conceptoAMTIMACS;
	
	
	public String generarBepSinValidarBoleta(BoletaPagoBO boleta, String deudaIdDeuda) throws WebServiceException {
		return generarBep( boleta, deudaIdDeuda);
	}
	
	@Override
	public String generarBep( BoletaPagoBO boleta ) throws WebServiceException {
		return generarBep( boleta, null);
	}
	 
	public String generarBep(BoletaPagoBO boleta, String deudaIdDeuda) throws WebServiceException {
		log.debug("generarBep() - boleta: {} - deudaIdDeuda: {}", boleta, deudaIdDeuda);
		
		WsAltaDeDeudasRequest requestAltaDeDeudas = null;
		
		validarBoleta(boleta);
		
		String rta = null;
		try {			
			WSAltaDeudaServicesImplServiceLocator service = new WSAltaDeudaServicesImplServiceLocator();
		    WSAltaDeudaServicesImplServiceSoapBindingStub stub = new WSAltaDeudaServicesImplServiceSoapBindingStub(new java.net.URL(service.getWSAltaDeudaServicesImplPortAddress()),service);
		    stub.setHeader( getHeader(boleta) );
		    
			requestAltaDeDeudas = getAltaDeDeudasRequest(boleta, deudaIdDeuda) ; 
			log.debug("generarBep() - request - AltaDeDeudas: {}", requestAltaDeDeudas);
			
			WsAltaDeDeudasResponse response = stub.altaDeDeudas(requestAltaDeDeudas);
			log.debug("generarBep() - response: {}", response);
			
			rta = procesarRespuesta(response);
			
			return rta;
		} catch(RemoteException e) {
			log.error("generarBep() - RemoteException - Error al llamar a ws Red Link: {}",e);
			if ( requestAltaDeDeudas != null)
				log.error("generarBep() - RemoteException - requestAltaDeDeudas: {}", requestAltaDeDeudas);
			//ret="Error Remote Exception "+ e.getMessage() ;
			throw new WebServiceException( "2", "Error Remote Exception" + e.getMessage() , e);
	    } catch ( BusinessException e) {
	    	if ( requestAltaDeDeudas != null)
				log.error("generarBep() - RemoteException - requestAltaDeDeudas: {}", requestAltaDeDeudas);
	    	throw e;
	    } catch ( WebServiceException e) {
	    	if ( requestAltaDeDeudas != null)
				log.error("generarBep() - RemoteException - requestAltaDeDeudas: {}", requestAltaDeDeudas);
	    	throw e;
	    } catch (Exception e) {
	    	log.error("generarBep() - Exception- Error al llamar a ws Red Link: {}",e);
	    	if ( requestAltaDeDeudas != null)
				log.error("generarBep() - RemoteException - requestAltaDeDeudas: {}", requestAltaDeDeudas);
	    	//ret="Error Exception" ;
	    	throw new WebServiceException( "3", "Exception- Error al llamar a ws Red Link: " + e.getMessage() , e);
	    }
	}
	
	private WsAltaDeDeudasRequest getAltaDeDeudasRequest(BoletaPagoBO boleta, String deudaIdDeuda) {
		String altaDeDeudasEnte = getAltaDeDeudasEnte(boleta); // "DJU";
		String altaDeDeudasUsuario = boleta.getEmpresa().getCuit(); //"20233725006"; //cuit del usuario que va a pagar la deuda

		WsHeaderRequest cabecera = getCabecera(boleta);
	    WsDeuda deuda = getDeuda(boleta, deudaIdDeuda);
		WsDeuda[] vDatosDeudas={deuda};
		WsAltaDeDeudasRequest requestAltaDeDeudas = new WsAltaDeDeudasRequest(cabecera, altaDeDeudasEnte, altaDeDeudasUsuario, vDatosDeudas);
		return requestAltaDeDeudas;
	}
	
	private String procesarRespuesta(WsAltaDeDeudasResponse response) {
		log.error("RedLinkService - procesarRespuesta - init");
		
		String rta = null;
		if ( response == null ) {
			log.error("Respuesta Vacia");
			throw new WebServiceException( "1", "response null");
		}		 
		log.error("RedLinkService - procesarRespuesta - 1");
		
		WsResultadoAlta[] resultado = response.getResultadoAltas();
		log.error("RedLinkService - procesarRespuesta - 2");
		
		for(int xi=0; xi<resultado.length; xi ++){
			log.error("RedLinkService - procesarRespuesta - 3");
			String codigoResultado  = resultado[xi].getCodigoResultadoAlta();
			String descripResultado = resultado[xi].getDescripcionResultadoAlta();
			
			log.error("RedLinkService - procesarRespuesta - 3 - codigoResultado: " + codigoResultado + " descripResultado: " + descripResultado);
			
		    if("00".equalsIgnoreCase(codigoResultado)){
			   log.info("Respuesta OK de Red Link: " + response.toString());
			   rta = response.getCpe();
		    } else {
			   log.info("Respuesta FAIL de Red Link: " + codigoResultado +" - "+ descripResultado);
			   log.info("Respuesta FAIL de Red Link - response: " + response.toString());
			   throw new WebServiceException( codigoResultado, descripResultado);
		    }  
		}
		log.error("RedLinkService - procesarRespuesta - 4 - rta: " + rta);
		return rta;
	}
	
	private void validarBoleta(BoletaPagoBO boleta) {
		if ( boleta == null ) {			
				String errorMsg = messageSource.getMessage(BepBoletaPagoEnumException.BOLETA_NULL.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(BepBoletaPagoEnumException.BOLETA_NULL.name(), errorMsg);					
		}
		
		if ( boleta.getAporte() == null ) {			
			String errorMsg = messageSource.getMessage(BepBoletaPagoEnumException.BOLETA_APORTE_NULL.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BepBoletaPagoEnumException.BOLETA_APORTE_NULL.name(), errorMsg);					
		}
		
		//Entidad: Solo se pagan aportes de las entidades: AMTIMA y UOMA
		if ( !esEntidadUOMA(boleta) && !esEntidadAMTIMA(boleta) ) {
			String errorMsg = messageSource.getMessage(BepBoletaPagoEnumException.REDLINK_ENTIDAD.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BepBoletaPagoEnumException.REDLINK_ENTIDAD.name(), errorMsg);					
		}
		
		//Aportes: Solo se pagan aportes con Concepto de pago en REDLINK
		if ( getConceptoPago(boleta.getAporte().getCodigo()) == null ) {
				String errorMsg = messageSource.getMessage(BepBoletaPagoEnumException.REDLINK_APORTE.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(BepBoletaPagoEnumException.REDLINK_APORTE.name(), errorMsg);					
		}
		
		if ( boleta.getEmpresa() == null || boleta.getEmpresa().getCuit() == null ) {
			String errorMsg = messageSource.getMessage(BepBoletaPagoEnumException.REDLINK_CUIT_PAGADOR.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BepBoletaPagoEnumException.REDLINK_CUIT_PAGADOR.name(), errorMsg);					
		}
		
		if ( boleta.getDdjjId() == null ) {
			String errorMsg = messageSource.getMessage(BepBoletaPagoEnumException.REDLINK_BOLETA_SIN_DDJJ.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BepBoletaPagoEnumException.REDLINK_BOLETA_SIN_DDJJ.name(), errorMsg);					
		}
		
	}
	
	private String getConceptoPago(String aporteCodigo) {
		//Aportes: Solo se pagan estos aportes
		/*
		 * APORTE			CONCEPTO REDLINK
		"UOMACS"    => CUOTA_SOC_UOMA=001
		"UOMAAS"    => APORTE_SOL_UOMA=002
		"UOMACU"    => CUOTA_USUFRUCTO=003
		"ART46"        => ART_46=004
		"AMTIMACS" => CUOTA_AMTIMA=001
		*/
		String rta = null;
		switch( aporteCodigo ) {
			case "UOMACS":
				rta = conceptoUOMACS;
				break;
			case "UOMAAS":
				rta = conceptoUOMAAS;
				break;
			case "UOMACU":
				rta = conceptoUOMACU;
				break;
			case "ART46":
				rta = conceptoART46;
				break;
		  case "AMTIMACS":
			  	rta = conceptoAMTIMACS;
			  	break;
		}
		
		return rta;
	}
	
	private WsHeaderRequest getCabecera(BoletaPagoBO boleta) {
		String idRequerimiento = getCabeceraIdRequerimiento(); //"1001"
		String timeStamp = getCabeceraTimeStamp(); //"20250111090102333";
		String idEntidad =  getCabeceraIdEntidad(boleta); // "UOMA";		
		
		return new WsHeaderRequest(idRequerimiento, IP_CLIENTE, timeStamp, idEntidad, REQUEST_CANAL);
	}
	
	private String getAltaDeDeudasEnte(BoletaPagoBO boleta) {
		if ( esEntidadUOMA(boleta) ) {
			return altaDeDeudasEnteUOMA;
		}
		if ( esEntidadAMTIMA(boleta) ) {
			return altaDeDeudasEnteAMTIMA;
		}
		return null;
	}
	
	private String getCabeceraIdRequerimiento() {
		SecureRandom random = new SecureRandom(); 
        byte bytes[] = new byte[20]; 
        random.nextBytes(bytes); 
        return bytes.toString();		
	}
	
	private String getCabeceraTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStamp = sdf.format( new Date() );
		return  timeStamp ;
	}

	private String getCabeceraIdEntidad(BoletaPagoBO boleta) {
		String idEntidad =  null;
		
		if ( esEntidadUOMA(boleta) ) {
			idEntidad =  cabeceraIdEntidadUOMA;
		}

		if ( esEntidadAMTIMA(boleta) ) {
			idEntidad = cabeceraIdEntidadAMTIMA;
		}
		return  idEntidad;
	}
	
	private Boolean esEntidadUOMA(BoletaPagoBO boleta) {
		return boleta.getAporte().getEntidad().equals( EntidadEnum.UOMA.getCodigo() );		
	}
	
	private Boolean esEntidadAMTIMA(BoletaPagoBO boleta) {
		return boleta.getAporte().getEntidad().equals( EntidadEnum.AMTIMA.getCodigo() );		
	}
	

	private SOAPHeaderElement getHeader(BoletaPagoBO boleta) throws SOAPException {
		String usuario = usuarioUOMA;
		String clave = passwordUOMA;
		
		if ( esEntidadAMTIMA(boleta) ) {
			usuario = usuarioAMTIMA;
			clave = passwordAMTIMA;
		}
		
		SOAPHeaderElement wsseSecurity =  new SOAPHeaderElement(new PrefixedQName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd","Security", "wsse"));
		wsseSecurity.setMustUnderstand(true);
		wsseSecurity.setActor(null);
		
		SOAPElement usernameToken = wsseSecurity.addChildElement("UsernameToken", "wsse");
		SOAPElement username = usernameToken.addChildElement("Username", "wsse");
		username.addTextNode(usuario); 
		
		SOAPElement password = usernameToken.addChildElement("Password", "wsse");
		password.addTextNode(clave);
		
		return wsseSecurity;
	}
	
	private WsVencimiento getVencimiento(BoletaPagoBO boleta) {
		String ordenVencimiento = "1";
		//String fechaVto = "250215";
		//String importe = "000000000125";
		
		

		DateTimeFormatter vtoFormatter = DateTimeFormatter.ofPattern("yyMMdd");
		String fechaVto = boleta.getIntencionDePago().format(vtoFormatter);

		//importe => 1.25 = "000000000125";
		BigDecimal importeBg = boleta.getTotal();				
		importeBg = importeBg.multiply(new BigDecimal(100)).setScale(0,  BigDecimal.ROUND_DOWN);
		String importe=String.format("%012d", importeBg.longValue() );
		
		WsVencimiento vto = new WsVencimiento(ordenVencimiento, fechaVto, importe);
		return vto;
	}
	
	private WsDeuda getDeuda(BoletaPagoBO boleta, String deudaIdDeuda) {		 
		String idDeuda = "";
		if ( deudaIdDeuda == null) {
			idDeuda = getDeudaIdDeuda(boleta); //"24119";		
		} else {
			idDeuda = deudaIdDeuda;
		}
		String concepto = getConceptoPago( boleta.getAporte().getCodigo() ); // "001";
		WsAbarcativa abarcativa = null;
		String importeMinimo=null;
		String importeMaximo=null;
		String discrecional=null;
		   
		WsVencimiento vto = getVencimiento(boleta);
		WsVencimiento[] vencimientos={vto};
		
		WsDeuda deuda = new WsDeuda(idDeuda, concepto, abarcativa, vencimientos, importeMinimo, importeMaximo, discrecional);
		return deuda;
	}
	
	private String getDeudaIdDeuda(BoletaPagoBO boleta) {
		//Ahora se manda el Nro de Boleta.-
		
		//return "00000"+boleta.getSecuencia();
		return String.format("%05d", boleta.getSecuencia());
		
		/*
		//Genero Periodo+secuencia DDJJ en formato: yyMM+(ultimo nro de la secuencia)
		//Si el periodo tiene mas de 9 rectificativas => SE REPITE idDeuda !!

		DateTimeFormatter periodoFormatter = DateTimeFormatter.ofPattern("yyMM");
		
		String idDeuda = null;
		String periodo = null;
		String ddjjSecuencia = null;

		DDJJBO ddjj = null;
		try { 
			ddjj = DdjjService.consultar(boleta.getDdjjId());
			periodo = ddjj.getPeriodo().format(periodoFormatter);
			ddjjSecuencia = ddjj.getSecuencia().toString();
			ddjjSecuencia = ddjjSecuencia.substring( ddjjSecuencia.length()-1 );
			
			idDeuda = periodo+ddjjSecuencia; //yyMM+ultimo nro de la secuencia
		} catch ( Exception e) {
			String errorMsg = messageSource.getMessage(BepBoletaPagoEnumException.BOLETA_DDJJ_INEXISTENTE.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(BepBoletaPagoEnumException.BOLETA_DDJJ_INEXISTENTE.name(), errorMsg);								
		}
		return idDeuda;	
		*/	
	}
	
}