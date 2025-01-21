package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "empresa_contacto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmpresaContacto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -932209727876626891L;

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "empresa_id")
    private Integer empresaId;
    
    @Column(name = "empresa_domicilio_id")
    private Integer empresaDomicilioId;
	
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "prefijo")
    private String prefijo;
    
    @Column(name = "valor")
    private String valor;
	
}
