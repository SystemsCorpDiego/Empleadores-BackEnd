package ar.ospim.empleadores.nuevo.app.servicios.boleta.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import ar.ospim.empleadores.comun.exception.BusinessException;
import ar.ospim.empleadores.nuevo.app.servicios.feriado.FeriadoService;
import ar.ospim.empleadores.nuevo.dominio.FeriadoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AfipVencimientoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.AporteVencimientoRepository;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AfipVencimiento;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteVencimiento;

@ExtendWith(MockitoExtension.class)
class BoletaPagoCalcularVtoServiceImplTest {

	@Mock
	private MessageSource messageSource;

	@Mock
	private AfipVencimientoRepository vtoRepo;

	@Mock
	private AporteVencimientoRepository vtoPorAporteRepo;

	@Mock
	private FeriadoService feriadoService;

	@InjectMocks
	private BoletaPagoCalcularVtoServiceImpl service;

	private static final String CUIT = "30718866711";
	private static final String APORTE = "ART46";

	
	@Test
	void usaElSeteoAfipPorEncimaDelSeteo() {
		LocalDate periodo = LocalDate.of(2026, 10, 1);

		FeriadoBO feriado0 = new FeriadoBO();
		feriado0.setFecha(LocalDate.of(2026, 10, 12));
		FeriadoBO feriado1 = new FeriadoBO();
		feriado1.setFecha(LocalDate.of(2026, 11, 9));
		FeriadoBO feriado2 = new FeriadoBO();
		feriado2.setFecha(LocalDate.of(2026, 11, 10));
		FeriadoBO feriado3 = new FeriadoBO();
		feriado3.setFecha(LocalDate.of(2026, 11, 11));
		FeriadoBO feriado4 = new FeriadoBO();
		feriado4.setFecha(LocalDate.of(2026, 11, 23));
		FeriadoBO feriado5 = new FeriadoBO();
		feriado5.setFecha(LocalDate.of(2026, 12, 8));
		FeriadoBO feriado6 = new FeriadoBO();
		feriado6.setFecha(LocalDate.of(2026, 12, 25));
		
		AfipVencimiento afipVto = new AfipVencimiento(1, periodo.minusMonths(1), 9);
		
		when(feriadoService.getFeriadosDesde(periodo)).thenReturn(List.of(feriado0, feriado1, feriado2, feriado3, feriado4, feriado5, feriado6));
		when(vtoPorAporteRepo.findContenido(APORTE, periodo)).thenReturn(Optional.empty());
		when(vtoRepo.findByVigenciaBeforeOrderByVigenciaDesc(periodo)).thenReturn(List.of(afipVto));
		
		LocalDate resultado = service.run(APORTE, CUIT, periodo);
		
		assertThat(resultado).isEqualTo(LocalDate.of(2026, 11, 12));
	}
	
	@Test
	void usaElSeteoPorAportePorEncimaDelSeteoAfipCuandoAmbosExisten() {
		LocalDate periodo = LocalDate.of(2024, 3, 1);
		AporteVencimiento aporteVto = new AporteVencimiento(1, APORTE, periodo.minusMonths(1), null, 10);

		when(feriadoService.getFeriadosDesde(periodo)).thenReturn(Collections.emptyList());
		when(vtoPorAporteRepo.findContenido(APORTE, periodo)).thenReturn(Optional.of(aporteVto));

		LocalDate resultado = service.run(APORTE, CUIT, periodo);

		assertThat(resultado).isEqualTo(LocalDate.of(2024, 4, 10));
	}

	@Test
	void usaElSeteoAfipAjustadoPorDigitoVerificadorDelCuitCuandoNoHaySeteoPorAporte() {
		LocalDate periodo = LocalDate.of(2024, 3, 1);
		String cuitDigitoCinco = "20111111115";
		AfipVencimiento afipVto = new AfipVencimiento(1, periodo.minusMonths(1), 9);

		when(feriadoService.getFeriadosDesde(periodo)).thenReturn(Collections.emptyList());
		when(vtoPorAporteRepo.findContenido(APORTE, periodo)).thenReturn(Optional.empty());
		when(vtoRepo.findByVigenciaBeforeOrderByVigenciaDesc(periodo)).thenReturn(List.of(afipVto));

		LocalDate resultado = service.run(APORTE, cuitDigitoCinco, periodo);

		// base: 9/3 + 1 mes = 9/4 (martes, sin ajuste) + 1 dia por digito verificador (dig 5 -> 2do dia) = 10/4
		assertThat(resultado).isEqualTo(LocalDate.of(2024, 4, 10));
	}

	@Test
	void saltaFinDeSemanaCuandoElVencimientoCalculadoCaeSabadoODomingo() {
		LocalDate periodo = LocalDate.of(2024, 1, 1);
		AporteVencimiento aporteVto = new AporteVencimiento(1, APORTE, periodo.minusMonths(1), null, 3);

		when(feriadoService.getFeriadosDesde(periodo)).thenReturn(Collections.emptyList());
		when(vtoPorAporteRepo.findContenido(APORTE, periodo)).thenReturn(Optional.of(aporteVto));

		LocalDate resultado = service.run(APORTE, CUIT, periodo);

		// base: 3/1 + 1 mes = 3/2/2024 (sabado) -> 4/2 (domingo) -> 5/2/2024 (lunes)
		assertThat(resultado).isEqualTo(LocalDate.of(2024, 2, 5));
	}

	@Test
	void saltaUnFeriadoCuandoElVencimientoCalculadoCoincideConUno() {
		LocalDate periodo = LocalDate.of(2024, 3, 1);
		AporteVencimiento aporteVto = new AporteVencimiento(1, APORTE, periodo.minusMonths(1), null, 10);
		FeriadoBO feriado = new FeriadoBO();
		feriado.setFecha(LocalDate.of(2024, 4, 10));

		when(feriadoService.getFeriadosDesde(periodo)).thenReturn(List.of(feriado));
		when(vtoPorAporteRepo.findContenido(APORTE, periodo)).thenReturn(Optional.of(aporteVto));

		LocalDate resultado = service.run(APORTE, CUIT, periodo);

		// base: 10/4/2024 (miercoles) es feriado -> pasa al 11/4/2024 (jueves)
		assertThat(resultado).isEqualTo(LocalDate.of(2024, 4, 11));
	}

	@Test
	void lanzaBusinessExceptionCuandoNoHaySeteoPorAporteNiSeteoAfip() {
		LocalDate periodo = LocalDate.of(2024, 3, 1);

		when(feriadoService.getFeriadosDesde(periodo)).thenReturn(Collections.emptyList());
		when(vtoPorAporteRepo.findContenido(APORTE, periodo)).thenReturn(Optional.empty());
		when(vtoRepo.findByVigenciaBeforeOrderByVigenciaDesc(periodo)).thenReturn(Collections.emptyList());
		when(messageSource.getMessage(eq("boleta.VTO_SETEO"), any(), any(Locale.class)))
				.thenReturn("No hay seteo de vencimiento (%s)");

		assertThatThrownBy(() -> service.run(APORTE, CUIT, periodo))
				.isInstanceOf(BusinessException.class)
				.extracting(ex -> ((BusinessException) ex).getDescripcion())
				.asString()
				.contains(CUIT);
	}
}
