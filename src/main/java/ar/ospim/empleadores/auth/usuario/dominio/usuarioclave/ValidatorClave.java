package ar.ospim.empleadores.auth.usuario.dominio.usuarioclave;

import org.springframework.stereotype.Service;

@Service
public class ValidatorClave {

	private int mayus;
	private int min;
	private int num;
	private int especial;

	public boolean esValida(String newPassword) {

		
		mayus = 0;
		min = 0;
		num = 0;
		especial = 0;

		for(int i = 0; i < newPassword.length(); i++)
		{
			char ch = newPassword.charAt(i);
			if (ch >= 'A' && ch <= 'Z') {
				mayus++;
			} else {
				if (ch >= 'a' && ch <= 'z') {
					min++;
				} else {
					if (ch >= '0' && ch <= '9') {
						num++;
					} else {
						especial++;
					}
				}
			}			
		}

		if(mayus == 0 || min == 0 || num == 0 || especial ==0 || newPassword.length() < 8)
			return false;

		return true;
	}
}
