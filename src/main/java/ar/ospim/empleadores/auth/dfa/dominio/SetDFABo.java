package ar.ospim.empleadores.auth.dfa.dominio;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SetDFABo {

	private String account;

	private String issuer;

	private String sharedSecret;

	public String generateAuthenticatorBarCode() {
		return "otpauth://totp/"
				+ URLEncoder.encode(this.getIssuer() + ":" + this.getAccount(), StandardCharsets.UTF_8).replace("+", "%20")
				+ "?secret=" + URLEncoder.encode(this.getSharedSecret(), StandardCharsets.UTF_8).replace("+", "%20")
				+ "&issuer=" + URLEncoder.encode(this.getIssuer(), StandardCharsets.UTF_8).replace("+", "%20");
	}
	
	@Override
	public String toString() {
		return "SetTwoFactorAuthenticationBo{" + "account='" + account + '\'' + ", issuer='" + issuer + '\'' + ", sharedSecret=*******}";
	}
}
