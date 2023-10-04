package com.dgomes.financas.model.repositories;

import com.dgomes.financas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Responsável por operações na entidade Usuário
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email); // --> Utilizando Query Method do Spring data "find by..."
}
