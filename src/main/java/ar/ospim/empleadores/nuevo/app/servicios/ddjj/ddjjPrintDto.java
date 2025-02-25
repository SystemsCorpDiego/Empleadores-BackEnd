package ar.ospim.empleadores.nuevo.app.servicios.ddjj;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ddjjPrintDto implements Comparable<ddjjPrintDto> {
	private String cuil;
	private String apellidonombre;
	private String camara;
	private String categoria;
	private String ingreso;
	private String planta;
	private BigDecimal remunerativo;
	private BigDecimal no_remunerativo;

	private String aporte1;
	private String aporteDescrip1;
	private BigDecimal importe1;
	 
	private String aporte2;
	private String aporteDescrip2;
	private BigDecimal importe2;
	
	private String aporte3;
	private String aporteDescrip3;
	private BigDecimal importe3;
	
	private String aporte4;
	private String aporteDescrip4;
	private BigDecimal importe4;

	private String aporte5;
	private String aporteDescrip5;
	private BigDecimal importe5;
	
	private String aporte6;
	private String aporteDescrip6;
	private BigDecimal importe6;
	
	 @Override
	 public int compareTo(ddjjPrintDto ddjj) {
	    return this.apellidonombre.compareTo(ddjj.apellidonombre);
	 }

}
