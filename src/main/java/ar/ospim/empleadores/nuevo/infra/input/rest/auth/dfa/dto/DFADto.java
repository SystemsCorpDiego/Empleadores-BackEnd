package ar.ospim.empleadores.nuevo.infra.input.rest.auth.dfa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DFADto {

	private String semillaCodigoBarra;

	private String semilla;

}
