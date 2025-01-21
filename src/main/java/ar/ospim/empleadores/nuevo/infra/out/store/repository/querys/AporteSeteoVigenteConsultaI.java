package ar.ospim.empleadores.nuevo.infra.out.store.repository.querys;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AporteSeteoVigenteConsultaI {
	Integer getId();

    String getAporte();

    LocalDate getDesde();
    LocalDate getHasta();

    String  getEntidad();
    String  getSocio();
    String  getCalculoTipo();
    BigDecimal  getCalculoValor();
    String  getCalculoBase();
    String  getCamara();
    String  getCamaraCategoria();
    Integer  getCamaraAntiguedad();
}
