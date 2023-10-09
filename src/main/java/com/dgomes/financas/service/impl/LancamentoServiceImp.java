package com.dgomes.financas.service.impl;

import com.dgomes.financas.model.entity.Lancamento;
import com.dgomes.financas.model.enums.StatusLancamento;
import com.dgomes.financas.model.repositories.LancamentoRepository;
import com.dgomes.financas.service.LancamentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class LancamentoServiceImp implements LancamentoService {
    private final LancamentoRepository repository;

    public LancamentoServiceImp(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        return repository.save(lancamento);
    }

    @Override
    public void deletar(Lancamento lancamento) {

    }

    @Override
    public List<Lancamento> buscar(Lancamento lancamentoBusca) {
        return null;
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {

    }
}
