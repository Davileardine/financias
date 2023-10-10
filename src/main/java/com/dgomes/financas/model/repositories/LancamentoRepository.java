package com.dgomes.financas.model.repositories;

import com.dgomes.financas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Responsável por operações na entidade Lançamento
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    Optional<Lancamento> findById(Long id);
}
