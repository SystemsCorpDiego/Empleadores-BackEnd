package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoletaPagoConsDDJJDtoVEEER {
    private Integer declaracion_jurada_id;
    private String tipo_ddjj; //"Original",
    private LocalDateTime periodo;

    private Integer numero_boleta;
    
    private String codigo; //Concepto
    private String descripcion; //Concepto descrip
    
    private LocalDate intencionDePago;
    private LocalDate fecha_de_pago;

    private BigDecimal total_final;
    private BigDecimal importe_recibido;
    private String formaDePago;
    private LocalDate vencimiento;
    private BigDecimal total_acumulado;
    private BigDecimal interes;
    private BigDecimal ajuste;

    private List<BoletaPagoConsDDJJAfiDto> afiliados;
}
