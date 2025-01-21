package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJTotalesEmpresaDto {
	  private Integer id;
	  private String estado;
	  private LocalDate presentada;
      private Integer empresaId;
      private String cuit;
      private String razonSocial;
      private LocalDate periodo;
      private Integer secuencia;
      private List<DDJJTotalesAportesDto> aportes;
      
      
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DDJJTotalesEmpresaDto other = (DDJJTotalesEmpresaDto) obj;
		return Objects.equals(id, other.id);
	}
      
      
}
