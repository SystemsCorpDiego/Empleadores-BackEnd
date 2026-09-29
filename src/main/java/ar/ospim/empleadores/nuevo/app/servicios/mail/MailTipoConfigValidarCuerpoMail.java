package ar.ospim.empleadores.nuevo.app.servicios.mail;

public interface MailTipoConfigValidarCuerpoMail {

	public boolean run(Integer mailTipo, String cuerpo);
	public String getVariables(Integer mailTipo);
}
