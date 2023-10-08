package com.dgomes.financas.model.entity;

import com.dgomes.financas.api.dto.UsuarioResponseDTO;
import jakarta.persistence.*;
import lombok.*;

//fazendo a classe sem lombok para aprendizado
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

//    public Usuario(UsuarioResponseDTO data){
//        this.nome = data.nome();
//        this.email = data.email();
//        this.senha = data.senha();
//    }

}

