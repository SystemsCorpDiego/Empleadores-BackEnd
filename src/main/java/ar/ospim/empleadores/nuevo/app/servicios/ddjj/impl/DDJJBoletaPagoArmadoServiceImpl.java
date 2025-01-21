package ar.ospim.empleadores.nuevo.app.servicios.ddjj.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoAporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.aporte.AporteService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularAjustesService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularInteresService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularVtoService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoConsultaService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJBoletaPagoArmadoService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJUtils;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.BoletaPagoDtoMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAfiliadoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAjusteDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDto;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class DDJJBoletaPagoArmadoServiceImpl implements DDJJBoletaPagoArmadoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final AporteService aporteService;
	private final DDJJConsultarService ddjjConsultarService;
	private final BoletaPagoConsultaService boletaPagoConsultaService;
	private final BoletaPagoCalcularVtoService boletaPagoCalcularVtoService;
	private final BoletaPagoCalcularInteresService boletaPagoCalcularInteresService; 
	private final BoletaPagoCalcularAjustesService boletaPagoCalcularAjustesService;
	private final BoletaPagoDtoMapper mapper;
	
	public DDJJBoletaArmadoDto run(Integer ddjjId, String aporteCodigo, LocalDate intencionDePago) {
		
		DDJJBO ddjj = ddjjConsultarService.consultar(ddjjId);
		logger.debug( ddjj.toString() );
		
		DDJJBoletaArmadoDto reg = buildConsulta( ddjj, aporteCodigo, intencionDePago );				
		return reg;
	}
	
	private DDJJBoletaArmadoDto buildConsulta( DDJJBO dj, String aporteCodigo, LocalDate intencionDePago) {
		DDJJBoletaArmadoDto cons = new DDJJBoletaArmadoDto();
		LocalDate boletaVto = null;
		cons.setDeclaracionJuradaId(dj.getId());
		cons.setPeriodo(dj.getPeriodo());
		cons.setTipoDdjj( DDJJUtils.getTipoDDJJ( dj.getSecuencia() ) );
		cons.setDdjjNro(dj.getSecuencia());
		cons.setBoletas( new ArrayList<DDJJBoletaArmadoDetalleDto>() );
		
		
		
		DDJJBoletaArmadoDetalleDto boleta = null;
		DDJJBoletaArmadoDetalleAfiliadoDto afiliado = null;
		DDJJBoletaArmadoDetalleAfiliadoDto afiliadoNew = null;
		Integer pos = -1;
		for ( DDJJEmpleadoBO emple: dj.getEmpleados() ) {
			afiliado = new DDJJBoletaArmadoDetalleAfiliadoDto(); //CAPITAL
			afiliado.setApellido( emple.getAfiliado().getApellido() );
			afiliado.setNombre( emple.getAfiliado().getNombre() );
			afiliado.setCuil( emple.getAfiliado().getCuil()  );
			afiliado.setRemunerativo( emple.getRemunerativo() );
			for (DDJJEmpleadoAporteBO empleAporte: emple.getAportes()) {
				if ( aporteCodigo == null || aporteCodigo.equals( empleAporte.getAporte().getCodigo()) ) {
					
					boleta = new DDJJBoletaArmadoDetalleDto(empleAporte.getAporte().getCodigo());
					boleta.setAjustes( new ArrayList<DDJJBoletaArmadoDetalleAjusteDto>() );
					boleta.setAfiliados(new ArrayList<DDJJBoletaArmadoDetalleAfiliadoDto>() );
					pos = cons.getBoletas().indexOf(boleta);
					if ( pos == -1) {
						boleta.setDescripcion(empleAporte.getAporte().getDescripcion());
						boletaVto = boletaPagoCalcularVtoService.run(empleAporte.getAporte().getCodigo(), dj.getEmpresa().getCuit(), dj.getPeriodo());
						boleta.setVencimiento( boletaVto ); 
						boleta.setPeriodo(dj.getPeriodo());
						boleta.setDeclaracionJuradaId(dj.getId());
						boleta.setTipoDdjj(  DDJJUtils.getTipoDDJJ(dj.getSecuencia()) );

						boleta.setInteres( BigDecimal.ZERO ); //Hasta no saber fecha Pago ...						
						boleta.setIntencionDePago( intencionDePago ); //DATO QUE CARGA EL USUARIO
						boleta.setFormaDePago(null); //DATO QUE CARGA EL USUARIO
						boleta.setNumeroBoleta( null ); //DATO QUE GENERA EL SISTEMA AL GRABAR
						
						Optional<BoletaPagoBO> bp = boletaPagoConsultaService.findByDdjjIdAndAporte(dj.getId(), empleAporte.getAporte().getCodigo());
						if ( bp.isPresent() ) {
							boleta.setId( bp.get().getId()  );
							boleta.setFormaDePago( bp.get().getFormaDePago()  );
							boleta.setNumeroBoleta( bp.get().getSecuencia() );
						}
						cons.getBoletas().add(boleta); 
					} else {
						boleta = cons.getBoletas().get(pos);
					}
					afiliadoNew = afiliado.cloneDto();
					afiliadoNew.setCapital( empleAporte.getImporte() );
					boleta.getAfiliados().add(afiliadoNew);
				}
			}		
		}
		
		//Calcular Interese de la Boleta
		if ( intencionDePago != null ) {
			for (DDJJBoletaArmadoDetalleDto blt : cons.getBoletas() ) {
				blt.setInteres( boletaPagoCalcularInteresService.run( blt.getCodigo(), dj.getEmpresa().getCuit(), dj.getPeriodo(), blt.getIntencionDePago(), blt.getTotal_acumulado() ) );
			}
		}

		//Calcular Ajustes de la Boleta
		for (DDJJBoletaArmadoDetalleDto blt : cons.getBoletas() ) {
			blt.setAjustes( boletaPagoCalcularAjustesService.run(blt, dj.getEmpresa().getId() ) );
		}
		
		//Reordenar Boletas segun el Aporte.orden
		cons.setBoletas( orderPorAporte(cons.getBoletas() ) ); 		
		
		return cons;
	}
	
	private List<DDJJBoletaArmadoDetalleDto>  orderPorAporte(List<DDJJBoletaArmadoDetalleDto> boletas ) {
		List<DDJJBoletaArmadoDetalleDto> boletasOrdenadas = new ArrayList<DDJJBoletaArmadoDetalleDto>();
		List<AporteBO> aportes = aporteService.consultarOrderByOrdenAsc();
		for(AporteBO aporte : aportes ) {
			//busco aporte en boletas generadas
			for( DDJJBoletaArmadoDetalleDto boleta: boletas) {
				if ( boleta.getCodigo().equals(aporte.getCodigo())) {
					boletasOrdenadas.add(boleta);
				}
			}
		}
		return boletasOrdenadas;
	}
	 
}
