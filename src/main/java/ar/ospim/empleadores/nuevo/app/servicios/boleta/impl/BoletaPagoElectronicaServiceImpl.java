package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.WebServiceException;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoConsultaService;
import ar.ospim.empleadores.nuevo.app.servicios.boleta.BoletaPagoElectronicaService;
import ar.ospim.empleadores.nuevo.app.servicios.formapago.FormaPagoService;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.pagomiscuentas.PagoMisCuentasService;
import ar.ospim.empleadores.nuevo.infra.out.getwaypago.webservice.redlink.RedLinkService;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.BoletaPagoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BoletaPago;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoletaPagoElectronicaServiceImpl implements BoletaPagoElectronicaService {
	
    @Value("${getwayPago.pagoMisCuentas.habilitado}")
    private Boolean BANELCO_HABI;
    @Value("${getwayPago.redlink.habilitado}")
    private Boolean REDLINK_HABI;
    
	private final FormaPagoService formaPagoService; 
	private final BoletaPagoRepository repository;
	private final BoletaPagoConsultaService consultaService;
	private final PagoMisCuentasService pagoMisCuentasService;
	private final RedLinkService redLinkService; 
	
	
	public String runAndSave(Integer boletaPagoId) {
		BoletaPagoBO boleta = consultaService.find(boletaPagoId);
		String  bep = run(boleta);
		boleta.setBep(bep);
		registrarBep(boleta);
		return bep;
	}
	
	public String run(BoletaPagoBO bp) {
		String bep = null;
		if ( formaPagoService.generaVEP(bp.getFormaDePago()) ) {
			if ( bp.getFormaDePago().equals("PMCUENTAS")  ) {
				if ( BANELCO_HABI ) {
					try {
						bep = pagoMisCuentasService.generarBep(bp);
					} catch (WebServiceException wsE) {
						bep = "ERROR: " + wsE.getCodigo() +"-"+ wsE.getDescripcion();
					} catch (Exception e) {
						bep = "ERROR-2: " + e.toString();
					}
				} else {
					bep = generarBepDemo(bp.getFormaDePago());
				}
			} else {
				if ( REDLINK_HABI ) {
					bep = redLinkService.generarBep(bp);
				} else {
					bep = generarBepDemo(bp.getFormaDePago());
				}
			}
		}
		if (bep != null && bep.length() > 254) {
			bep = bep.substring(0,254);
		}

		return bep;
	}
	
	public List<BoletaPagoBO> runAndSave(List<BoletaPagoBO> lstBp) {
		for(BoletaPagoBO reg: lstBp) {
			reg.setBep( run(reg) );
			registrarBep(reg);
		}
		return lstBp;
	}

	private String generarBepDemo(String formaPago) {
		double SIXDigits = 100000 + Math.random() * 90000;
		double FOURDigits = 1000 + Math.random() * 9000;
		
		return "BEP-"+formaPago+"-"+(int)FOURDigits+"-DEMO-"+(int)SIXDigits;
	}

	private void registrarBep(BoletaPagoBO bp) {
		Optional<BoletaPago> bpCons = repository.findById(bp.getId());
		if ( bpCons.isPresent() ) {
			BoletaPago boletaPago = bpCons.get();
			boletaPago.setBep(bp.getBep());
			repository.save(boletaPago);
		}
	}
	

}
