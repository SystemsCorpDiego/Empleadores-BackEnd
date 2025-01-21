package ar.ospim.empleadores.nuevo.app.dominio;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolBO {
	private Short id;
	private String descripcion;
	private List<FuncionalidadBO> funcionalidades;
}
