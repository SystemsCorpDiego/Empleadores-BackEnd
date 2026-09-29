package ar.ospim.empleadores.nuevo.app.servicios.mail;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.ospim.empleadores.nuevo.dominio.MailEnum;
import ar.ospim.empleadores.nuevo.dominio.MailTipoBO;

@Service
public class MailTipoConsultaServiceImpl implements MailTipoConsultaService {

	@Override
	public List<MailTipoBO> consultar() {
		List<MailTipoBO> lst = new ArrayList<MailTipoBO>();
		MailTipoBO reg = null;
		for (MailEnum mailEnum : MailEnum.values()) {
			reg = new MailTipoBO();
			reg.setId(mailEnum.getId());
			reg.setDescripcion(mailEnum.getDescripcion());
			lst.add(reg);
		}
		return lst;
	}

}
