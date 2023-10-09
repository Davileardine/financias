package com.dgomes.financas.service;

import com.dgomes.financas.model.entity.Usuario;
import org.springframework.stereotype.Service;


public interface UsuarioService {
    Usuario autenticar(String email, String senha);
    Usuario salvarUsuario(Usuario usuario); //vai receber uma nova instancia de usuário
    void validarEmail(String email);

    //TODO: Criar validação de campos de salvar usuário
}
