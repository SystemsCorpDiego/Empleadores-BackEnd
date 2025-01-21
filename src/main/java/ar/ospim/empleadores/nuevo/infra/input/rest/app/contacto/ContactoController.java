package ar.ospim.empleadores.nuevo.infra.input.rest.app.contacto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.contacto.dto.ContactoOspimDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("ospim/contacto")
@RequiredArgsConstructor
public class ContactoController {

	@Value("${app.contacto.whatsap}")
    protected String whatsap;

	@Value("${app.contacto.tel}")
    protected String tel;

	@Value("${app.contacto.mail}")
    protected String mail;
	 
	@Value("${app.contacto.diasHorarios}")
    protected String diasHorarios;

	@GetMapping()
	public ResponseEntity<ContactoOspimDto>  consultar() {
		
		ContactoOspimDto reg = new ContactoOspimDto(); 
		reg.setEmail(mail);
		reg.setTelefono(tel);
		reg.setWhasap(whatsap);		
		reg.setDiasHorarios(diasHorarios);
		
		return ResponseEntity.ok( reg );
	}	 
	
}
