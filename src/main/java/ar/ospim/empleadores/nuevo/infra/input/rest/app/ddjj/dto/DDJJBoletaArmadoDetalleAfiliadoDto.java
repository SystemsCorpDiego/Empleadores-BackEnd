package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJBoletaArmadoDetalleAfiliadoDto {
	private String cuil;
	private String apellido;
	private String nombre;
	private BigDecimal remunerativo;
	private BigDecimal capital; //Es lo que paga por el Aporte de la Boleta
	
	public DDJJBoletaArmadoDetalleAfiliadoDto cloneDto() {
		DDJJBoletaArmadoDetalleAfiliadoDto regNew = new DDJJBoletaArmadoDetalleAfiliadoDto();
		regNew.setApellido( this.getApellido() );
		regNew.setNombre( this.getNombre() );
		regNew.setCuil( this.getCuil()  );
		regNew.setRemunerativo( this.getRemunerativo() );
		return regNew; 
	}
}
