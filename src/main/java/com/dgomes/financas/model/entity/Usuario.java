package com.dgomes.financas.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;

@Entity //Defino que essa classe representa uma entidade no banco de dados
@Table(name = "usuario", schema = "financas")
@Data //Define todos os métodos padrão para operações
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Column @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    private String senha;
    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class) //necessário para traduzir o localdate do java para um formato aceito pelo BD
    private LocalDate data_cadastro;

}

