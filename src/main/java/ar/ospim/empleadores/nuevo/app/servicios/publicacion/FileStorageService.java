package ar.ospim.empleadores.nuevo.app.servicios.publicacion;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;

@Service
public class FileStorageService {
	
	@Value("${app.publicaciones.file-path}")
	private String path;
	
	private Path rootLocation;
	 
	
	public void store(Integer publicacionId, MultipartFile file) {
		this.rootLocation = Paths.get(path);
		try {
			Path destinationFile = this.rootLocation.resolve(
					Paths.get( getFileName(publicacionId) ))
					.normalize().toAbsolutePath();
			
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new BusinessException(CommonEnumException.CODIGO_INVALIDO.name(), "No se pudo guardar el Archivo");
		}
	}

	public  Resource get(Integer publicacionId) {
		try {
			Path file = load( getFileName(publicacionId) );
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new BusinessException(CommonEnumException.CODIGO_INVALIDO.name(), "Error recuperando el Archivo. Código Inexistente");
			}
		} catch (MalformedURLException e) {
			throw new BusinessException(CommonEnumException.CODIGO_INVALIDO.name(), "Error de lectura recuperando el Archivo. MalformedURLException");
		}
	}
	
	private String getFileName(Integer publicacionId) {
		return "pub-"+publicacionId+".jpg";
	}
	
	private Path load(String filename) { 
		this.rootLocation = Paths.get(path);
		return this.rootLocation.resolve(filename);
	}
}
