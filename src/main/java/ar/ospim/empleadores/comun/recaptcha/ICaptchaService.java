package ar.ospim.empleadores.comun.recaptcha;

import ar.ospim.empleadores.comun.recaptcha.servicio.dominio.RecaptchaPublicConfigBo;

public interface ICaptchaService {

    void validRecaptcha(String response, String frontUrl);

    boolean isRecaptchaEnable();

    RecaptchaPublicConfigBo getPublicConfig();

}
