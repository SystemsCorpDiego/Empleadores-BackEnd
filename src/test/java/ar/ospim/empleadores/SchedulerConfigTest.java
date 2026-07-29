package ar.ospim.empleadores;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

public class SchedulerConfigTest {
	// Inicializa el corredor del contexto con la clase de configuración que queremos probar
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(SchedulerConfig.class);
    
    
    @Test
    void debeCrearElSchedulerCuandoLaPropiedadEsTrue() {
        this.contextRunner
                .withPropertyValues("app.cron.habilitado=true")
                .run(context -> {
                    // Verifica que el procesador interno de @Scheduled de Spring se haya creado
                    assertThat(context).hasSingleBean(ScheduledAnnotationBeanPostProcessor.class);
                    System.out.println("¡Scheduler creado exitosamente cuando está en true!");
                });
    }
    
    @Test
    void noDebeCrearElSchedulerCuandoLaPropiedadEsFalse() {
        this.contextRunner
                .withPropertyValues("app.cron.habilitado=false")
                .run(context -> {
                    // Verifica que el procesador NO exista en el contexto
                    assertThat(context).doesNotHaveBean(ScheduledAnnotationBeanPostProcessor.class);
                    System.out.println("¡Scheduler ignorado exitosamente cuando está en false!");
                });
    }

    @Test
    void debeCrearElSchedulerPorDefectoSiLaPropiedadNoExiste() {
        this.contextRunner
                // No enviamos ninguna propiedad para probar el "matchIfMissing = false"
                .run(context -> {
                    assertThat(context).doesNotHaveBean(ScheduledAnnotationBeanPostProcessor.class);
                    System.out.println("¡Scheduler NO creado por defecto!");
                });
    }
    
}
