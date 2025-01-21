package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.usuarioempresaalta;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import ar.ospim.empleadores.comun.infra.input.service.BasicDataEmpresaDto;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaDomicilioBO;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface UsuarioEmpresaAltaDtoMapper { 
     
    //@Mapping( target="contactos", source ="emailAlternativos", qualifiedByName = "dtoMailAltToBoContacto")
	@Mapping( target="contactos.tipo", constant ="TEL")
	@Mapping( target="contactos.prefijo", source ="telefono_prefijo")
    @Mapping( target="contactos.valor", source ="telefono")
    @Mapping( target="actividadMolinera", source ="actividad_molinera")
    EmpresaBO map(UsuarioEmpresaAltaDto empresa); 
    
    
    @Mapping( target="provincia.id", source ="provinciaId")
    @Mapping( target="localidad.id", source ="localidadId")    
    EmpresaDomicilioBO map(EmpresaDomicilioDto dto);

    @Mapping( target="cuit", ignore = true) 
    @Mapping( target="razonSocial", ignore = true)    
    @Mapping( target="domicilios", ignore = true)
	@Mapping( target="contactos.tipo", constant ="WHATSAP")
	@Mapping( target="contactos.prefijo", source ="whatsapp_prefijo")
    @Mapping( target="contactos.valor", source ="whatsapp")
     void mapWhatsap(@MappingTarget EmpresaBO empresaBO, UsuarioEmpresaAltaDto empresa);
    
    
    @Mapping( target="cuit", ignore = true)
    @Mapping( target="razonSocial", ignore = true)    
    @Mapping( target="domicilios", ignore = true)
	@Mapping( target="contactos.tipo", constant ="MAIL")
	@Mapping( target="contactos.prefijo", ignore = true)
    @Mapping( target="contactos.valor", source ="email")
    void mapMail(@MappingTarget EmpresaBO empresa, UsuarioEmpresaAltaDto empresaDto);    
    
    @Mapping( target="cuit", ignore = true)
    @Mapping( target="razonSocial", ignore = true)    
    @Mapping( target="domicilios", ignore = true) 
    @Mapping( target="contactos", source ="telefonosAlternativos")
     void mapTelAlt(@MappingTarget EmpresaBO empresaBO, UsuarioEmpresaAltaDto empresa);
    
    @Mapping( target="tipo", constant ="TEL2")
    @Mapping( target="prefijo", source ="prefijo")
    @Mapping( target="valor", source ="nro")
    ContactoBO mapTelAlt(TelefonoDto dato);
    
    /*
    @Mapping( target="valor", source ="dato.nro")
    @Mapping( target="prefijo", source ="dato.prefijo")
    @Mapping( target="tipo", constant ="TEL2")
    //@Named("dtoTelAltToBoContacto")
    ContactoBO mapTelAlt(TelefonoDto dato);
*/
    
    //*****************************************************************
    
    /*
    @Mapping( target="cuit", ignore = true)
    @Mapping( target="ramo", ignore = true)
    @Mapping( target="razonSocial", ignore = true)    
    @Mapping( target="domicilios", ignore = true)
    @Mapping( target="contactos", source ="telefonoAlternativos", qualifiedByName = "dtoTelAltToBoContacto")
     void mapTelAlt(@MappingTarget EmpresaBO empresaBO, UsuarioEmpresaAltaDto empresa);

    @Mapping( target="contactos", source ="telefono", qualifiedByName = "dtoTelToBoContacto")
    @Mapping( target="domicilios", ignore = true)
    @Mapping( target="cuit", ignore = true)
    @Mapping( target="ramo", ignore = true)
    @Mapping( target="razonSocial", ignore = true)    
     void mapTelPpal(@MappingTarget EmpresaBO empresaBO, UsuarioEmpresaAltaDto empresa);    
    

    

    //@Mapping( target="prefijo", source ="prefijo")
    @Mapping( target="valor", source ="dato")
    @Mapping( target="tipo", constant ="TEL")
    @Named("dtoTelToBoContacto")
    ContactoBO dtoTelToBoContacto(String dato);

    
    
    @Mapping( target="valor", source ="dato")
    @Mapping( target="tipo", constant ="MAIL")
    @Named("dtoMailToBoContacto")
    ContactoBO dtoMailToBoContacto(String  dato);
    
    @Mapping( target="valor", source ="dato")
    @Mapping( target="tipo", constant ="MAIL2")
    @Named("dtoMailAltToBoContacto")
    ContactoBO dtoMailAltToBoContacto(String  dato);
    
    @Mapping( target="valor", source ="dato")
    @Mapping( target="tipo", constant ="WHATSAP")
    @Named("dtoWasapToBoContacto")
    ContactoBO dtoWasapToBoContacto(String  dato);
*/
    
    @Named("basicDataFromEmpresa") 
    @Mapping( target="actividad_molinera", source ="actividadMolinera")
    BasicDataEmpresaDto  basicDataFromEmpresa(EmpresaBO empresa);
}
