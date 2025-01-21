package ar.ospim.empleadores.auth.oauth.infra.output;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import ar.ospim.empleadores.auth.oauth.dominio.OAuthUserInfoBo;

@Mapper
public interface OAuthUserMapper { 
   
    @Named("fromOAuthUserInfoDto")
    @Mapping(source = "preferred_username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "given_name", target = "firstName")
    @Mapping(source = "family_name", target = "lastName")
    OAuthUserInfoBo fromOAuthUserInfoDto(OAuthUserInfoDto oAuthUserInfoDto);

}