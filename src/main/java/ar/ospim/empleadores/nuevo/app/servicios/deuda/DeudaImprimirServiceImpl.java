package ar.ospim.empleadores.nuevo.app.servicios.deuda;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.dates.DateTimeProvider;
import ar.ospim.empleadores.nuevo.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.deuda.dto.IGestionDeudaDDJJDto;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaStorage;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.ActaMolinerosI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeudaImprimirServiceImpl implements DeudaImprimirService {

	private JasperReport deudaGestionJasper;
	private JasperReport deudaGestionDDJJSubJasper;
	private JasperReport deudaGestionActasSubJasper;

	@Autowired
	private final DeudaService deudaService;

	@Autowired
	private final EmpresaStorage empresaStorage;

	@Autowired
	private DateTimeProvider dtProvider;

	@PostConstruct
	private void init() {
		log.debug("init(): ARRANQUE...");
		try {
			InputStream fleJasperDDJJSub = getClass().getClassLoader().getResourceAsStream("reportes/deudaGestionDDJJSub.jrxml");
			deudaGestionDDJJSubJasper = JasperCompileManager.compileReport(fleJasperDDJJSub);

			InputStream fleJasperActasSub = getClass().getClassLoader().getResourceAsStream("reportes/deudaGestionActasSub.jrxml");
			deudaGestionActasSubJasper = JasperCompileManager.compileReport(fleJasperActasSub);

			InputStream fleJasper = getClass().getClassLoader().getResourceAsStream("reportes/deudaGestion.jrxml");
			deudaGestionJasper = JasperCompileManager.compileReport(fleJasper);

		} catch (Exception e) {
			log.error("init() - ERROR : " + e.getMessage() + " - " + e.getCause() + " - " + e.getStackTrace());
		}
	}

	@Override
	public byte[] run(Integer empresaId, String entidad) throws JRException, SQLException {
		byte[] pdfBytes = null;

		try {
			List<IGestionDeudaDDJJDto> lstDDJJ = deudaService.getDDJJDto(empresaId, entidad);
			List<ActaMolinerosI> lstActas = deudaService.getMolinerosActas2(empresaId, entidad);

			HashMap<String, Object> params = getParametros(empresaId, entidad);
			params.put("SUBREPORT_DDJJ", deudaGestionDDJJSubJasper);
			params.put("SUBREPORT_DDJJ_DATASOURCE", new JRBeanCollectionDataSource(lstDDJJ));
			params.put("SUBREPORT_ACTAS", deudaGestionActasSubJasper);
			params.put("SUBREPORT_ACTAS_DATASOURCE", new JRBeanCollectionDataSource(lstActas));

			JasperPrint jasperPrint = JasperFillManager.fillReport(deudaGestionJasper, params, new JREmptyDataSource());
			pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}

		log.debug("FIN ");
		return pdfBytes;
	}

	private HashMap<String, Object> getParametros(Integer empresaId, String entidad) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		Optional<EmpresaBO> empresa = empresaStorage.findById(empresaId);
		if (empresa.isPresent()) {
			params.put("empresaRazonSocial", empresa.get().getRazonSocial());
			params.put("empresaCuit", empresa.get().getCuit());
		}

		params.put("entidad", entidad);
		params.put("fechaImpresion", dtProvider.getDateToString(dtProvider.nowDate()));

		return params;
	}

}
