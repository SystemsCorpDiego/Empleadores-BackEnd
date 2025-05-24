package ar.ospim.empleadores.nuevo.app.servicios.convenio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioConsultaFiltro;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioCuotaChequeAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioCuotaConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.ConvenioMapper;
import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ActaMolinerosRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AjusteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioActaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioAjusteRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioCuotaChequeRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioCuotaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioDdjjDeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioDdjjRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DDJJRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.DeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.EmpresaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioActa;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuota;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioCuotaCheque;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjjDeudaNomina;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DeudaNomina;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Empresa;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConvenioServiceImpl implements ConvenioService {

	private final ConvenioMapper mapper;
	
	private final EmpresaRepository empresaRepository;
	private final ConvenioStorage storage;
	private final ActaMolinerosRepository actaMolinerosRepository;
	private final ConvenioActaRepository convenioActaRepository;  
	private final ConvenioDdjjRepository convenioDdjjRepository;
	private final ConvenioAjusteRepository convenioAjusteRepository;
	private final ConvenioCuotaRepository convenioCuotaRepository;
	private final ConvenioCuotaChequeRepository convenioCuotaChequeRepository;
	private final DDJJRepository ddjjRepository;
	private final ConvenioDdjjDeudaNominaRepository convenioDdjjDeudaNominaRepository;
	private final DeudaNominaRepository deudaNominaRepository;
	private final AjusteRepository ajusteRepository; 
	
	@Override
	public Convenio generar(ConvenioAltaDto dto) {
		 validarAlta(dto);
		 Convenio convenio = new Convenio();
		 convenio.setCreatedOn(LocalDateTime.now());
		 
		 Empresa empresa = empresaRepository.getById(dto.getEmpresaId());
		 
		 convenio.setEmpresa(empresa);
		 convenio.setEntidad(dto.getEntidad());
		 convenio.setEstado("PENDIENTE");
		 
		 //Estos hay que calcularlos segun detalle
		 convenio.setImporteDeuda( BigDecimal.ZERO );
		 convenio.setImporteIntereses( BigDecimal.ZERO );
		 convenio.setImporteSaldoFavor( BigDecimal.ZERO );
		 
		 convenio.setIntencionDePago( dto.getFechaPago() );
		 convenio.setCuotasCanti(dto.getCantidadCuota());
		 convenio.setMedioPago("CHEQUE");
		 
		 calcularCuotas( convenio);
		 
		 List<ConvenioActa> actas = new ArrayList<ConvenioActa>();
		 ConvenioActa aux = null;
		 for(Integer reg : dto.getActas()) {
			 aux = new ConvenioActa(); 
			 aux.setConvenio(convenio);
			 aux.setActa( actaMolinerosRepository.getById(reg) );
			 actas.add(  aux );		     
		 }		 		 
		 convenio.setActas(actas);
		 
		 List<ConvenioDdjj> ddjjs = new ArrayList<ConvenioDdjj>();
		 ConvenioDdjj auxDDJJ = null;
		 for(Integer reg : dto.getDdjjs()) {
			 auxDDJJ = new ConvenioDdjj();
			 auxDDJJ.setConvenio(convenio);
			 auxDDJJ.setDdjj( ddjjRepository.getById(reg) );
			 
			 List<DeudaNomina> lstDeudaNomina = deudaNominaRepository.findByDdjjIdAndEntidadAndActaIdIsNull(reg, convenio.getEntidad() );
			 auxDDJJ.setDdjjDeudaNomina( new ArrayList<ConvenioDdjjDeudaNomina>());
			 ConvenioDdjjDeudaNomina auxCDDN = null;
			 for ( DeudaNomina dn: lstDeudaNomina) {
				 auxCDDN = new ConvenioDdjjDeudaNomina();
				 auxCDDN.setConvenioDdjj(auxDDJJ);
				 
				 auxCDDN.setAporte(dn.getAporte().getCodigo());
				 auxCDDN.setAporteImporte(dn.getImporte());
				 auxCDDN.setInteres(dn.getInteres());
				 auxCDDN.setVencimiento( dn.getVencimiento() );
				 
				 auxDDJJ.getDdjjDeudaNomina().add(auxCDDN);
			 }
			 
			 ddjjs.add( auxDDJJ );
		 }
		 convenio.setDdjjs(ddjjs);
		 
		 
		 List<ConvenioAjuste> ajustes = new ArrayList<ConvenioAjuste>();
		 ConvenioAjuste auxAjuste = null;
		 for(Integer reg : dto.getAjustes()) {
			 auxAjuste = new ConvenioAjuste();
			 auxAjuste.setConvenio(convenio);
			 auxAjuste.setAjuste( ajusteRepository.getById(reg) );
			 auxAjuste.setImporte(BigDecimal.ZERO); //TODO: 
			 ajustes.add(auxAjuste);
		 }
		 convenio.setAjustes(ajustes);
		 
		 convenio = storage.guardar(convenio);
		 if ( convenio.getActas() != null ) {
			 for (ConvenioActa ca:  convenio.getActas()) {
				 ca = convenioActaRepository.save(ca);				 
			 }
		 }
		 if ( convenio.getDdjjs() != null ) { 
			for (ConvenioDdjj cd:  convenio.getDdjjs()) {
				 cd = convenioDdjjRepository.save(cd);
				 for (ConvenioDdjjDeudaNomina cddn:  cd.getDdjjDeudaNomina()) {
					 cddn = convenioDdjjDeudaNominaRepository.save(cddn);
				 }
			}
		 }
		 if ( convenio.getAjustes() != null ) { 
				for (ConvenioAjuste caj:  convenio.getAjustes()) {
					caj = convenioAjusteRepository.save(caj);
				}
		}
		
		 if ( convenio.getCuotas() != null ) {
			 for (ConvenioCuota caj:  convenio.getCuotas()) {
					caj = convenioCuotaRepository.save(caj);
				}
		 }
		 return convenio;
		//	repository.flush();
	}
	
	public Convenio cambiarEstado(Integer empresaId, Integer convenioId, String estado) {
		//cambio estado
		Convenio  convenio = storage.getById(convenioId);
		convenio.setEstado(estado);
		convenio = storage.guardar(convenio);
		
		//TODO: falta enviar convenio a molineros !!!
		
		return convenio;		
	}
	
	private void calcularCuotas(Convenio convenio) {
		ConvenioCuota cuota = null;
		Integer cantidad = convenio.getCuotasCanti();
		LocalDate vencimiento = convenio.getIntencionDePago().minusMonths(1);
		BigDecimal impCuota = BigDecimal.ZERO;
		if ( convenio.getImporteIntereses() != null && convenio.getImporteIntereses().compareTo( BigDecimal.ZERO )  == 0 && cantidad!=null && cantidad>0  ) {
			impCuota = convenio.getImporteDeuda().add(convenio.getImporteIntereses()).divide(BigDecimal.valueOf(cantidad)); 
		}
		convenio.setCuotas( new ArrayList<ConvenioCuota>() );
		for (int cuotaNro = 1; cuotaNro <= cantidad; cuotaNro++) {
			cuota = new ConvenioCuota();
			cuota.setConvenio(convenio);
			cuota.setCuotaNro(cuotaNro);
			cuota.setVencimiento( vencimiento.plusMonths(1*cuotaNro) );
			cuota.setImporte( impCuota );
			convenio.getCuotas().add(cuota);
		}
	}
	
	private void validarAlta(ConvenioAltaDto dto) {
		
	}
	
	public Convenio get(Integer empresaId, Integer convenioId) {		
		Convenio convenio = storage.get(convenioId);		
		return convenio;
	}
		
	public List<Convenio> get(ConvenioConsultaFiltro filtro) {
		List<Convenio> lst = null;		
		lst = storage.get(filtro);		
		return lst;
	}
	
	
	public List<ConvenioCuotaConsultaDto> getCuotas(Integer empresaId, Integer convenioId){
		List<ConvenioCuotaConsultaDto> rta = null;
		List<ConvenioCuota> lstCuotas = convenioCuotaRepository.findByConvenioId(convenioId);
		rta = mapper.run6(lstCuotas);
		
		String listCheques = "";
		BigDecimal impTotal = BigDecimal.ZERO; 
		List<ConvenioCuotaCheque> lstCheques = null;
		for(ConvenioCuotaConsultaDto convenioCuota: rta) {
			listCheques = "";
			impTotal = BigDecimal.ZERO;
			lstCheques = convenioCuotaChequeRepository.findByConvenioCuotaId(convenioCuota.getId());
			for(ConvenioCuotaCheque cheque: lstCheques) {
				listCheques = listCheques + ", " + cheque.getNumero();
				impTotal = impTotal.add(cheque.getImporte());				
			}
			if ( !listCheques.equals("")) {
				listCheques = listCheques.substring(2);
			}
			convenioCuota.setChequesNro(listCheques);
			convenioCuota.setChequestotal(impTotal);
		}
		
		return rta;
	}

	public ConvenioCuotaCheque generar(ConvenioCuotaChequeAltaDto cheque) {
		ConvenioCuotaCheque rta = null;
		ConvenioCuotaCheque reg = new ConvenioCuotaCheque();
		
		ConvenioCuota convenioCuota = convenioCuotaRepository.getById(cheque.getCuotaId());
		reg.setConvenioCuota(convenioCuota);
		
		reg.setFecha(cheque.getFecha());
		reg.setImporte(cheque.getImporte());
		reg.setNumero(cheque.getNumero());
		
		rta = convenioCuotaChequeRepository.save(reg);
		
		return rta;
	}
	
	public ConvenioCuotaCheque actualizar(ConvenioCuotaChequeAltaDto cheque) {
		ConvenioCuotaCheque reg = convenioCuotaChequeRepository.getById(cheque.getChequeId());
		reg.setNumero(cheque.getNumero());
		reg.setFecha(cheque.getFecha());
		reg.setImporte(cheque.getImporte());
		reg = convenioCuotaChequeRepository.save(reg);
		return reg;
	}

	public List<ConvenioCuotaCheque> getCheques(Integer empresaId, Integer convenioId, Integer cuotaId){
		 List<ConvenioCuotaCheque> rta = null;
		 rta = convenioCuotaChequeRepository.findByConvenioCuotaId(cuotaId);
		 return rta;
	}

	public ConvenioCuotaCheque getCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId) {
		 ConvenioCuotaCheque rta = null;
		 rta = convenioCuotaChequeRepository.getById(chequeId);
		 return rta;
	}
	
	public void borrarCheque(Integer empresaId, Integer convenioId, Integer cuotaId, Integer chequeId) {
		convenioCuotaChequeRepository.deleteById(chequeId);
	}
}
