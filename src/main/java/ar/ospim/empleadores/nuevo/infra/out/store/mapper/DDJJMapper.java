package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoAporteBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAfiliadoDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Afiliado;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Aporte;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DDJJ;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.DDJJEmpleadoAporte;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsulta;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsultaI;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJEmpleadosConsultaI;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJSecuencia;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJSecuenciaI;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJTotales;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJTotalesI;

@Mapper 
public interface DDJJMapper {

	@Mappings({
		@Mapping(target = "empleados", source = "empleados"),
		@Mapping(target = "presentacion", source = "fechaPresentacion")
		})
	DDJJ map(DDJJBO ddjj); 
	    
	@Mapping(source = "cuil", target = "id.cuil_titular") 
	@Mapping(source = "cuil", target = "cuil")
	@Mapping(source = "inte", target = "id.inte")
	Afiliado map(AfiliadoBO afiliadoBo);  
	
	DDJJEmpleadoAporte map(DDJJEmpleadoAporteBO afiliadoAporteBo);
	
	Aporte map(AporteBO aporteBo);
	AporteBO map(Aporte aporte);
	
	@InheritInverseConfiguration
	DDJJBO map(DDJJ ddjj);
	
	List<DDJJBO> map(List<DDJJ> ddjj);
	
	
	 @Mapping(target = "createdBy", ignore = true) 
	 @Mapping(target = "updatedBy", ignore = true)
	 @Mapping(target = "deletedBy", ignore = true)
	 @Mapping(target = "createdOn", ignore = true) 
	 @Mapping(target = "updatedOn", ignore = true)
	 @Mapping(target = "deletedOn", ignore = true)
	 @Mapping(target = "deleted", ignore = true)
	 void mapSinAuditoria(@MappingTarget DDJJ ddjj, DDJJ  ddjjNew); 
	 
	 
	 BoletaPagoDDJJConsulta map(BoletaPagoDDJJConsultaI reg);  
	 
	 @Mapping(target = "empresaId", source = "empresa_id")  
	 @Mapping(target = "razonSocial", source = "razon_Social")  	 
	 DDJJTotales map ( DDJJTotalesI reg);
	
	 DDJJBoletaArmadoDetalleAfiliadoDto map ( BoletaPagoDDJJEmpleadosConsultaI reg);
	    
	 DDJJSecuencia map ( DDJJSecuenciaI reg);
	 
}
