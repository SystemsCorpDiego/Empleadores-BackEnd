package ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ospim.empleadores.comun.infra.output.dto.IdGeneradoDto;
import ar.ospim.empleadores.nuevo.app.dominio.ContactoBO;
import ar.ospim.empleadores.nuevo.app.dominio.EmpresaBO;
import ar.ospim.empleadores.nuevo.app.servicios.usuario.empresa.CrearUsuarioEmpresa;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.usuarioempresaalta.UsuarioEmpresaAltaDto;
import ar.ospim.empleadores.nuevo.infra.input.rest.auth.usuario.dto.usuarioempresaalta.UsuarioEmpresaAltaDtoMapper;

@RestController
@RequestMapping("usuario/empresa")
public class UsuarioEmpresaController {
    private static final Logger LOG = LoggerFactory.getLogger(UsuarioEmpresaController.class);
    public static final String OUTPUT = "Output -> {}";

    private final CrearUsuarioEmpresa crearUsuarioEmpresa;
    private final UsuarioEmpresaAltaDtoMapper mapper;

    
    public UsuarioEmpresaController(CrearUsuarioEmpresa crearUsuarioEmpresa, 
    		UsuarioEmpresaAltaDtoMapper empresaMapper) {
        super();
        this.crearUsuarioEmpresa = crearUsuarioEmpresa;
        this.mapper = empresaMapper; 
    }
    
    
    @PostMapping(value={"/", "/public/"} )
    @Transactional
    public ResponseEntity<IdGeneradoDto> crearUsuarioEmpresa (
            @RequestBody @Valid UsuarioEmpresaAltaDto usuarioEmpresaAltaDto) throws URISyntaxException {
        LOG.debug("Crear empresa con usuario-> {}", usuarioEmpresaAltaDto);
 
        EmpresaBO empresaNueva = mapper.map(usuarioEmpresaAltaDto);
        mapperAux(empresaNueva, usuarioEmpresaAltaDto);
        
        EmpresaBO empresaCreada = crearUsuarioEmpresa.run(empresaNueva, usuarioEmpresaAltaDto.getClave() );
        
        
        LOG.debug("Empresa creada -> {}", empresaCreada);
        return ResponseEntity.created(new URI("")).body(new IdGeneradoDto(empresaCreada.getId()) );
    }
    
    private void mapperAux(EmpresaBO empresaNueva, UsuarioEmpresaAltaDto usuarioEmpresaAltaDto) {    	
    	mapper.mapWhatsap(empresaNueva, usuarioEmpresaAltaDto);
    	mapper.mapMail(empresaNueva, usuarioEmpresaAltaDto);        
    	mapper.mapTelAlt(empresaNueva, usuarioEmpresaAltaDto);
    	
    	ContactoBO contacto = null;
    	if ( empresaNueva.getContactos() == null && usuarioEmpresaAltaDto.getEmailAlternativos() != null 
    			&& usuarioEmpresaAltaDto.getEmailAlternativos().size() > 0) {
    		empresaNueva.setContactos( new ArrayList<ContactoBO>());
    	}
    	
    	if ( usuarioEmpresaAltaDto.getEmailAlternativos() != null ) {
	    	for ( String mailAlt: usuarioEmpresaAltaDto.getEmailAlternativos()) {
	    		contacto = new ContactoBO();
	    		contacto.setTipo("MAIL2");
	    		contacto.setValor(mailAlt);
	    		empresaNueva.getContactos().add(contacto);
	    	}
    	}
    }
    
}
