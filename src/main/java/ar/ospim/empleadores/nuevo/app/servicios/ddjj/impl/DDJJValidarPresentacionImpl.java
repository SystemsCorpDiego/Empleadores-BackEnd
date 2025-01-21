package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJValidarPresentacion;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarErrorDto;

@Service
public class DDJJValidarPresentacionImpl implements DDJJValidarPresentacion {

	@Override
	public Optional<List<DDJJValidarErrorDto>> run(DDJJBO ddjj) {
		if(ddjj == null ) {
			return Optional.empty();
		}
		if(ddjj.getEmpleados() == null || ddjj.getEmpleados().size() == 0) {
			return Optional.empty();
		}
		Optional<List<DDJJValidarErrorDto>> lstAuxRta = null;
		List<DDJJValidarErrorDto> lstRta = new ArrayList<DDJJValidarErrorDto>(); 
		Optional<DDJJValidarErrorDto> rta = null;
		List<DDJJEmpleadoBO> lstAux;
		DDJJValidarErrorDto validarError;
		for(DDJJEmpleadoBO emple: ddjj.getEmpleados()) {
			lstAuxRta = validarRegistro(ddjj.getPeriodo(), emple);
			if ( lstAuxRta.isPresent() ) {
				lstRta = append( lstRta, lstAuxRta.get() ) ;
			}
			
			//TODO: Validar que no tenga CUIL repetidos
			if ( emple.getAfiliado() != null && emple.getAfiliado().getCuil() != null ) {
				lstAux = ddjj.getEmpleados().stream().filter(e -> e.getAfiliado() != null 
						&& e.getAfiliado().getCuil() != null 
						&& e.getAfiliado().getCuil().equals(emple.getAfiliado().getCuil()) )
						.collect(Collectors.toList());
				
				if ( lstAux != null &&  lstAux.size() > 1 ) {
					validarError = new DDJJValidarErrorDto();
					validarError.setCuil( emple.getAfiliado().getCuil()  );
					validarError.setCodigo("cuil");
					validarError.setDescripcion("cuil repetido");			
					lstRta.add(validarError);
				}
			}
			
		}		
		
		//TODO: Validar que no tenga CUIL repetidos
		for(DDJJEmpleadoBO emple: ddjj.getEmpleados()) {
			
		}
		
		if ( lstRta.size() > 0) {
			return Optional.of(lstRta);
		}
		return Optional.empty();
	}

