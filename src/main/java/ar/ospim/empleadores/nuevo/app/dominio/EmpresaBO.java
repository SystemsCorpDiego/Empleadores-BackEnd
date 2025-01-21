package ar.ospim.empleadores.nuevo.app.dominio;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter 
@ToString
public class EmpresaBO {

    public EmpresaBO(Integer id) {
		super();
		this.id = id;
	}

	private Integer id;

    private String razonSocial;

    private String cuit;
    
    private boolean actividadMolinera;
    
    private List<EmpresaDomicilioBO> domicilios = new ArrayList<EmpresaDomicilioBO>();
    private List<ContactoBO> contactos = new ArrayList<ContactoBO>();
    
    public void addContactos(ContactoBO contacto) {
    	if (contactos == null) {
    		contactos = new ArrayList<ContactoBO>();
        }
    	contactos.add(contacto);
    }
}
