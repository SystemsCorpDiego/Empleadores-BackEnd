package ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.AporteSeteoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.aporte.dto.AporteSeteoDto;

@Mapper
public interface AporteSeteoDtoMapper {

	@Mapping(target = "camara.codigo", source = "camara")
	@Mapping(target = "entidad.codigo", source = "entidad")
	@Mapping(target = "esSocio", source = "socio")
	@Mapping(target = "calculo.tipo", source = "calculoTipo")
	@Mapping(target = "calculo.valor", source = "calculoValor")
	@Mapping(target = "calculo.base", source = "calculoBase")
	@Mapping(target = "categoria", source = "camaraCategoria")
	@Mapping(target = "antiguedad", source = "camaraAntiguedad")
	AporteSeteoBO map(AporteSeteoDto aporteSeteo);
	
	@Mapping(target = "camara", source = "camara.codigo")
	@Mapping(target = "entidad", source = "entidad.codigo")	
	@Mapping(target = "socio", source = "esSocio")
	@Mapping(target = "calculoTipo", source = "calculo.tipo")
	@Mapping(target = "calculoValor", source = "calculo.valor")
	@Mapping(target = "calculoBase", source = "calculo.base")
	@Mapping(target = "camaraCategoria", source = "categoria")
	@Mapping(target = "camaraAntiguedad", source = "antiguedad")
	AporteSeteoDto map(AporteSeteoBO aporteSeteo);

	List<AporteSeteoDto> map(List<AporteSeteoBO> lst);

	@Mapping(target = "id", source = "id")
	@Mapping(target = "camara.codigo", source = "dto.camara")
	@Mapping(target = "entidad.codigo", source = "dto.entidad")
	@Mapping(target = "esSocio", source = "dto.socio")
	@Mapping(target = "calculo.tipo", source = "dto.calculoTipo")
	@Mapping(target = "calculo.valor", source = "dto.calculoValor")
	@Mapping(target = "calculo.base", source = "dto.calculoBase")
	@Mapping(target = "categoria", source = "dto.camaraCategoria")
	@Mapping(target = "antiguedad", source = "dto.camaraAntiguedad")
	AporteSeteoBO map(AporteSeteoDto dto, Integer id);
	
}
