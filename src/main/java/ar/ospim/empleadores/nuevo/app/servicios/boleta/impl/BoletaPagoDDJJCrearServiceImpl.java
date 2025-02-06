package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoAporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularAjustesService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularInteresService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularVtoService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJCrearService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoDDJJCrearValidacionesService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoElectronicaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoEnumException;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJConsultarService;
import ar.ospim.empleadores.nuevo.app.servicios.ddjj.DDJJEstadoBoletaGeneradaService;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.out.store.BoletaPagoStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.mapper.BoletaPagoMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoletaPagoDDJJCrearServiceImpl implements BoletaPagoDDJJCrearService {
	
	private final MessageSource messageSource;
	private final BoletaPagoMapper mapper;
	
	private final DDJJConsultarService ddjjConsultarService; 
	private final FormaPagoService formaPagoService;
	private final DDJJEstadoBoletaGeneradaService estadoBoletaGeneradaService;
	//private final BoletaPagoActaService boletaPagoService;
	private final BoletaPagoStorage boletaPagoStorage;
	private final BoletaPagoCalcularInteresService calcularInteresService;
	private final BoletaPagoCalcularAjustesService calcularAjustesService;
	private final BoletaPagoCalcularVtoService boletaPagoCalcularVtoService;
	private final BoletaPagoDDJJCrearValidacionesService bpDDJJCrearValidacionesService;
	private final BoletaPagoElectronicaService bepService;
	
	public void run(Integer ddjjId, List<BoletaPagoBO> boletasParam) {		
		DDJJBO ddjj = ddjjConsultarService.consultar(ddjjId);
		
		bpDDJJCrearValidacionesService.run(ddjj.getEmpresa().getId(), ddjjId);
		
		List<BoletaPagoBO> boletasDDJJ =  crearBoletasDDJJ(ddjj);		
		boletasDDJJ = actualizarInfoPago(boletasDDJJ, boletasParam);
		boletasDDJJ = calcularVtoAFIP(boletasDDJJ, ddjj.getEmpresa().getCuit(), ddjj.getPeriodo());
		boletasDDJJ = calcularIntereses(boletasDDJJ,  ddjj.getPeriodo());
		boletasDDJJ = calcularAjustes(boletasDDJJ, ddjj.getPeriodo());
		
		boletasDDJJ = guardar(boletasDDJJ);
		estadoBoletaGeneradaService.run(ddjjId);
		boletasDDJJ = bepService.runAndSave(boletasDDJJ);
		
	}
	
	private List<BoletaPagoBO> crearBoletasDDJJ(DDJJBO ddjj) {
		List<BoletaPagoBO> boletasDDJJ = new ArrayList<BoletaPagoBO>(); 
		BoletaPagoBO boletaPagoAux = null;
		
		Integer indexAporte = -1;
		for (DDJJEmpleadoBO empleado:  ddjj.getEmpleados() ) {
			for (DDJJEmpleadoAporteBO empleAporte: empleado.getAportes() ) {
				indexAporte = getAporteIndex(boletasDDJJ,  empleAporte.getAporte().getCodigo() );
				if ( indexAporte > -1) {
					boletasDDJJ.get(indexAporte).setImporte(
							boletasDDJJ.get(indexAporte).getImporte().add(empleAporte.getImporte()) );
				} else {
					//Crear BoletaPagoBO:
					boletaPagoAux = mapper.map(ddjj, empleAporte);
					//Agregar la Boleta a la Lista 
					boletasDDJJ.add(boletaPagoAux);
				}
			}
		}
		return boletasDDJJ;
	}

	private Integer getAporteIndex( List<BoletaPagoBO> lstDDJJBoletasPago, String aporte  ) {
		if (lstDDJJBoletasPago == null || lstDDJJBoletasPago.size() == 0 ) {
			return -1;
		}
		
		for (int i = 0; lstDDJJBoletasPago.size()>i; i++ ) {
			if ( lstDDJJBoletasPago.get(i).getAporte().getCodigo().equals(aporte)  ) {
				return i;
			}
		}
		return -1;
	}

	private List<BoletaPagoBO> calcularVtoAFIP(List<BoletaPagoBO> boletasDDJJ, String cuit, LocalDate periodo ) {
		if ( boletasDDJJ != null && boletasDDJJ.size()>0 ) {
			LocalDate vto = null;
			for(BoletaPagoBO reg: boletasDDJJ) {
				vto = boletaPagoCalcularVtoService.run(reg.getAporte().getCodigo(), cuit, periodo);
				reg.setVencimiento(vto);
			}
		}
		return boletasDDJJ;
	}
	
	private List<BoletaPagoBO> calcularIntereses(List<BoletaPagoBO> boletasDDJJ, LocalDate periodo ) {
		//calcular intereses por mora
		BigDecimal interesMora = null;
		for(BoletaPagoBO boleta : boletasDDJJ) {
			//validar: todas las boletas deben tener InfoPago
			if ( boleta.getFormaDePago() == null || boleta.getIntencionDePago() == null ) {
				String errorMsg = messageSource.getMessage(BoletaPagoEnumException.APORTE_INFO_PAGO_INEXISTENTE.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(BoletaPagoEnumException.APORTE_INFO_PAGO_INEXISTENTE.name(),
						String.format(errorMsg, boleta.getAporte().getCodigo())	);				
			}
			
			interesMora = calcularInteresService.run( boleta.getVencimiento(), boleta.getIntencionDePago(), boleta.getImporte());
			boleta.setInteres( interesMora );			 
		}
		return boletasDDJJ;
	}
	
	private List<BoletaPagoBO> calcularAjustes(List<BoletaPagoBO> boletasDDJJ, LocalDate periodo) {
		for(BoletaPagoBO regBoleta: boletasDDJJ) {
			regBoleta.setAjustes( calcularAjustesService.run(regBoleta, periodo) );			
		}
		
		return boletasDDJJ;
	}
	
	private List<BoletaPagoBO>  guardar(List<BoletaPagoBO> boletas) {
		BoletaPagoBO boletaNew = null;
		for(BoletaPagoBO boleta: boletas) {
			boletaNew = boletaPagoStorage.guardar(boleta);
			boleta.setId( boletaNew.getId() );
			boleta.setSecuencia( boletaNew.getSecuencia() );
		}
		return boletas;
	}

	private List<BoletaPagoBO> actualizarInfoPago(List<BoletaPagoBO> lstBoletasDDJJ, List<BoletaPagoBO> lstBoletasParam) {
		//Actualizar Boletas de la DDJJ con: intencionDePago y formaDePago; //Ventanilla, Link, PagoMisCuentas. 
		Integer indexAporte = -1;
		for(BoletaPagoBO boleta: lstBoletasDDJJ) {
			indexAporte = getAporteIndex(lstBoletasParam,  boleta.getAporte().getCodigo() );
			if ( indexAporte >-1) {
				//TODO: ver que no este ya cargado ..(mandaron 2 veces el concepto pago !!!!)
				if ( ! formaPagoService.existe( lstBoletasParam.get(indexAporte).getFormaDePago()  )  ) {
					String errorMsg = messageSource.getMessage(BoletaPagoEnumException.FORMA_PAGO_INEXISTENTE.getMsgKey(), null, new Locale("es"));
					throw new BusinessException(BoletaPagoEnumException.FORMA_PAGO_INEXISTENTE.name(), errorMsg );					
				}
				boleta.setIntencionDePago(lstBoletasParam.get(indexAporte).getIntencionDePago() );
				boleta.setFormaDePago( lstBoletasParam.get(indexAporte).getFormaDePago() );
			} else {
				String errorMsg = messageSource.getMessage(BoletaPagoEnumException.APORTE_DDJJ_INEXISTENTE.getMsgKey(), null, new Locale("es"));
				throw new BusinessException(BoletaPagoEnumException.APORTE_DDJJ_INEXISTENTE.name(),
						String.format(errorMsg, boleta.getAporte().getCodigo())	);
			}
		}
		return lstBoletasDDJJ;
	}
	
}
