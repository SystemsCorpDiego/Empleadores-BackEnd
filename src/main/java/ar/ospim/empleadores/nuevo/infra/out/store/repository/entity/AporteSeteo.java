package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "aporte_seteo")
@Getter
@Setter
@NoArgsConstructor 
@ToString
public class AporteSeteo {
	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String aporte;

    private LocalDate desde;
    private LocalDate hasta;

    private String  entidad;
    private Boolean  socio;
    private String  calculoTipo;
    private BigDecimal  calculoValor;
    private String  calculoBase;
    private String  camara;
    private String  camaraCategoria;
    private Integer  camaraAntiguedad;

}
