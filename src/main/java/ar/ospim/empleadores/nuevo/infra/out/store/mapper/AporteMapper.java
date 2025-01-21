package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ar.ospim.empleadores.nuevo.app.dominio.AporteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BancoMovimientoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Aporte;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BancoConvenioMovimiento;

@Mapper 
public interface AporteMapper {
 
	Aporte map(AporteBO aporte);
	AporteBO map(Aporte aporte);
	
	
	
	void map(AporteBO aporte, @MappingTarget Aporte aporteNew);

	List<AporteBO> map(List<Aporte> aporte);

	
	@Mapping(target = "convenioCodigo", source = "convenio.codigo")
	@Mapping(target = "convenioCuenta", source = "convenio.cuenta")
	@Mapping(target = "bancoDescripcion", source = "convenio.sucursal.banco.descripcion")
	@Mapping(target = "sucursalCodigo", source = "convenio.sucursal.codigo")
	@Mapping(target = "sucursalDescripcion", source = "convenio.sucursal.descripcion")
	BancoMovimientoBO map(BancoConvenioMovimiento reg);
	
}

