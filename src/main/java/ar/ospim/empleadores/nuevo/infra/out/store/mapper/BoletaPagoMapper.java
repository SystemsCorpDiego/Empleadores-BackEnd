package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoAjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoAporteBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BoletaPago;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BoletaPagoAjuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsulta;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsultaI;

@Mapper 
public interface BoletaPagoMapper {
	
	@Mapping(target = "ajuste.id", source = "id") 
	BoletaPagoAjuste map( AjusteBO reg);
	
	@Mapping(target = "boletaPago", ignore = true )
	BoletaPagoAjuste mapBpa(BoletaPagoAjusteBO bpa);
	
	List<BoletaPagoAjuste> mapAjustes( List<AjusteBO> ajustes);	
	BoletaPago map(BoletaPagoBO reg);  
	
	@InheritInverseConfiguration
	BoletaPagoBO map(BoletaPago reg);

	@Mapping(target = "boletaPago", ignore = true )
	BoletaPagoAjusteBO mapBpa(BoletaPagoAjuste bpa);
	
	@Mapping(target = "id", source = "ajuste.id")
	@Mapping(target = "empresa.id", source = "ajuste.empresa.id")
	@Mapping(target = "empresa.cuit", source = "ajuste.empresa.cuit")
	@Mapping(target = "empresa.razonSocial", source = "ajuste.empresa.razonSocial")		
	@Mapping(target = "aporte.codigo", source = "ajuste.aporte.codigo")
	@Mapping(target = "aporte.descripcion", source = "ajuste.aporte.descripcion")
	@Mapping(target = "periodo", source = "ajuste.periodo")
	@Mapping(target = "vigencia", source = "ajuste.vigencia")
	@Mapping(target = "importe", source = "ajuste.importe")	 
	AjusteBO map(BoletaPagoAjuste reg);
	
	List<AjusteBO> mapBpAjuste(List<BoletaPagoAjuste> ajustes);
	
	
	@Mapping(target = "empresa", ignore = true) 
	void map(@MappingTarget BoletaPago boleta, BoletaPago  boletaNew); 
		 
	
	@Mapping(target = "ddjjId", source = "ddjj.id") 
	@Mapping(target = "empresa", source = "ddjj.empresa") 
	@Mapping(target = "aporte", source = "empleAporte.aporte") 
	@Mapping(target = "importe", source = "empleAporte.importe") 
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "secuencia", ignore = true) 
	BoletaPagoBO map(DDJJBO ddjj, DDJJEmpleadoAporteBO empleAporte );

	List<BoletaPagoBO > map(List<BoletaPago> lst);
	
	BoletaPagoDDJJConsulta map(BoletaPagoDDJJConsultaI reg); 
}
