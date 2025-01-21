package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DDJJBoletaArmadoDto {
    
    @JsonProperty("declaracion_jurada_id")
    private Integer declaracionJuradaId;

    private Integer ddjjNro;
    
    @JsonProperty("tipo_ddjj")
    private String tipoDdjj; //"Original",
    
    private LocalDate periodo; 
    
    @JsonProperty("detalle_boletas")
    private List<DDJJBoletaArmadoDetalleDto> boletas;

}
