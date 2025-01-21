package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoletaPagoConsConDDJJDto {
	private Integer id;
    
	private Integer empresaId;
	private String cuit;
	private String razonSocial;
	
	private String codigo; //Concepto
    
    private String descripcion; //Concepto descrip
    
    private LocalDate vencimiento; //TODO:falta completarlo
    
    private LocalDate periodo; 
    
    private BigDecimal totalConcepto;
    private BigDecimal totalAjuste;
    private BigDecimal interes;
    @JsonProperty("total_final")
    private BigDecimal totalFinal;
    
    private LocalDate intencionDePago;
    
    @JsonProperty("fecha_de_pago")
    private LocalDate fechaDePago;
    
    @JsonProperty("importe_recibido")
    private BigDecimal importeRecibido;

    private String formaDePago; //Descripcion de FormaPago
    
    private Boolean requiereBep; //Booleano para nostrar o no icono Generacion VEP

    @JsonProperty("tipo_ddjj")
    private String tipoDdjj; //"Original",
    
    @JsonProperty("numero_boleta")
    private Integer numeroBoleta;
    
    @JsonProperty("declaracion_jurada_id")
    private Integer declaracionJuradaId;
    
    private LocalDate baja;
}
