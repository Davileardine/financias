package com.dgomes.financas.service.impl;

import com.dgomes.financas.exceptions.ErroAutenticacao;
import com.dgomes.financas.exceptions.RegraNegocioException;
import com.dgomes.financas.model.entity.Usuario;
import com.dgomes.financas.model.repositories.UsuarioRepository;
import com.dgomes.financas.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        Optional<Usuario> usuario = repository.findByEmail(email);
        if(usuario.isEmpty()){
            throw new ErroAutenticacao("Esse usuário não existe");
        }
        if(!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("Senha inválida!");
        }
        return usuario.get(); //método .get() me retorna a instancia do usuário
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if(existe){
            throw new RegraNegocioException("Já existe um usuário com este e-mail!");
        }
    }

    @Override
    @Transactional //Já que iremos mexer no BDD e alterar o seu estado, deve-se utilizar essa anotation
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        usuario.setData_cadastro(LocalDate.now()); // setando a data já na chamada da função
        return repository.save(usuario);
    }
}
