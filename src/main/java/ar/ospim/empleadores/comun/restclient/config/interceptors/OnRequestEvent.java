package ar.ospim.empleadores.comun.restclient.config.interceptors;

import org.springframework.context.ApplicationEvent;

import ar.ospim.empleadores.comun.restclient.infra.output.repository.RestClientMeasure;
import lombok.Getter;

@Getter
public class OnRequestEvent  extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7999858208800011174L;
	
	private RestClientMeasure restClientMeasure;

	public OnRequestEvent(RestClientMeasure restClientMeasure) {
		super(restClientMeasure);
		this.restClientMeasure = restClientMeasure;
	}

}
