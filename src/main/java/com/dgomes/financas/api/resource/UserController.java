package com.dgomes.financas.api.resource;

import com.dgomes.financas.api.dto.UsuarioAutenticacaoDTO;
import com.dgomes.financas.api.dto.UsuarioResponseDTO;
import com.dgomes.financas.exceptions.ErroAutenticacao;
import com.dgomes.financas.exceptions.RegraNegocioException;
import com.dgomes.financas.model.entity.Usuario;
import com.dgomes.financas.service.LancamentoService;
import com.dgomes.financas.service.UsuarioService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    private final UsuarioService service;
    private final LancamentoService lancamentoService;
    private UserController(UsuarioService service, LancamentoService lancamentoService){
        this.service = service;
        this.lancamentoService = lancamentoService;
    }
    @PostMapping
    public ResponseEntity saveUser(@RequestBody @NotNull UsuarioResponseDTO dto){
        Usuario user = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha())
                .build(); // data de criação sendo salva na função

        try {
            Usuario usuarioSalvo = service.salvarUsuario(user);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);

        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody @NotNull UsuarioAutenticacaoDTO dto){
       try{
           Usuario usuarioAutenticado = service.autenticar(dto.email(), dto.senha());
           return ResponseEntity.ok(usuarioAutenticado);
       } catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
    @GetMapping("{id}/saldo")
    public ResponseEntity buscarSaldo(@PathVariable("id") Long id){
        Optional<Usuario> usuario = service.buscarId(id);
        if (!usuario.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }
}
