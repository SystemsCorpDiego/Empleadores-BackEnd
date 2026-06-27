package ar.ospim.empleadores.nuevo.app.servicios.empresa.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.cui.CuilUtils;
import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaRestringidaMailBO;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaExceptionEnum;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaRestringidaMailService;
import ar.ospim.empleadores.nuevo.app.servicios.empresa.EmpresaService;
import ar.ospim.empleadores.nuevo.infra.out.store.EmpresaRestringidaMailStorage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpresaRestringidaMailServiceImpl implements EmpresaRestringidaMailService {

	private final MessageSource messageSource;
	private final EmpresaRestringidaMailStorage storage;
	private final EmpresaService empresaService;

	@Override
	public EmpresaRestringidaMailBO crear(EmpresaRestringidaMailBO reg) {
		if (!CuilUtils.validar(reg.getCuit())) {
			String errorMsg = messageSource.getMessage(EmpresaExceptionEnum.CUIT_FORMAT.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(EmpresaExceptionEnum.CUIT_FORMAT.name(), errorMsg);
		}
		return storage.save(reg);
	}

	@Override
	public void borrar(Integer id) {
		if (id == null) {
			String errorMsg = messageSource.getMessage(CommonEnumException.ID_NO_INFORMADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ID_NO_INFORMADO.name(), errorMsg);
		}
		storage.deleteById(id);
	}

	@Override
	public List<EmpresaRestringidaMailBO> consultar() {
		List<EmpresaRestringidaMailBO> lst = storage.findAll();
		for (EmpresaRestringidaMailBO reg : lst) {
			reg.setRazonSocial(getEmpresaDescrip(reg.getCuit()));
		}
		return lst;
	}

	@Override
	public Boolean esRestringido(String cuit) {
		Optional<EmpresaRestringidaMailBO> cons = storage.findByCuit(cuit);
		return cons.isPresent();
	}

	private String getEmpresaDescrip(String cuit) {
		String razonSocial = null;
		try {
			if (cuit != null) {
				EmpresaBO empre = empresaService.getEmpresa(cuit);
				razonSocial = empre.getRazonSocial();
			}
		} catch (Exception e) {
			System.out.print(e);
		}
		return razonSocial;
	}
}
