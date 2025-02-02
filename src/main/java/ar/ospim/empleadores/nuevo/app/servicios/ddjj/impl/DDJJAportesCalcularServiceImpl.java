package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoAporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EscalaSalarialBO;
import ar.ospim.empleadores.nuevo.app.servicios.aporte.AporteSeteoService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJAportesCalcularService;
import ar.ospim.empleadores.nuevo.app.servicios.escalasalarial.EscalaSalarialService;
import ar.ospim.empleadores.nuevo.infra.out.store.AporteStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.EntidadEnum;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteSeteo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DDJJAportesCalcularServiceImpl implements DDJJAportesCalcularService {
	private final AporteStorage aporteStorage; 
	private final AporteSeteoService aporteSeteoService;
	private final EscalaSalarialService escalaSalarialService;
	
	@Override
	public void run(DDJJBO ddjj) {
		for ( DDJJEmpleadoBO empleado : ddjj.getEmpleados() ) {
			run(ddjj.getPeriodo(), empleado);
		}
	}

	private Boolean aplicaAporte(AporteSeteo aporteSeteo, DDJJEmpleadoBO empleado ) {
		if ( aporteSeteo.getEntidad() == null || //El aporte se aplica a cualquiera
			 aporteSeteo.getSocio() == null || //El aporte aplica a Socios y no socios
		    (aporteSeteo.getEntidad().equals(EntidadEnum.UOMA.getCodigo())  && empleado.getUomaSocio() && aporteSeteo.getSocio()  ) ||
			(aporteSeteo.getEntidad().equals(EntidadEnum.UOMA.getCodigo())  && !empleado.getUomaSocio() && !aporteSeteo.getSocio() ) ||		
		    (aporteSeteo.getEntidad().equals(EntidadEnum.AMTIMA.getCodigo())  && empleado.getAmtimaSocio() && aporteSeteo.getSocio()  ) ||
			(aporteSeteo.getEntidad().equals(EntidadEnum.AMTIMA.getCodigo())  && !empleado.getAmtimaSocio() && !aporteSeteo.getSocio() ) 
			) {
				return true;
		}		
		return false;
	}
	
	private BigDecimal calcular(AporteSeteo aporteSeteo, LocalDate periodo, DDJJEmpleadoBO empleado) {
		
		BigDecimal importe = BigDecimal.ZERO;
		
		if ( empleado.getRemunerativo() != null && empleado.getRemunerativo().compareTo(BigDecimal.ZERO)>0 ) {			
			if ( aporteSeteo.getCalculoTipo().equals("EN")  ) {
				importe =  aporteSeteo.getCalculoValor();
			}
			
			if ( aporteSeteo.getCalculoTipo().equals("PO") ) {
				if ( aporteSeteo.getCalculoBase().equals("RE")  ) {
					if ( empleado.getRemunerativo() != null ) {
						importe = empleado.getRemunerativo();
					}
				}
				if  ( aporteSeteo.getCalculoBase().equals("PJ")  || //Paritaria por Jornal
						aporteSeteo.getCalculoBase().equals("PS")    //Paritaria por Salario
						) { 
					String camara = aporteSeteo.getCamara();
					String categoria = aporteSeteo.getCamaraCategoria();
					if ( "DJ".equals(camara) ) {
						camara = empleado.getCamara();
					}
					if ( "DJ".equals(categoria) ) {
						categoria = empleado.getCategoria();
					}
					if ( "MI".equals(categoria) ) {
						categoria = getCategoriaMinimaVigente(periodo, aporteSeteo.getCalculoBase(),  camara, aporteSeteo.getCamaraAntiguedad() );
					}
					List<EscalaSalarialBO> cons = escalaSalarialService.get(aporteSeteo.getCalculoBase(), camara, categoria, aporteSeteo.getCamaraAntiguedad(), periodo);
					if ( cons != null && cons.size()>0 ) {
						importe = cons.get(0).getBasico();
					}
				}
				importe = importe.multiply(aporteSeteo.getCalculoValor()).divide(BigDecimal.valueOf(100L));
			}	 
		}

		return importe;
	}
	
	private String getCategoriaMinimaVigente(LocalDate periodo, String escalaSalarialTipo, String camara, Integer antiguedad) {
		//(String tipo, String camara, Integer antiguedad, Integer basico, LocalDate vigencia);
		String rtaCategoria = "E";
		
		rtaCategoria = escalaSalarialService.getMenorCategoriaVigente(escalaSalarialTipo, camara, antiguedad, periodo);
		 
		return rtaCategoria;
	}
	
	@Override
	public void run(LocalDate periodo, DDJJEmpleadoBO empleado) {
		log.debug("DDJJAportesCalcularService.run() - periodo: {} - empleado: {}", periodo, empleado);
		AporteBO aporte = null;
		DDJJEmpleadoAporteBO empleadoAporte = null;
		
		if ( empleado.getUomaSocio() == null &&  empleado.getUomaSocio() == null ) {			
			empleado.setAportes( null );			
		}
		
		List<AporteSeteo> aporteSeteos = aporteSeteoService.findVigentes(periodo);
		empleado.setAportes(new ArrayList<DDJJEmpleadoAporteBO>() );
		for (AporteSeteo aporteSeteo : aporteSeteos) {
			if ( aplicaAporte(aporteSeteo, empleado) ) {
				empleadoAporte = new DDJJEmpleadoAporteBO();
				aporte = aporteStorage.findByCodigo( aporteSeteo.getAporte() );
				empleadoAporte.setAporte(aporte);
				empleadoAporte.setImporte( calcular(aporteSeteo, periodo, empleado) );  //TODO: falta CALCULARLO
				empleado.getAportes().add(empleadoAporte);
			}
		}
	}
 }
