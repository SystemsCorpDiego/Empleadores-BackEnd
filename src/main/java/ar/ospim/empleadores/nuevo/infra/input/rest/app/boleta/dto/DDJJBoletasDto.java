package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DDJJBoletasDto {
    private Integer declaracion_jurada_id;
    private String tipo_ddjj; //Original
    private String periodo; //  "01-2024",
    private List<DDJJBoletasDetalleDto> detalle_boletas;
   
}
