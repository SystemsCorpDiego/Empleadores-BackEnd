package ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.AfiliadoBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoAporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.DDJJEmpleadoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJEmpleadoAporteConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJEmpleadoAporteDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJEmpleadoConsultaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJEmpleadoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJPeriodoAnteriorAfiliadoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJPeriodoAnteriorDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJTotalesAportesDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJTotalesEmpresaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJValidarCuilDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.DDJJTotales;

@Mapper 
public interface DDJJDtoMapper {
	
	DDJJBO map(DDJJAltaDto reg);  
	  

	@Mapping(target = "empresa.id", source = "empresaId")
	DDJJBO map(Integer empresaId, DDJJAltaDto reg); 
	
	@Mapping(target = "inte", constant = "0")
	AfiliadoBO dDJJEmpleadoDtoToAfiliadoBO(DDJJEmpleadoDto dDJJEmpleadoDto);
	
	@Mapping(target = "ingreso", source = "fechaIngreso")
	@Mapping(target = "afiliado.cuil", source = "cuil")
	@Mapping(target = "afiliado.inte", source = "inte")
	@Mapping(target = "afiliado.apellido", source = "apellido")
	@Mapping(target = "afiliado.nombre", source = "nombre")
	@Mapping(target = "empresaDomicilio.id", source = "empresaDomicilioId")
	DDJJEmpleadoBO map(DDJJEmpleadoDto reg);

 
	 
	DDJJAltaDto map(DDJJBO reg);	 
	
	@Mapping(target = "cuil", source = "reg.afiliado.cuil")
	@Mapping(target = "inte", source = "reg.afiliado.inte")
	@Mapping(target = "apellido", source = "reg.afiliado.apellido")
	@Mapping(target = "nombre", source = "reg.afiliado.nombre")
	@Mapping(target = "fechaIngreso", source = "reg.ingreso")
	@Mapping(target = "empresaDomicilioId", source = "reg.empresaDomicilio.id")	
	DDJJEmpleadoDto map(DDJJEmpleadoBO reg);	
	
	@Mapping(target = "codigo", source = "aporte.codigo")
	@Mapping(target = "descripcion", source = "aporte.descripcion") 
	DDJJEmpleadoAporteDto map( DDJJEmpleadoAporteBO reg);

	
	DDJJTotalesEmpresaDto map(DDJJTotales reg);
	
	@Mapping(target = "codigo", source = "aporte") 
	DDJJTotalesAportesDto mapAporte(DDJJTotales reg);
	
	// Mapeo a Consulta DDJJ	
	@Mapping(target = "empresaId", source = "empresa.id")
	@Mapping(target = "afiliados", source = "empleados")	
	DDJJConsultaDto mapConsulta(DDJJBO reg);
	
	@Mapping(target = "cuil", source = "afiliado.cuil")
	@Mapping(target = "inte", source = "afiliado.inte")
	@Mapping(target = "apellido", source = "afiliado.apellido")
	@Mapping(target = "nombre", source = "afiliado.nombre")
	@Mapping(target = "fechaIngreso", source = "ingreso")
	@Mapping(target = "empresaDomicilioId", source = "empresaDomicilio.id")
	@Mapping(target = "uomaSocio", source = "uomaSocio")
	@Mapping(target = "amtimaSocio", source = "amtimaSocio")
	DDJJEmpleadoConsultaDto mapConsulta(DDJJEmpleadoBO reg);
	List<DDJJEmpleadoConsultaDto> mapConsultaEmpleLst(List<DDJJEmpleadoBO> reg);
	
	@Mapping(target = "codigo", source = "aporte.codigo")
	DDJJEmpleadoAporteConsultaDto mapConsulta(DDJJEmpleadoAporteBO reg);
	List<DDJJEmpleadoAporteConsultaDto> mapConsultaAproteLst(List<DDJJEmpleadoAporteBO> reg);
	// FIN: Mapeo a Consulta DDJJ

	//Mappers para FUncion de validacion de cuiles 
	DDJJValidarCuilDto map(String cuil);
	List<DDJJValidarCuilDto> map(List<String> cuil);
 	
	@Mapping(target = "afiliados", source = "empleados")
	@Mapping(target = "id", ignore = true)
	DDJJPeriodoAnteriorDto mapPeriodoAnterior(DDJJBO bo);
		
	
	@Mapping(target = "cuil", source = "afiliado.cuil") 
	@Mapping(target = "inte", source = "afiliado.inte")
	@Mapping(target = "fechaIngreso", source = "ingreso")
	@Mapping(target = "apellido", source = "afiliado.apellido")
	@Mapping(target = "nombre", source = "afiliado.nombre")
	@Mapping(target = "empresaDomicilioId", source = "empresaDomicilio.id")
	DDJJPeriodoAnteriorAfiliadoDto  mapPeriodoAnterior(DDJJEmpleadoBO bo);
	
}