	private Optional<List<DDJJValidarErrorDto>> validarRegistro(LocalDate periodo, DDJJEmpleadoBO emple) {
		DDJJValidarErrorDto rta = null;
		List<DDJJValidarErrorDto> rtaLst = new ArrayList<DDJJValidarErrorDto>();
		String cuil = "--";
		String valFaltante = "valor no informado";
		String valMenorUno = "valor menor a 0 ";
		String valPeriodoFuturo = "Periodo: debe ser menor al mes en curso";
		String valIngresoFuturo = "Fecha de Ingreso: valor mayor al Periodo";
		String valUomaAmtima = "Debe ser Socio de UOMA para poder asociarse a AMTIMA";
		boolean bErrorPeriodo = false;
		//TODO: Validar codigos FK
		
		if ( periodo == null ) {
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("periodo");
			rta.setDescripcion(valFaltante);			
			rtaLst.add(rta);
			bErrorPeriodo = true;
		} else {
			//el periodo debe ser ANTERIOR al 1ro del mes en curso.-			
			if ( !periodo.atStartOfDay().isBefore( LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay() ) ) {
				rta = new DDJJValidarErrorDto();
				rta.setCuil( cuil );
				rta.setCodigo("periodo");
				rta.setDescripcion(valPeriodoFuturo);			
				rtaLst.add(rta);
				bErrorPeriodo = true;
			}
		}
		

		
		if ( emple.getAfiliado() != null && emple.getAfiliado().getCuil() != null ) {
			
			if ( emple.getAfiliado() == null || emple.getAfiliado().getCuil() == null ) {			
				rta = new DDJJValidarErrorDto();
				rta.setCuil( cuil );
				rta.setCodigo("cuil");
				rta.setDescripcion(valFaltante);			
				rtaLst.add(rta);
			} else {
				cuil = emple.getAfiliado().getCuil();
			}
			
			if ( emple.getAfiliado() == null || ObjectUtils.isEmpty(emple.getAfiliado().getNombre()) ) {			
				rta = new DDJJValidarErrorDto();
				rta.setCuil( cuil );
				rta.setCodigo("nombre");
				rta.setDescripcion(valFaltante);							
				rtaLst.add(rta);
			}
			
			if ( emple.getAfiliado() == null || ObjectUtils.isEmpty(emple.getAfiliado().getApellido()) ) {			
				rta = new DDJJValidarErrorDto();
				rta.setCuil( cuil );
				rta.setCodigo("apellido");
				rta.setDescripcion(valFaltante);							
				rtaLst.add(rta);
			}
		}	
		
		if ( emple.getUomaSocio() == null ) {
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("uomaSocio");
			rta.setDescripcion(valFaltante);			
			rtaLst.add(rta);
		}
		if ( emple.getAmtimaSocio() == null ) {
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("amtimaSocio");
			rta.setDescripcion(valFaltante);			
			rtaLst.add(rta);
		}
		if ( emple.getAmtimaSocio() != null && emple.getUomaSocio() != null && !emple.getUomaSocio() && emple.getAmtimaSocio() ) {
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("amtimaSocio");
			rta.setDescripcion(valUomaAmtima);			
			rtaLst.add(rta);
		}
		
		if (  ObjectUtils.isEmpty(emple.getCamara()) ) {			
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("camara");
			rta.setDescripcion(valFaltante);							
			rtaLst.add(rta);
		}
		
		if (  ObjectUtils.isEmpty(emple.getCategoria()) ) {			
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("categoria");
			rta.setDescripcion(valFaltante);							
			rtaLst.add(rta);
		}			
		
		if (  ObjectUtils.isEmpty(emple.getIngreso()) ) {			
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("fechaIngreso");
			rta.setDescripcion(valFaltante);							
			rtaLst.add(rta);
		} else {
			//ingreso menor IGUAL al periodo.
			if ( !bErrorPeriodo ) {
				if ( !emple.getIngreso().isBefore( periodo.with(TemporalAdjusters.lastDayOfMonth()) ) ) {
					rta = new DDJJValidarErrorDto();
					rta.setCuil( cuil );
					rta.setCodigo("fechaIngreso");
					rta.setDescripcion(valIngresoFuturo);							
					rtaLst.add(rta);
				}
			}
			//valido formato fecha: hay que cambiar emple.getIngreso() a  String !!!!!
			/*
			try {
				LocalDate date = LocalDate.parse(emple.getIngreso());
			}
			*/
		}
		
		
		if ( emple.getEmpresaDomicilio() == null || ObjectUtils.isEmpty(emple.getEmpresaDomicilio().getId()) ) {			
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("empresaDomicilioId");
			rta.setDescripcion(valFaltante);							
			rtaLst.add(rta);
		}
		
		/*
		if (  ObjectUtils.isEmpty(emple.getNoRemunerativo()) ) {			
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("noRemunerativo");
			rta.setDescripcion(valFaltante);							
			rtaLst.add(rta);
		}	else {
			if  ( emple.getNoRemunerativo().compareTo(BigDecimal.ZERO) <0) {
				rta = new DDJJValidarErrorDto();
				rta.setCuil( cuil );
				rta.setCodigo("noRemunerativo");
				rta.setDescripcion(valMenorUno);							
				rtaLst.add(rta);
			}
		}		
		*/
		if (  ObjectUtils.isEmpty(emple.getRemunerativo()) ) {			
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("remunerativo");
			rta.setDescripcion(valFaltante);							
			rtaLst.add(rta);
		} else {
			if  ( emple.getRemunerativo().compareTo(BigDecimal.ZERO) <0) {
				rta = new DDJJValidarErrorDto();
				rta.setCuil( cuil );
				rta.setCodigo("remunerativo");
				rta.setDescripcion(valMenorUno);							
				rtaLst.add(rta);
			}
		}
		
		if (  emple.getUomaSocio() == null ) {			
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("uomaSocio");
			rta.setDescripcion(valFaltante);							
			rtaLst.add(rta);
		}

		if (  emple.getAmtimaSocio() == null ) {			
			rta = new DDJJValidarErrorDto();
			rta.setCuil( cuil );
			rta.setCodigo("amtimaSocio");
			rta.setDescripcion(valFaltante);							
			rtaLst.add(rta);
		}
		
		if (rtaLst.size() > 0 )
			return Optional.of(rtaLst);
		return Optional.empty();
	}
	
	private List<DDJJValidarErrorDto> append(List<DDJJValidarErrorDto> destino, List<DDJJValidarErrorDto> lst) {
		for(DDJJValidarErrorDto reg: lst) {
			destino.add(reg);
		}
		return destino;
	}
}
