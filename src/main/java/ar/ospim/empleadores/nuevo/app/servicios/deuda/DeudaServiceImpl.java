package ar.ospim.empleadores.nuevo.app.servicios.deuda;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.GestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.AporteStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.DDJJStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.DeudaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ActaMolineros;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DeudaServiceImpl implements DeudaService {
	
	private final MessageSource messageSource;
	
	private DeudaStorage deudaStorage;	
	private EmpresaStorage empresaStorage;
	private DDJJStorage ddjjStorage; 	
    private AporteStorage aporteStorage;

    
    public List<ActaMolineros>  getMolinerosActas(Integer empresaId) {
		
		Optional<EmpresaBO> empresa = empresaStorage.findById(empresaId);
		if ( empresa.isEmpty()  ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
					String.format(errorMsg, empresaId)  );
		}
		
		List<ActaMolineros> rta = null;
		rta = deudaStorage.getActasMolineros( empresa.get().getCuit() );
		
		return rta; 
	}
	
	public List<DeudaNomina>  getDDJJ(Integer empresaId) {
		Optional<EmpresaBO> empresa = empresaStorage.findById(empresaId);
		if ( empresa.isEmpty()  ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
					String.format(errorMsg, empresaId)  );
		}
		List<DeudaNomina> rta = null;
		rta = deudaStorage.getNomina( empresa.get().getCuit() );
		
		return rta; 
	}

	public List<IGestionDeudaDDJJDto>  getDDJJDto(Integer empresaId) {
		String cuit = null;
		
		Optional<EmpresaBO> empresa = empresaStorage.findById(empresaId);
		if ( empresa.isEmpty()  ) {
			String errorMsg = messageSource.getMessage(CommonEnumException.REGISTRO_INEXISTENTE_ID.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_INEXISTENTE_ID.name(), 
					String.format(errorMsg, empresaId)  );
		}
		
		return deudaStorage.getNominaDto( empresa.get().getCuit() );
		
	}
	
	
	public List<GestionDeudaDDJJDto> actualizarSecuencia(List<GestionDeudaDDJJDto> lst) {
		AporteBO aporte; 
		for(GestionDeudaDDJJDto reg : lst) {
			
			aporte = aporteStorage.findByCodigo(reg.getAporteCodigo());
			if ( aporte != null ) 
				reg.setAporteDescripcion( aporte.getDescripcion() );
		    
			//imprimimos el objeto pivote
		    System.out.println(reg);
		}
		return lst;
	}

	public List<GestionDeudaDDJJDto> actualizarAporteDescrip(List<GestionDeudaDDJJDto> lst){
		
		return lst;
	}
}
