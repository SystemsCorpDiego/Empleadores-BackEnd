package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.comun.strings.StringHelper;
import ar.ospim.empleadores.exception.CommonEnumException;
import ar.ospim.empleadores.nuevo.dominio.MailTipoConfiguracionBO;
import ar.ospim.empleadores.nuevo.infra.out.store.MailTipoConfiguracionStorage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailTipoConfiguracionServiceImpl implements MailTipoConfiguracionService {

	private final MessageSource messageSource;
	private final MailTipoConfiguracionStorage storage;

	@Override
	public List<MailTipoConfiguracionBO> consultar(Integer mailId) {
		List<MailTipoConfiguracionBO> lst = storage.findAll();
		if (mailId != null) {
			lst = lst.stream()
					.filter(reg -> Objects.equals(reg.getMailId(), mailId))
					.collect(Collectors.toList());
		}
		return lst;
	}


	@Override
	public Optional<MailTipoConfiguracionBO> consultarVigente(Integer mailId) {
		return storage.findVigente(mailId);
	}
	
	@Override
	public MailTipoConfiguracionBO crear(MailTipoConfiguracionBO reg) {
		validar(reg);
		return storage.save(reg);
	}

	@Override
	public MailTipoConfiguracionBO actualizar(Integer id, MailTipoConfiguracionBO reg) {
		reg.setId(id);
		validar(reg);
		return storage.save(reg);
	}

	
	private void validar(MailTipoConfiguracionBO reg) {
		String errorMsg = null;
		//TODO: falta definir valiables del "CuerpoMail" y validarlas.-
		
		if (reg == null || StringHelper.isNullOrWhiteSpace(reg.getCuerpoMail()) || reg.getDiaProceso() == null ) {
			errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg,  "Dia de Proceso y Cuerpo del Mail" ));
		}					
		
		if ( reg.getMailId() == null ) {
			errorMsg = messageSource.getMessage(CommonEnumException.ATRIBUTO_OBLIGADO.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.ATRIBUTO_OBLIGADO.name(), String.format(errorMsg,  "Tipo de Mail" ));
		}					
		
		if ( reg.getDiaProceso() < 1 || reg.getDiaProceso() > 31 ) {
			errorMsg = messageSource.getMessage(CommonEnumException.DIA_DEL_MES_ERROR.getMsgKey(), null, new Locale("es"));
			throw new BusinessException(CommonEnumException.REGISTRO_DUPLICADO.name(), errorMsg );
		}
	}
}
