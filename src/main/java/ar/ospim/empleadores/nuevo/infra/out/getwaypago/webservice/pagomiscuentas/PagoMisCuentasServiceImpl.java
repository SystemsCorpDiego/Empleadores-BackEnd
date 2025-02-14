package ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.pagomiscuentas;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.comun.exception.WebServiceException;
import ar.ospim.empleadores.nuevo.app.dominio.BancoMovimientoBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.pagomiscuentas.dto.Due_Dates;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.pagomiscuentas.dto.Factura;
import ar.ospim.empleadores.nuevo.infra.out.store.AporteStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EntidadEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PagoMisCuentasServiceImpl implements PagoMisCuentasService {
	
	@Value("${getwayPago.pagoMisCuentas.host}")
	private  String host;
	
	@Value("${getwayPago.pagoMisCuentas.method}")
	private  String method;
	
	@Value("${getwayPago.pagoMisCuentas.token}")
	private  String token;
	
	@Value("${getwayPago.pagoMisCuentas.tokenAmtima}")
	private  String token_amtima;
	
	private final DateTimeProvider dateTimeProvider;
	private final AporteStorage aporteStorage;
	
	@Override
	public String generarBep(BoletaPagoBO boleta) {
		log.debug("generarBep() - boleta: {}", boleta);
		
		String strRequest = "";
		ObjectMapper objectMapper = new ObjectMapper();
	    RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_JSON);
		Factura request = getFactura(boleta);
		log.debug("generarBep() - request - Factura: {}", request);
		
		try {
			strRequest = objectMapper.writeValueAsString(request);
			HttpEntity<String> hRequest = new HttpEntity<String>(strRequest, headers);
			
			String aux = restTemplate.postForObject(host+ method, hRequest, String.class);
			
			log.debug("generarBep() - response - aux: {}", aux);
			return request.getInvoice_id();			
		} catch (HttpStatusCodeException e) {
			try {
				String responseString = e.getResponseBodyAsString();
				String ret= "";
				Gson gson = new Gson();
				Map<String, Object> res = new Gson().fromJson(responseString, Map.class);
				JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
				JsonArray array = jsonObject.getAsJsonArray("errors"); 
				for (JsonElement obj : array) {
		            JsonObject gsonObj = obj.getAsJsonObject();
		            String error = gsonObj.get("code").getAsString()+" - "+gsonObj.get("title").getAsString();
		            ret += error.trim()+" - ";
				}    
				throw new WebServiceException( String.valueOf(e.getStatusCode().value()), ret, e);
			} catch ( Exception e2) {
				throw new WebServiceException( "-1", e2.getMessage(), e2);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			log.debug( " JsonProcessingException - request: " + request.toString() );
			throw new WebServiceException( "-2", e.getMessage(), e);
		}  catch ( Exception e2) {
			log.debug( " Exception - e2: " + e2 );
			throw new WebServiceException( "-3", e2.getMessage(), e2);
		}
	}

	private Factura getFactura(BoletaPagoBO boleta) {
		Factura factura = new Factura();
		
		if ( boleta.getAporte().getEntidad().equals( EntidadEnum.AMTIMA.getCodigo() ) ) {
			factura.setCompany_id(token_amtima);
		} else {
			factura.setCompany_id(token);
		}
		
		factura.setCustomer_id( boleta.getEmpresa().getCuit() );
		factura.setInvoice_id( boleta.getSecuencia().toString() );
		
		BancoMovimientoBO movBancario = aporteStorage.findMovimientoByAporte(boleta.getAporte().getCodigo());		
		factura.setScreen_description( movBancario.getTipo() ); //Tipo movimiento Bancario => banco_convenio_mov_tipo.tipo
		
		factura.setInvoice_description(factura.getScreen_description() ); 
		factura.setCurrency("032");		
		
		Due_Dates dd =new Due_Dates();
		DecimalFormat df = new DecimalFormat("#.00");
		dd.setAmount( df.format(boleta.getImporte())  );
		dd.setDue_date(dateTimeProvider.getToStringBEP(boleta.getIntencionDePago()));
		dd.setDue_date_hour("23:59:59");
		
		List<Due_Dates>ldd=new ArrayList<Due_Dates>();
		ldd.add(dd);
		factura.setDueDates(ldd);
		
		
		return factura;
	}
	
}
