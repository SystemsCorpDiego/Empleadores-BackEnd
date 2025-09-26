package ar.ospim.empleadores.comun.dates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import ar.ospim.empleadores.comun.strings.StringHelper;

@Component
public class DateTimeProvider {
	DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");	
	
    public LocalDateTime nowDateTime(){
        LocalDateTime result = LocalDateTime.now();
        return result;
    }

    public LocalDate nowDate(){
        LocalDate result = LocalDate.now();
        return result;
    }

    public LocalDateTime nowDateTimeWithZone(ZoneId zoneId) {
        LocalDateTime localDateTime = LocalDateTime.now()
                .atZone(ZoneId.of(JacksonDateFormatConfig.UTC_ZONE_ID))
                .withZoneSameInstant(zoneId)
                .toLocalDateTime();
        return localDateTime;
    }
    
    public LocalDate getPeriodoMax() {
    	LocalDate hoy = LocalDate.now();
		LocalDate periodoMax = LocalDate.of(hoy.getYear(), hoy.getMonthValue(), 1);
		periodoMax = periodoMax.plusMonths(-1L);
		return periodoMax;
    }
    
    public LocalDate getUltimoDiaMes(LocalDate fecha) {    	
		LocalDate periodoMax = LocalDate.of(fecha.getYear(), fecha.getMonthValue(), 1);
		periodoMax = periodoMax.plusMonths(1L);
		periodoMax = periodoMax.minusDays(1L);
		return periodoMax;
    }
    
    public LocalDate getPeriodoDesde(LocalDate date) {
    	if(date==null)
    		return LocalDate.of(1900, 1, 1);
    	
    	return date;
    }
    
    public LocalDate getPeriodoHasta(LocalDate date) {
    	if (date == null)
    		return LocalDate.of(3000, 1, 1);
    		
    	return date;
    }
    
    public LocalDateTime getLocalDate(String sFecha) {
    	LocalDateTime dateTime = null;
    	if ( StringHelper.isNullOrWhiteSpace(sFecha)  ) 
    		return dateTime;
    	try {    	 
	    	LocalDate oDate = LocalDate.parse(sFecha, DATE_FORMATTER);
	    	dateTime = oDate.atStartOfDay();
    	} catch ( Exception e ) {
	    		LocalDate oDate = LocalDate.parse(sFecha, DATETIME_FORMATTER);
		    	dateTime = oDate.atStartOfDay();
    	}
    	
    	return dateTime; 
    }
    
    public String getPeriodoToString(LocalDate date) {
    	DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    	return CUSTOM_FORMATTER.format(date) ;
    }

    public String getPeriodoToString(LocalDateTime date) {
    	DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    	return CUSTOM_FORMATTER.format(date) ;
    }
    
    public String getDateToString(LocalDate date) {
    	return getDateToString(date.atStartOfDay());
    }
    
    public String getDateToString(LocalDateTime date) {
    	DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	return CUSTOM_FORMATTER.format(date);
    }
    
    public String getToStringSql(LocalDate date) {
    	DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	return CUSTOM_FORMATTER.format(date);
    }    

    public String getToStringBEP(LocalDate date) {
    	DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    	return CUSTOM_FORMATTER.format(date);
    }    
    
    public LocalDate getDateBEP(String sFecha) {
    	DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    	//return CUSTOM_FORMATTER.format(sFecha);
    	
    	LocalDate dateTime = null;
    	if ( StringHelper.isNullOrWhiteSpace(sFecha)  ) 
    		return dateTime;
    	try {    	 
	    	dateTime = LocalDate.parse(sFecha, CUSTOM_FORMATTER);
    	} catch ( Exception e ) {    	}
    	
    	return dateTime; 
    }    
    
    public String getDesdeToStringSql(LocalDate date) {
    	if ( date == null)
    		return "1900-01-01";
    	return getToStringSql(date);
    }
    
    public String getPeriodoDesdeToStringSql(LocalDate date) {
    	if ( date != null)
    		date = getPeriodoDesde(date);
    	return getDesdeToStringSql(date);
    }

    public String getHastaToStringSql(LocalDate date) {
    	if ( date == null)
    		return "9999-01-01";
    	return getToStringSql(date);
    }
    
    public String getPeriodoHastaToStringSql(LocalDate date) {
    	if ( date != null)
    		date = getPeriodoHasta(date);
    	return getHastaToStringSql(date);
    }
    
    public LocalDate getAnioNuevo(Integer anio) {
   		return LocalDate.of(anio, 1, 1);
    }

}
