package ar.ospim.empleadores.nuevo.infra.out.store.repository.querys;

import java.math.BigDecimal;
import java.time.LocalDate;

import ar.ospim.empleadores.nuevo.infra.out.store.enums.DDJJEstadoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DDJJTotales {
	private Integer id;
	private String estado;
	private LocalDate presentada;
	private Integer empresaId;
	private String cuit;
	private String razonSocial;
	private LocalDate periodo;
	private Integer secuencia; 
	private String aporte;
	private BigDecimal importe;
	private Integer ddjjVigente;
	
	public String getEstado() {
		if ( ddjjVigente != null && id != null && id.equals(ddjjVigente) )
			return this.estado;		
		return DDJJEstadoEnum.NO_VIGENTE.getCodigo();
	}
		
}
