package com.dgomes.financas.api.resource;

import com.dgomes.financas.api.dto.AtualizaStatusDTO;
import com.dgomes.financas.api.dto.LancamentoDTO;
import com.dgomes.financas.api.dto.LancamentoResponseDTO;
import com.dgomes.financas.exceptions.RegraNegocioException;
import com.dgomes.financas.model.entity.Lancamento;
import com.dgomes.financas.model.entity.Usuario;
import com.dgomes.financas.model.enums.StatusLancamento;
import com.dgomes.financas.model.enums.TipoLancamento;

import com.dgomes.financas.service.LancamentoService;
import com.dgomes.financas.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private final LancamentoService service;
    private final UsuarioService usuarioService;

    public LancamentoController(LancamentoService service, UsuarioService usuarioService) {
        this.service = service;
        this.usuarioService = usuarioService;


    }

    private Lancamento converter(LancamentoDTO dto) { //convertendo DTO -> entidade
        Usuario usuario = usuarioService.buscarId(dto.usuario()).orElseThrow(() -> new RegraNegocioException("Por favor, insira um usuário válido!"));

        Lancamento lancamento = Lancamento.builder()
                .usuario(usuario)
                .valor(dto.valor())
                .descricao(dto.descricao())
                .ano(dto.ano())
                .mes(dto.mes())
                .build();

        if(dto.tipo() != null){
            lancamento.setTipo(TipoLancamento.valueOf(dto.tipo()));
        }
        if(dto.status() != null) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.status()));
        }
        return lancamento;
    }

    @PostMapping("salvar")
    public ResponseEntity salvarLancamento(@RequestBody LancamentoDTO dto) {
        try {
            Lancamento lancamento = converter(dto);
            lancamento = service.salvar(lancamento);
            return new ResponseEntity(lancamento, HttpStatus.CREATED); //TODO: mapear o retorno para não ter dados sensíveis

        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizarLancamento(@PathVariable("id") Long id, // -> /api/lancamentos/id
                                              @RequestBody LancamentoDTO dto) {
        return service.buscarId(id).map(entidadeComId -> {
            try {
                Lancamento lancamento = converter(dto);
                lancamento.setId(entidadeComId.getId());
                service.atualizar(lancamento);
                return ResponseEntity.ok(lancamento);
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Lançamento inválido!", HttpStatus.BAD_REQUEST));

    }
    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizaStatus(@PathVariable("id") Long id,
                                        AtualizaStatusDTO dto){
        return service.buscarId(id).map(entidadeComId -> {
            StatusLancamento atualizado = StatusLancamento.valueOf(dto.status());
            if(atualizado == null){
                return ResponseEntity.badRequest().body("Favor insira um status válido");
            }
            try{
                service.atualizarStatus(entidadeComId,atualizado);
                return ResponseEntity.ok(entidadeComId);
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Lançamento inválido!", HttpStatus.BAD_REQUEST));
    }
    @GetMapping
    public ResponseEntity<?> buscarLancamento(
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "usuario") Long usuarioId
    ) {
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);
        lancamentoFiltro.setDescricao(descricao);

        Optional<Usuario> usuario = usuarioService.buscarId(usuarioId);

        if (usuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        lancamentoFiltro.setUsuario(usuario.get());

        List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);

        if (lancamentos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Nenhum lançamento encontrado para os parâmetros fornecidos.");
        }

        return ResponseEntity.ok(lancamentos);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletarLancamento(@PathVariable("id") Long id) {
        return service.buscarId(id).map(entidadeComId -> {
            try {
                service.deletar(entidadeComId);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado!", HttpStatus.BAD_REQUEST));
    }
}