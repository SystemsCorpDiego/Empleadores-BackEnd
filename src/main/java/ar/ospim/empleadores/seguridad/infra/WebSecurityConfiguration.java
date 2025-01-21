package ar.ospim.empleadores.seguridad.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.ospim.empleadores.auth.jwt.infra.input.rest.filtro.AuthenticationTokenFilter;
import ar.ospim.empleadores.auth.oauth.infra.input.OAuth2AuthenticationFilter;
import ar.ospim.empleadores.comun.actuator.infra.config.ActuatorConfiguration;
import ar.ospim.empleadores.nuevo.infra.out.store.enums.ERol;
import ar.ospim.empleadores.seguridad.infra.filtro.AuthorizationFilter;
import ar.ospim.empleadores.seguridad.infra.filtro.PublicApiAuthenticationFilter;
import ar.ospim.empleadores.seguridad.infra.filtro.TwoFactorAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String RECAPTCHA = "/recaptcha";

	private static final String PASSWORD_RESET = "/password-reset";
	
	private static final String BACKOFFICE = "/backoffice";

	private static final String PUBLIC = "/public";

	private static final String[] SWAGGER_RESOURCES = {
			"/v3/**",
			"/swagger-ui/**"
	};

	private String[] BOOKING_API_RESOURCES = new String[]{};

	private final AuthenticationTokenFilter authenticationTokenFilter;

	private final ActuatorConfiguration actuatorConfiguration;

	private final AuthorizationFilter authorizationFilter;

	private final PublicApiAuthenticationFilter publicApiAuthenticationFilter;

	private final OAuth2AuthenticationFilter oAuth2AuthenticationFilter;

	private final TwoFactorAuthenticationFilter twoFactorAuthenticationFilter;

	public WebSecurityConfiguration(AuthenticationTokenFilter authenticationTokenFilter,
									ActuatorConfiguration actuatorConfiguration,
									AuthorizationFilter authorizationFilter,
									PublicApiAuthenticationFilter publicApiAuthenticationFilter,
									OAuth2AuthenticationFilter oAuth2AuthenticationFilter,
									TwoFactorAuthenticationFilter twoFactorAuthenticationFilter) {
		this.authenticationTokenFilter = authenticationTokenFilter;
		this.actuatorConfiguration = actuatorConfiguration;
		this.authorizationFilter = authorizationFilter;
		this.publicApiAuthenticationFilter = publicApiAuthenticationFilter;
		this.oAuth2AuthenticationFilter = oAuth2AuthenticationFilter;
		this.twoFactorAuthenticationFilter = twoFactorAuthenticationFilter;
		 
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		// @formatter:off
		httpSecurity.csrf().disable()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
				.requestMatchers(req-> req.getRequestURI().contains(PUBLIC)).permitAll()
				.antMatchers(  HttpMethod.POST,  "/**"+ PUBLIC + "/**").permitAll()
				.antMatchers("/actuator/health").permitAll()
				.antMatchers("/actuator/env/**").hasAnyAuthority(
						ERol.ROOT.getValue(),
						ERol.ADMINISTRADOR.getValue())
				.antMatchers("/actuator/**").access(actuatorConfiguration.getAccessInfo())
				.antMatchers( "/auth/**").permitAll()
				.antMatchers(SWAGGER_RESOURCES).permitAll()
				.antMatchers(BACKOFFICE + "/properties").hasAnyAuthority(
						ERol.ROOT.getValue(),
						ERol.ADMINISTRADOR.getValue())
				.antMatchers(BACKOFFICE + "/**").hasAnyAuthority(
					ERol.ROOT.getValue(),
					ERol.ADMINISTRADOR.getValue())
				.antMatchers(RECAPTCHA + "/**").permitAll()
				.antMatchers("/oauth/**").permitAll()
				.antMatchers(HttpMethod.POST, PASSWORD_RESET).permitAll()
				.antMatchers(HttpMethod.GET, "/bed/reports/**").permitAll()
				.antMatchers(HttpMethod.GET, "/assets/**").permitAll()
				.antMatchers("/fhir/**").permitAll()
				.antMatchers(BOOKING_API_RESOURCES).permitAll()
				//.antMatchers("/public-api/**").hasAnyAuthority(ERol.API_CONSUMER.getValue())
				.antMatchers("/**").authenticated()
		.anyRequest().authenticated();

		// @formatter:on
		httpSecurity.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		httpSecurity.addFilterAfter(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		httpSecurity.addFilterAfter(oAuth2AuthenticationFilter, AuthenticationTokenFilter.class);
		httpSecurity.addFilterAfter(publicApiAuthenticationFilter, OAuth2AuthenticationFilter.class);
		httpSecurity.addFilterAfter(authorizationFilter, PublicApiAuthenticationFilter.class);
		httpSecurity.addFilterAfter(twoFactorAuthenticationFilter, AuthorizationFilter.class);
	}

}
