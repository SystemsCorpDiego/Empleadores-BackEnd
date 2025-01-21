package ar.ospim.empleadores.auth.usuario.app;

public interface GetUsuarioIdByToken{
	
    Integer execute(String token);
}
