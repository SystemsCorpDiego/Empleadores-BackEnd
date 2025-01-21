package ar.ospim.empleadores.nuevo.app.servicios.publicacion;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import ar.ospim.empleadores.nuevo.app.dominio.PublicacionBO;

public interface PublicacionService {
	
	public PublicacionBO crear(PublicacionBO feriado);	
	public void borrar(Integer publicacionId);	
	public List<PublicacionBO> consultar();   
	public List<PublicacionBO> consultarVigentes();   
	public void guardarArchivo(Integer publicacionId, MultipartFile archivo);
	public Resource getArchivo(Integer publicacionId);
	
}
