package ar.ospim.empleadores.comun.infra.input.service;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasicDataEmpresaDto implements Serializable {

	private static final long serialVersionUID = -4373366981710504002L;

	private Integer id;

    private String razonSocial;

    private String cuit;

    private String email;
 
    private String actividad_molinera;
}
