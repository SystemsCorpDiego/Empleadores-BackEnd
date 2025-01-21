package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAfiliadoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DDJJBoletasDetalleDto {
    private String codigo;
    private String descripcion;
    private LocalDateTime vencimiento;
    private String periodo;
	private BigDecimal total_acumulado; //": 21810,
	private BigDecimal interes;
    //ajustes: [}
    private BigDecimal total_final; //": 21810,
    private LocalDate intencionDePago; 
    private List<DDJJBoletaArmadoDetalleAfiliadoDto> afiliados;

    private String formaDePago;    
    private Integer declaracion_jurada_id;
    private String tipo_ddjj; //: "Original",
    private String numero_boleta;

}
