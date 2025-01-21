package ar.ospim.empleadores.nuevo.app.servicios.empresa.validaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ContactoTipoValidator.class)
@Target( ElementType.FIELD )
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactoTipoValid {
	Class<? extends Enum<?>> enumClass();
	String message() default "Codigo de Tipo de contacto inexistente.";	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
	