package ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;
import ar.ospim.empleadores.nuevo.app.dominio.LocalidadBO;
import ar.ospim.empleadores.nuevo.app.dominio.ProvinciaBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.LocalidadDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.comun.dto.ProvinciaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaDomicilioAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaDomicilioDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.empresa.dto.EmpresaPlantaDto;

@Mapper
public interface EmpresaDomicilioDtoMapper {
	
	EmpresaDomicilioBO map(EmpresaDomicilioDto dto);
	
	EmpresaDomicilioDto map(EmpresaDomicilioBO bo); 	

	EmpresaPlantaDto mapPlanta(EmpresaDomicilioBO bo); 

	List<EmpresaPlantaDto> mapPlanta(List<EmpresaDomicilioBO> bo);
	
	List<EmpresaDomicilioDto> map(List<EmpresaDomicilioBO> lstBo);

	
	@Mapping(target = "provincia.id", source = "provinciaId")
	@Mapping(target = "localidad.id", source = "localidadId")
	EmpresaDomicilioBO map(EmpresaDomicilioAltaDto dto);
	
	@Mapping(target = "provincia.id", source = "dto.provinciaId")
	@Mapping(target = "localidad.id", source = "dto.localidadId")
	EmpresaDomicilioBO map(Integer id, EmpresaDomicilioAltaDto dto);
	 
	
	
	@Mapping(target = "descripcion", source = "detalle")
	ProvinciaDto map(ProvinciaBO bo);
	
	@Mapping(target = "provinciaId", source = "provincia.id")
	LocalidadDto map(LocalidadBO bo);

}
