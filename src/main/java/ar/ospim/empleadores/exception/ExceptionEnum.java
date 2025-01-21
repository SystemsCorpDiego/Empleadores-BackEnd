package ar.ospim.empleadores.exception;

public enum ExceptionEnum {

	UNEXPECTED_JAVA_ERROR(-1001L),
	APP_SETTING(-1002L),
	APP_USE(-1003L),
	BUSINESS_SETTING(-1004L),
	BUSINESS_USER(1020L),
	REPOSITORY(-1010L),
	SERVICE(-1011L),
	WS_CALL(-1012L),
	;
	
	private Long codigo;
	
	private ExceptionEnum(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

}
