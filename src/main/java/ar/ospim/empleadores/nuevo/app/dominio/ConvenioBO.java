package ar.ospim.empleadores.nuevo.app.dominio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter 
@ToString
public class ConvenioBO {
	 public ConvenioBO(Integer id) {
			super();
			this.id = id;
		}

		private Integer id;
		
		EmpresaBO empresa;
		
}
