package ar.ospim.empleadores.comun.i18n;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class SmartLocaleResolver extends AcceptHeaderLocaleResolver {

    @Value("${app.default.language}")
    protected String defaultLanguage;

    @Value("${app.other.languages}")
    protected Set<String> othersLanguages;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        final String acceptLanguage = request.getHeader("Accept-Language");
        return othersLanguages.stream().filter(l -> l.equalsIgnoreCase(acceptLanguage))
                .findAny().map(Locale::forLanguageTag).orElse(Locale.forLanguageTag(defaultLanguage));
        }
}
