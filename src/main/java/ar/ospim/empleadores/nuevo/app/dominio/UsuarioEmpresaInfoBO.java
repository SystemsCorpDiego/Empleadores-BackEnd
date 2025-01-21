package ar.ospim.empleadores.nuevo.app.dominio;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioEmpresaInfoBO {
    private Integer id;

    private String email;

    private Integer empresaId;

    private String razonSocial;

	private  String cuit;

	private  boolean actividadMolinera;

	private LocalDateTime previoLogin;

    public UsuarioEmpresaInfoBO(Integer id, String email, Integer empresaId, String razonSocial, String cuit, LocalDateTime previoLogin) {
        this.id = id;
        this.email = email;
        this.empresaId = empresaId;
        this.razonSocial = razonSocial;
        this.cuit = cuit;
		this.previoLogin = previoLogin;
    }

}
