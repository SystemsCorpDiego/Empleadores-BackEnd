package ar.ospim.empleadores.nuevo.infra.out.store.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.infra.out.store.ConvenioStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioDdjjDeudaNominaRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.ConvenioRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Convenio;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjj;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.ConvenioDdjjDeudaNomina;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.ConvenioDdjjDeudaNominaConsultaI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConvenioStorageImpl implements ConvenioStorage {

	 private final ConvenioRepository repository;
	 private final ConvenioDdjjDeudaNominaRepository convenioDdjjDeudaNominaRepository ; 
	 
	 @Transactional
	 @Override
	public Convenio guardar(Convenio reg) {
		// TODO Auto-generated method stub

		Convenio regNew = repository.save(reg);
		return regNew;
	}

	@Override
	public Convenio get(Integer id) {
		
		Convenio convenio = repository.getById(id);
		
		List<ConvenioDdjjDeudaNominaConsultaI> lst = null;
		List<ConvenioDdjjDeudaNomina> lst2 = null;
		ConvenioDdjjDeudaNomina aux = null;
		for( ConvenioDdjj reg : convenio.getDdjjs()) {
			lst = convenioDdjjDeudaNominaRepository.findDeudaNomina(reg.getId());
			if ( lst.size() > 0 ) {
				reg.setDdjjDeudaNomina(new ArrayList<ConvenioDdjjDeudaNomina>());
				
				for ( ConvenioDdjjDeudaNominaConsultaI regDN : lst) {
					aux = new ConvenioDdjjDeudaNomina();
					aux.setId( regDN.getId());
					aux.setAporte(regDN.getAporte());
					aux.setAporteImporte( regDN.getImporte());
					aux.setInteres( regDN.getInteres() );
					aux.setVencimiento( regDN.getVencimiento());
					aux.setBoletaId( regDN.getBoletaId() );
					reg.getDdjjDeudaNomina().add(aux);
				}
			}
			//log.error("test : lst" + lst.toString() );
		}		
		
		return convenio;		
	}

}
