package ar.ospim.empleadores.nuevo.app.servicios.empresa;

import lombok.Getter;

@Getter
public enum EmpresaStorageEnumException {
	REGISTRO_INEXISTENTE,
    EMPRESA_USUARIO_INEXISTENTE,
    REGISTRO_DUPLICADO
    ;
	
	public String getMsgKey() {
		return "empresa." + this.name();
	}
	
}
