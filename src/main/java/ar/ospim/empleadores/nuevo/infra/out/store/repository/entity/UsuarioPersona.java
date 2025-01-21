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
@Table(name = "usuario_persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioPersona implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5066640664529669420L;

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   @Column(name = "usuario_id")
   private Integer usuarioId;

   @Column(name = "nombre", length = 100)
    private String nombre;

   @Column(name = "apellido", length = 100)
    private String apellido;
   
   @Column(name = "email", length = 100)
    private String email;
   
   @Column(name = "mail_empre_alta", length = 100)
   private Boolean notificaciones;
}
