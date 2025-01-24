package ar.ospim.empleadores.nuevo.app.dominio;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RolBO {
	private Short id;
	private String descripcion;
	private List<FuncionalidadBO> funcionalidades;
}
