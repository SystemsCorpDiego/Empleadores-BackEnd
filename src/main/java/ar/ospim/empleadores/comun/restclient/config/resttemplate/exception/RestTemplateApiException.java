package ar.ospim.empleadores.comun.restclient.config.resttemplate.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestTemplateApiException extends Exception {

    private HttpStatus statusCode;

    private String body;

    public RestTemplateApiException(HttpStatus statusCode, String message, String body) {
        super(message);
        this.statusCode = statusCode;
        this.body = body;
    }

    public <T> T mapErrorBody(Class<T> responseType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(body, responseType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}