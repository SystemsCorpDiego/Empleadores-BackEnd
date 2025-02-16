package ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ar.ospim.empleadores.nuevo.app.dominio.AjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoAjusteBO;
import ar.ospim.empleadores.nuevo.app.dominio.BoletaPagoBO;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoActualizarDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoConsConDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoConsSinDDJJDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.BoletaPagoDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.boleta.dto.DDJJBoletaPagoAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleAjusteDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.app.ddjj.dto.DDJJBoletaArmadoDetalleDto;
import ar.ospim.empleadores.nuevo.infra.out.store.repository.querys.BoletaPagoDDJJConsulta;

@Mapper 
public interface BoletaPagoDtoMapper {

	@Mapping(target = "aporte.entidad", source = "reg.entidad")
	@Mapping(target = "empresa.id", source = "empresaId")
	@Mapping(target = "actaNro", source = "reg.nroActa")
	@Mapping(target = "descripcion", source = "reg.razonDePago")
	@Mapping(target = "intencionDePago", source = "reg.intencionDePago")
	BoletaPagoBO map(BoletaPagoDto reg, Integer empresaId);
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "empresa.id", source = "empresaId")
	@Mapping(target = "actaNro", source = "dto.nroActa")
	@Mapping(target = "descripcion", source = "dto.razonDePago")
	@Mapping(target = "intencionDePago", source = "dto.intencionDePago")
	BoletaPagoBO map(BoletaPagoDto dto, Integer empresaId, Integer id);
	
	
	@Mapping(target = "aporte.codigo", source = "codigo")
	@Mapping(target = "intencionDePago", source = "intencionDePago")
	@Mapping(target = "formaDePago", source = "formaDePago")
	BoletaPagoBO map(DDJJBoletaPagoAltaDto dto);
	
	List<BoletaPagoBO> map(List<DDJJBoletaPagoAltaDto> dto);	
	
	
	@Mapping(target = "empresa.id", source = "empresaId")
	@Mapping(target = "id", source = "id")	
	@Mapping(target = "intencionDePago", source = "dto.intencionDePago")
	@Mapping(target = "formaDePago", source = "dto.formaDePago")
	BoletaPagoBO map(Integer empresaId, Integer id, BoletaPagoActualizarDto dto);

	@Mapping(target = "empresa.id", source = "empresaId")
	@Mapping(target = "id", source = "id")	
	BoletaPagoBO map(Integer empresaId, Integer id);

	
	 
	
	@Mapping(target = "codigo", source = "aporte") 
	@Mapping(target = "cuit", source = "cuit") 
	@Mapping(target = "empresaId", source = "empresaId") 
	@Mapping(target = "razonSocial", source = "razonSocial") 
	@Mapping(target = "descripcion", source = "aporteDescripcion")
	@Mapping(target = "totalFinal", source = "totalFinal")	
	@Mapping(target = "totalAjuste", source = "ajuste")
	@Mapping(target = "totalConcepto", source = "importe")
	@Mapping(target = "interes", source = "interes")	
	@Mapping(target = "intencionDePago", source = "intencionDePago")
	@Mapping(target = "formaDePago", source = "formaDePagoDescripcion")	 
	@Mapping(target = "numeroBoleta", source = "secuenciaBoleta")	  
	@Mapping(target = "declaracionJuradaId", source = "ddjjId")
	@Mapping(target = "requiereBep", expression = "java( reg.getRequiereBep() )" )
	@Mapping(target = "estadoBep", expression = "java( reg.getBepEstado() )" )
	BoletaPagoConsConDDJJDto map(BoletaPagoDDJJConsulta reg);
	List<BoletaPagoConsConDDJJDto> mapConsConDDJJ(List<BoletaPagoDDJJConsulta> dto);	

	
	
	//BoletaPagoBO a BoletaPagoConsSinDDJJDto
	@Mapping(target = "entidad", source = "aporte.entidad")	
	@Mapping(target = "nroActa", source = "actaNro")
	@Mapping(target = "razonDePago", source = "descripcion")
	@Mapping(target = "numeroBoleta", source = "secuencia")	
	BoletaPagoConsSinDDJJDto map(BoletaPagoBO bo);
	List<BoletaPagoConsSinDDJJDto> mapConsSinDDJJ(List<BoletaPagoBO> dto);	
	
	
	@Mapping(target = "descripcion", source = "periodo")
	@Mapping(target = "monto", source = "importe")	
	DDJJBoletaArmadoDetalleAjusteDto map (AjusteBO ajuste);
	List<DDJJBoletaArmadoDetalleAjusteDto> mapBo (List<AjusteBO> ajustes);

	@Mapping(target = "descripcion", source = "ajuste.periodo")
	@Mapping(target = "motivo", source = "ajuste.motivo")
	@Mapping(target = "monto", source = "importe")	
	DDJJBoletaArmadoDetalleAjusteDto  mapBPA( BoletaPagoAjusteBO ajuste);
	List<DDJJBoletaArmadoDetalleAjusteDto>  mapBPA( List<BoletaPagoAjusteBO> ajustes);

	@Mapping(target = "aporte.codigo", source = "dto.codigo")
	@Mapping(target = "aporte.descripcion", source = "dto.descripcion")
	@Mapping(target = "importe", source = "dto.total_acumulado")
	@Mapping(target = "interes", source = "dto.interes") 
	@Mapping(target = "ddjjId", source = "dto.declaracionJuradaId")
	@Mapping(target = "secuencia", source = "dto.numeroBoleta")
	@Mapping(target = "intencionDePago", source = "dto.intencionDePago")
	@Mapping(target = "formaDePago", source = "dto.formaDePago")	
	@Mapping(target = "empresa.id", source = "empresaId")	 
	BoletaPagoBO mapDto (DDJJBoletaArmadoDetalleDto  dto, Integer empresaId); 
  
	
	@Mapping(target = "id", source = "id")
	@Mapping(target = "numeroBoleta", source = "secuencia")
	//@Mapping(target = "totalFinal", source = "importe")
	@Mapping(target = "codigo", source = "aporte.codigo")
	@Mapping(target = "descripcion", source = "aporte.descripcion")
	@Mapping(target = "declaracionJuradaId", source = "ddjjId")	
	@Mapping(target = "interes", source = "interes")
	@Mapping(target = "formaDePago", source = "formaDePago")
	@Mapping(target = "intencionDePago", source = "intencionDePago")	
	DDJJBoletaArmadoDetalleDto mapDto (BoletaPagoBO bo);
	
}
