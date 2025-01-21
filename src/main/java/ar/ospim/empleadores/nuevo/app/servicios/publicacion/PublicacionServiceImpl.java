package ar.ospim.empleadores.nuevo.app.servicios.publicacion;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.PublicacionBO;
import ar.ospim.empleadores.nuevo.infra.out.store.PublicacionStorage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {
	
	private final MessageSource messageSource;
	private final PublicacionStorage storage;
	private final FileStorageService fileStorage; 	
		
		@Override
		public PublicacionBO crear(PublicacionBO feriado) {		
			return storage.save(feriado);
		}
		
		public void borrar(Integer feriadoId) {
			if (feriadoId == null ) {
				String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg);
			}
			storage.deleteById(feriadoId);
		}
		
		public List<PublicacionBO> consultar() {
			return storage.findAll();
		}
		
		public List<PublicacionBO> consultarVigentes() {
			List<PublicacionBO> lst = storage.findAll();
			LocalDate now = LocalDate.now();
			
			lst = lst.stream()
			.filter(pub -> ( ! now.isBefore( pub.getVigenciaDesde() ) ) && ( pub.getVigenciaHasta() == null ||now.isBefore( pub.getVigenciaHasta() ) ) )
			.collect(Collectors.toList());
			
			return lst; 
		}

		@Override
		public void guardarArchivo(Integer publicacionId, MultipartFile archivo) {
			fileStorage.store(publicacionId, archivo); 
		}
		
		@Override
		public Resource getArchivo(Integer publicacionId) {
			return fileStorage.get(publicacionId);
		}
}
