package ar.ospim.empleadores;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "app.cron.habilitado", havingValue = "true", matchIfMissing = false)
public class SchedulerConfig {
	// Se activa en Scheduler Globalmente solo cuando la propiedad es TRUE
	
}
