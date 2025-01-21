package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.AporteSeteoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.AporteSeteo;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.AporteSeteoVigenteConsultaI;

@Mapper
public interface AporteSeteoMapper {
	
	@Mapping(target = "camara", source = "camara.codigo")
	@Mapping(target = "entidad", source = "entidad.codigo")
	@Mapping(target = "calculoTipo", source = "calculo.tipo")
	@Mapping(target = "calculoValor", source = "calculo.valor") 
	@Mapping(target = "calculoBase", source = "calculo.base")
	@Mapping(target = "socio", source = "esSocio")
	void map(AporteSeteoBO aporteSeteo, @MappingTarget AporteSeteo aporteSeteoNew);
	
	
	@Mapping(target = "camara", source = "camara.codigo")
	@Mapping(target = "entidad", source = "entidad.codigo")
	@Mapping(target = "calculoTipo", source = "calculo.tipo")
	@Mapping(target = "calculoValor", source = "calculo.valor") 
	@Mapping(target = "calculoBase", source = "calculo.base")
	@Mapping(target = "socio", source = "esSocio")
	AporteSeteo map(AporteSeteoBO reg);
	
	@Mapping(target = "camara.codigo", source = "camara")
	@Mapping(target = "entidad.codigo", source = "entidad")
	@Mapping(target = "calculo.tipo", source = "calculoTipo")
	@Mapping(target = "calculo.valor", source = "calculoValor") 
	@Mapping(target = "calculo.base", source = "calculoBase")
	@Mapping(target = "esSocio", source = "socio")
	AporteSeteoBO map(AporteSeteo reg);
	
	AporteSeteo map(AporteSeteoVigenteConsultaI reg);  
	List<AporteSeteo> map(List<AporteSeteoVigenteConsultaI> reg);
	
}
