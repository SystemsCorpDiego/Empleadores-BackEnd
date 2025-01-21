package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

import lombok.Getter;

@Getter
public enum DDJJStorageEnumException {
	PERIODO_FUTURO,
    EMPRESA_USUARIO_INEXISTENTE,
    REGISTRO_DUPLICADO,
    ESTADO_PRESENTADA,
    USUARIO_EMPRESA_DIFERENTE,
    CUILES_INVALIDOS,
    NO_AFI_DETA,
    PRESENTAR_VAL,
    ESTADO_PENDIENTE_DUP,
    ESTADO_BG_ERROR,
    PERIODO_ANTERIOR,
    PERIODO_ANTERIOR_ULTIMO,
    ;
	
	public String getMsgKey() {
		return "ddjj." + this.name();
	}
}
