package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DDJJEmpleadoDto {	
	private Integer id;
	private String cuil; //PK tabla Afiliado 
	private Integer inte; //PK tabla Afiliado 
	private String apellido; //se guardan en alta de Afiliado
	private String nombre; //se guardan en alta de Afiliado
	private LocalDate fechaIngreso;
	private Integer empresaDomicilioId;
	private String camara;
	private String categoria;
	private BigDecimal remunerativo;
	private BigDecimal noRemunerativo;
	
	private Boolean uomaSocio ;
	private Boolean amtimaSocio; 
    
	private List<DDJJEmpleadoAporteDto> aportes;
	
	/*

	modificado_0	false
	documento_tipo_0	
	docu_numero_0	
	 
	cuo_soc_uoma_0	true
	apo_sol_uoma_0	true
	art_46_0	true
	 */
}
