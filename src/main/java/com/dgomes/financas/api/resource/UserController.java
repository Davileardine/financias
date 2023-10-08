package com.dgomes.financas.api.resource;

import com.dgomes.financas.api.dto.UsuarioAutenticacaoDTO;
import com.dgomes.financas.api.dto.UsuarioResponseDTO;
import com.dgomes.financas.exceptions.ErroAutenticacao;
import com.dgomes.financas.exceptions.RegraNegocioException;
import com.dgomes.financas.model.entity.Usuario;
import com.dgomes.financas.model.repositories.UsuarioRepository;
import com.dgomes.financas.service.UsuarioService;
import jdk.javadoc.doclet.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    private UsuarioRepository repository;
    private UsuarioService service;
    private UserController(UsuarioService service){
        this.service = service;
    }
    @PostMapping
    public ResponseEntity saveUser(@RequestBody UsuarioResponseDTO dto){
        Usuario user = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha())
                .build();

        try {
            Usuario usuarioSalvo = service.salvarUsuario(user);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);

        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioAutenticacaoDTO dto){
       try{
           Usuario usuarioAutenticado = service.autenticar(dto.email(), dto.senha());
           return new ResponseEntity(usuarioAutenticado, HttpStatus.ACCEPTED);
       } catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
