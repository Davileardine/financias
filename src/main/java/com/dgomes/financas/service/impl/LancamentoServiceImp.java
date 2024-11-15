package com.dgomes.financas.service.impl;

import com.dgomes.financas.exceptions.RegraNegocioException;
import com.dgomes.financas.model.entity.Lancamento;
import com.dgomes.financas.model.entity.Usuario;
import com.dgomes.financas.model.enums.StatusLancamento;
import com.dgomes.financas.model.enums.TipoLancamento;
import com.dgomes.financas.model.repositories.LancamentoRepository;
import com.dgomes.financas.service.LancamentoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoServiceImp implements LancamentoService {
    Year anoAtual = Year.now();
    private final LancamentoRepository repository;

    public LancamentoServiceImp(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validarLancamento(@NotNull Lancamento lancamento) {
        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().isEmpty()){
            throw new RegraNegocioException("Insira uma descrição válida!");
        }
        if (lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12 ){
            throw new RegraNegocioException("Insira um mês válido!");
        }
        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4 || !lancamento.getAno().toString().equals(anoAtual.toString())){
            throw new RegraNegocioException("Insira um ano válido!");
        }
        if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null){
            throw new RegraNegocioException("Insira um usuário!");
        }
        if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1){
            throw new RegraNegocioException("Insira um valor válido!");
        }
        if (lancamento.getTipo() == null){
            throw new RegraNegocioException("Insira um tipo de lançamento!");
        }
    }


    @Override
    public Optional<Lancamento> buscarId(Long id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new RegraNegocioException("Esse lançamento não existe")));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldoPorUsuario(Long idUsuario) {
        BigDecimal receita = repository.obterSaldoPorUsuario(idUsuario, TipoLancamento.RECEITA);
        BigDecimal despesa = repository.obterSaldoPorUsuario(idUsuario, TipoLancamento.DESPESA);

        if(receita == null){
            receita = BigDecimal.ZERO;
        }
        if(despesa == null){
            receita = BigDecimal.ZERO;
        }

        return receita.subtract(despesa);
    }


    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validarLancamento(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        lancamento.setData_cadastro(LocalDate.now());
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        validarLancamento(lancamento);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        repository.delete(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoBusca) {

        Example<Lancamento> ex = Example.of(lancamentoBusca, ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(ex);
    }


    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);

    }
}
