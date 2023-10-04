package com.dgomes.financas.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
//fazendo a classe sem lombok para aprendizado
@Entity //Defino que essa classe representa uma entidade no banco de dados
@Table(name = "usuario", schema = "financas")
@Data //Define todos os métodos padrão para operações
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Column @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    private String senha;


}

