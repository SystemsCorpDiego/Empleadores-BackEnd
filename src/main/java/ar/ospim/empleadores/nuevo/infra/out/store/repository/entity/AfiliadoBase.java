package ar.ospim.empleadores.nuevo.infra.out.store.repository.entity;

 
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AfiliadoBase extends BaseEntity {

	private static final long serialVersionUID = 4992139162644535311L;

	private String cuil;
	private String apellido;
	private String nombre;

	private Date alta_fecha;
	private String alta_usr;
	private Date modi_fecha;
	private String modi_usr;
	private Date baja_fecha;
	private String baja_usr;
	
	



	public AfiliadoBase(AfiliadoBase afiliado) {
		super();
		
		this.apellido = afiliado.apellido;
		this.nombre = afiliado.nombre;
		this.cuil = afiliado.cuil;
		this.alta_fecha = afiliado.alta_fecha;
		this.alta_usr = afiliado.alta_usr;
		this.modi_fecha = afiliado.modi_fecha;
		this.modi_usr = afiliado.modi_usr;
		this.baja_fecha = afiliado.baja_fecha;
		this.baja_usr = afiliado.baja_usr;
	}

	public AfiliadoBase() {

	}

	public AfiliadoBase(String cuil, int inte, String apellido, String nombre) {
		this.cuil = cuil;
		this.apellido = apellido;
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	public Date getAlta_fecha() {
		return alta_fecha;
	}

	public void setAlta_fecha(Date alta_fecha) {
		this.alta_fecha = alta_fecha;
	}

	public String getAlta_usr() {
		return alta_usr;
	}

	public void setAlta_usr(String alta_usr) {
		this.alta_usr = alta_usr;
	}

	public Date getModi_fecha() {
		return modi_fecha;
	}

	public void setModi_fecha(Date modi_fecha) {
		this.modi_fecha = modi_fecha;
	}

	public String getModi_usr() {
		return modi_usr;
	}

	public void setModi_usr(String modi_usr) {
		this.modi_usr = modi_usr;
	}

	public Date getBaja_fecha() {
		return baja_fecha;
	}

	public String getBaja_fechaString() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return baja_fecha != null ? format.format(baja_fecha) : "";
	}

	public void setBaja_fecha(Date baja_fecha) {
		this.baja_fecha = baja_fecha;
	}

	public String getBaja_usr() {
		return baja_usr;
	}

	public void setBaja_usr(String baja_usr) {
		this.baja_usr = baja_usr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

