package com.dgomes.financas.service.impl;

import com.dgomes.financas.exceptions.ErroAutenticacao;
import com.dgomes.financas.exceptions.RegraNegocioException;
import com.dgomes.financas.model.entity.Usuario;
import com.dgomes.financas.model.repositories.UsuarioRepository;
import com.dgomes.financas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service //Pedindo ao SPRING que gere e gerencie uma instancia dessa classe, junto com um container
public class UsuarioServiceImpl implements UsuarioService {
    final private UsuarioRepository repository; // A camada de services não acessa diretamente o BDD

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> user = repository.findByEmail(email);
        if(!user.isPresent()){
            throw new ErroAutenticacao("Esse usuário não existe");
        }
        if(!user.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("Senha inválida!");
        }
        return user.get(); //método .get() me retorna a instancia do usuário
    }

    @Override
    @Transactional //Já que iremos mexer no BDD e alterar o seu estado, deve-se utilizar essa anotation
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if(existe){
            throw new RegraNegocioException("Já existe um usuário com este e-mail!");
        }
    }
}
