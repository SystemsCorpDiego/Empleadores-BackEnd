package ar.ospim.empleadores.nuevo.infra.out.store.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoAjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.Ajuste;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BoletaPago;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.entity.BoletaPagoAjuste;

@Mapper
public interface AjusteMapper {
	 
	 @Named( "sinAjustes" )
	 @Mapping( target = "ajustes", ignore = true)
	 BoletaPagoBO mapBoletaToDtoSinAjustes(BoletaPago bp);
	 
	 @Named( "sinBoletas" )
	 @Mapping( target = "boletas", ignore = true)
	 AjusteBO mapAjusteToDtoSinBoletas( Ajuste reg );

	 Ajuste map(AjusteBO reg);
	
	
	@Mapping(target = "boletas", source = "boletaPagoAjuste")
	AjusteBO map(Ajuste reg);
	
	@Mapping(target = "ajuste", qualifiedByName = "sinBoletas")	
	@Mapping( target = "boletaPago", qualifiedByName = "sinAjustes")
	BoletaPagoAjusteBO map2(BoletaPagoAjuste bpa);   
	List<BoletaPagoAjusteBO> map2(List<BoletaPagoAjuste>  lst);
	
	 //List<BoletaPagoAjusteBO> ajustes;
	 
	
	List<AjusteBO> map(List<Ajuste> reg);
	
	@Mapping( target="id", ignore = true)
	void map(@MappingTarget Ajuste newReg, AjusteBO reg);
	
	
	 
}
