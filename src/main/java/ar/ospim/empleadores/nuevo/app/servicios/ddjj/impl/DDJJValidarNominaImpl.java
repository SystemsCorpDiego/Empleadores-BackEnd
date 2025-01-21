package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.afiliado.AfiliadoService;
import ar.ospim.empleadores.nuevo.app.servicios.afiliado.ValidarCUIL;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJValidarNomina;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarCuilDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarErrorDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DDJJValidarNominaImpl implements DDJJValidarNomina {
	
	private final ValidarCUIL validarCUIL;
	private final AfiliadoService afiliadoService;
	
	@Override
	public List<DDJJValidarCuilDto> run(List<DDJJValidarCuilDto> lstCuiles) {
		
		if(lstCuiles == null || lstCuiles.size() == 0) {
			return lstCuiles;
		}
		
		for(DDJJValidarCuilDto cuil: lstCuiles) {
			if ( cuil != null ) {
				cuil.setCuilValido( validarCUIL.run(cuil.getCuil()) );
				 
				if ( cuil.getCuilValido() ) {			
					List<AfiliadoBO> afiliados = afiliadoService.get(cuil.getCuil());
					if ( afiliados != null && afiliados.size()>0) {
						cuil.setApellido( afiliados.get(0).getApellido() );
						cuil.setNombre( afiliados.get(0).getNombre() );
						cuil.setInte( afiliados.get(0).getInte() );
					}
				} 
			}
		}
		return lstCuiles;
	}
	
	@Override
	public Optional<List<DDJJValidarErrorDto>> run(DDJJBO ddjj) {
		if(ddjj == null ) {
			return Optional.empty();
		}
		if(ddjj.getEmpleados() == null || ddjj.getEmpleados().size() == 0) {
			return Optional.empty();
		}
		
		List<DDJJValidarErrorDto> lstErrores = new ArrayList<DDJJValidarErrorDto>(); 
		Optional<DDJJValidarErrorDto> error = null;
		for(DDJJEmpleadoBO emple: ddjj.getEmpleados()) {
			error = getErrorRegistro(emple);
			if ( error.isPresent() ) {
				lstErrores.add(error.get());
			}
		}		
		if ( lstErrores.size() > 0) {
			return Optional.of(lstErrores);
		}
		return Optional.empty();
	}

	@Override
	public boolean esValida(DDJJBO ddjj) {
		Optional<DDJJValidarErrorDto> error = null;
		for ( DDJJEmpleadoBO emple : ddjj.getEmpleados() ) {
			error = getErrorRegistro(emple);
			if ( error.isPresent() )  {
				return false;
			}
		}
		return true;		
	}
		
	private Optional<DDJJValidarErrorDto> getErrorRegistro(DDJJEmpleadoBO emple) {
		DDJJValidarErrorDto rta = null;
		if ( emple.getAfiliado() != null && emple.getAfiliado().getCuil() != null ) {
			if ( !validarCUIL.run(emple.getAfiliado().getCuil()) ) {			
				rta = new DDJJValidarErrorDto();
				rta.setCuil(emple.getAfiliado().getCuil());
				rta.setDescripcion("cuil invalido");							
				return Optional.of(rta);
			}
			
			//Si algun cuil no existe en Afiliado , debe venir Apellido y Nombre !!
			if ( emple.getAfiliado().getApellido() == null || emple.getAfiliado().getNombre() == null  ) {
				 List<AfiliadoBO> lst = afiliadoService.get( emple.getAfiliado().getCuil() );
				 if ( lst == null || lst.size() == 0 ) {
				    rta = new DDJJValidarErrorDto();
					rta.setCuil(emple.getAfiliado().getCuil());
					rta.setDescripcion("Cuil sin registrar en la base de Afiliados: Debe informar Apellido y Nombre.");							
					return Optional.of(rta);
				 }			
			}
		} else {
			rta = new DDJJValidarErrorDto();
			rta.setCuil("--");
			rta.setDescripcion("cuil vacio");							
			return Optional.of(rta);
		}
		return Optional.empty();
	}
	
}
