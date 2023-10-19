package com.dgomes.financas.service;

import com.dgomes.financas.model.entity.Lancamento;
import com.dgomes.financas.model.enums.StatusLancamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LancamentoService {
    Lancamento salvar(Lancamento lancamento);
    Lancamento atualizar (Lancamento lancamento); //precisa receber um lançamento já com ID
    void deletar(Lancamento lancamento); //void pois não retorna nada, recebe o objeto a ser deletado
    List<Lancamento> buscar(Lancamento lancamentoBusca);
    void atualizarStatus(Lancamento lancamento, StatusLancamento status);
    void validarLancamento(Lancamento lancamento);

    Optional<Lancamento> buscarId(Long id);

    BigDecimal obterSaldoPorUsuario(Long idUsuario);
}
