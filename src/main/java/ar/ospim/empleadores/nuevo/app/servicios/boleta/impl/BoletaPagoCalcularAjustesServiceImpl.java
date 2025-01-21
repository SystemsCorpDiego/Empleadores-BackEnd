package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoAjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.ajuste.AjusteService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoCalcularAjustesService;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.BoletaPagoDtoMapper;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAjusteDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BoletaPagoCalcularAjustesServiceImpl implements BoletaPagoCalcularAjustesService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final AjusteService service;
	private final BoletaPagoDtoMapper mapper;
	
	@Transactional
	@Override
	public List<BoletaPagoAjusteBO> run(BoletaPagoBO boleta, LocalDate periodoDDJJ) {
		List<BoletaPagoAjusteBO> ajustesAplicar = new ArrayList<BoletaPagoAjusteBO>();
		BoletaPagoAjusteBO ajusteBP = null;
		BigDecimal importeBoleta = boleta.getImporte();
		if ( boleta.getInteres() != null )
			importeBoleta = importeBoleta.add(boleta.getInteres());
		BigDecimal importeUsado = null;
		BigDecimal importeAjuste = null;		
		
		logger.error( "generarAjusteAutomaticoIPF - Aporte: " + boleta.getAporte().getCodigo() );
		service.generarAjusteAutomaticoIPF(boleta.getAporte().getCodigo(), boleta.getEmpresa().getId());
		
		//1) consulta Ajustes con saldo para el CUIT+Aporte+Periodo
		//1.1) Modificacion: los Ajustes se calculan para: fechaVigencia>=sysdate
		//List<AjusteBO>  ajustes = service.consultarAportesVigentes(boleta.getEmpresa().getId(), boleta.getAporte().getCodigo(), periodoDDJJ);
		List<AjusteBO>  ajustes = service.consultarAportesVigentes(boleta.getEmpresa().getId(), boleta.getAporte().getCodigo(), LocalDate.now());
		for (AjusteBO ajuste: ajustes) {
			if ( ajuste.getImporte().compareTo(BigDecimal.ZERO)>-1 ) {
				//Ajuste positivo: paga deuda periodo Anterior.-
				ajusteBP = new BoletaPagoAjusteBO(boleta, ajuste, ajuste.getImporte());
				ajustesAplicar.add(ajusteBP);
				importeBoleta =  importeBoleta.add( ajuste.getImporte() );				
			} else {
				if ( importeBoleta.compareTo(BigDecimal.ZERO) != 0 ) {
					importeUsado = service.getImporteUsado(ajuste.getId());
					importeAjuste = ajuste.getImporte().add(importeUsado.negate()); //Controlar signos +-
					
					//Ajuste negativo: OSPIM devuelve monto por saldo acreedor del empleador.-
					if ( importeBoleta.add(importeAjuste).compareTo(BigDecimal.ZERO) > -1 ) {
						ajusteBP = new BoletaPagoAjusteBO(boleta, ajuste, importeAjuste);						
						ajustesAplicar.add(ajusteBP);						
						importeBoleta = importeBoleta.add(importeAjuste);				
					} else {
						//Saldo Acreedor mayor a lo que paga: genero boleta en CERO y no controlo mas nada...
						ajusteBP = new BoletaPagoAjusteBO(boleta, ajuste, importeBoleta.negate() );	
						ajustesAplicar.add(ajusteBP);
						importeBoleta = BigDecimal.ZERO;
					}
				}
			}			 
		}	
		return ajustesAplicar;
	}
	
	@Override
	public List<DDJJBoletaArmadoDetalleAjusteDto> run(DDJJBoletaArmadoDetalleDto boleta, Integer empresaId) {
		
		//convertir DDJJBoletaArmadoDetalleDto => BoletaPagoBO
		BoletaPagoBO boletaBo = mapper.mapDto(boleta, empresaId);
		
		//llamar a calculo
		List<BoletaPagoAjusteBO> ajustesAplicar = run(boletaBo, boleta.getPeriodo() );
		//boletaBo.setAjustes(ajustesAplicar);
		
		//convertir BoletaPagoBO => DDJJBoletaArmadoDetalleDto
		List<DDJJBoletaArmadoDetalleAjusteDto> lst = mapper.mapBPA(ajustesAplicar);
		
		//casteo descripciones de Ajustes(que son periodos)
		for (DDJJBoletaArmadoDetalleAjusteDto reg: lst ) {
			try {
				reg.setDescripcion( "Período " +reg.getDescripcion().split("-")[1] + "/" +reg.getDescripcion().split("-")[0] );
			} catch (Exception e) {}
		}
		
		return lst;
	}

}
