package com.dgomes.financas.model.repositories;

import com.dgomes.financas.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

//Responsável por operações na entidade Lançamento
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    Optional<Lancamento> findById(Long id);
    @Query( value = " select sum(l.valor) from Lancamento l join l.usuario u" +
            " where u.id = :idUsuario and l.tipo = :tipo" +
            "group by u") //indicando que a somatoria é por usuario
    BigDecimal obterSaldoPorUsuario(@Param("idUsuario")Long idUsuario,@Param("tipo") String tipo);
}
