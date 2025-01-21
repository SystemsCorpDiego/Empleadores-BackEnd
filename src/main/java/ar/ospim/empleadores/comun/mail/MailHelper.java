package ar.ospim.empleadores.comun.mail;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailHelper {

	public static boolean isValid(String mail) {
    	Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    	Matcher matcher = pattern.matcher(mail);
    	return matcher.find();
	}
	
}
