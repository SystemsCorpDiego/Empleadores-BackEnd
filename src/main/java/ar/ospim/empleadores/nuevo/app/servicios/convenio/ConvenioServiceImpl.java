package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ActaMolinerosRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioActaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConvenioServiceImpl implements ConvenioService {

	private final EmpresaService empresaService; 
	private final ConvenioStorage storage;
	private final ActaMolinerosRepository actaMolinerosRepository;
	private final ConvenioActaRepository convenioActaRepository;  
	
	@Override
	public Convenio generar(ConvenioAltaDto dto) {
		 validarAlta(dto);
		 Convenio convenio = new Convenio();
		 convenio.setCreatedOn(LocalDateTime.now());
		 
		 EmpresaBO empresa = empresaService.getEmpresa(dto.getEmpresaId());
		 
		 convenio.setCuit(empresa.getCuit());
		 convenio.setEntidad(dto.getEntidad());
		 convenio.setEstado("PENDIENTE");
		 
		 //Estos hay que calcularlos segun detalle
		 convenio.setImporteDeuda( BigDecimal.ZERO );
		 convenio.setImporteIntereses( BigDecimal.ZERO );
		 convenio.setImporteSaldoFavor( BigDecimal.ZERO );
		 
		 convenio.setIntencionDePago( dto.getFechaPago() );
		 convenio.setCuotas(dto.getCantidadCuota());
		 convenio.setMedioPago("CHEQUE");
		 
		 List<ConvenioActa> actas = new ArrayList<ConvenioActa>();
		 ConvenioActa aux = null;
		 for(Integer reg : dto.getActas()) {
			 aux = new ConvenioActa(); 
			 aux.setConvenio(convenio);
			 aux.setActa( actaMolinerosRepository.getById(reg) );
			 actas.add(  aux );		     
		 }		 		 
		 convenio.setActas(actas);
		 
		 convenio = storage.guardar(convenio);
		 for (ConvenioActa ca:  convenio.getActas()) {
			 ca = convenioActaRepository.save(ca);				 
		}
		 
		 return convenio;
		//	repository.flush();
	}
	
	private void validarAlta(ConvenioAltaDto dto) {
		
	}

}
