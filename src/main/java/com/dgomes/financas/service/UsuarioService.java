package com.dgomes.financas.service;

import com.dgomes.financas.model.entity.Usuario;

public interface UsuarioService {
    Usuario autenticar(String email, String senha);
    Usuario salvarUsuario(Usuario usuario); //vai receber uma nova instancia de usuário
    void validarEmail(String email);
}
