package com.dgomes.financas.model.entity;

import com.dgomes.financas.model.enums.StatusLancamento;
import com.dgomes.financas.model.enums.TipoLancamento;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento", schema = "financas")
@Data
public class Lancamento { //fazendo sem @Column
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;
    private Integer mes;
    private Integer ano;
    @ManyToOne //Indicando que um usuário pode ter vários lançamentos
    @JoinColumn(name = "id_usuario") //indicando qual coluna do banco atual terá essa dependência
    private Usuario usuario; //referenciando o objeto
    private BigDecimal valor;
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class) //necessário para traduzir o localdate do java para um formato aceito pelo BD
    private LocalDate data_cadastro;
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;


}
