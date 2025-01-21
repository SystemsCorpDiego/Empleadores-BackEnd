package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "aporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Aporte {

	@Id @Column(length = 20, nullable = false)
    private String codigo;
	
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Column(name = "entidad", length = 20)
    private String entidad;
    
    @Column(name = "orden")
    private Integer orden;
    
    private Boolean ddjj;
}
