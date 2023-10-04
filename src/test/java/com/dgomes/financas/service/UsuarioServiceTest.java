package com.dgomes.financas.service;

import com.dgomes.financas.exceptions.RegraNegocioException;
import com.dgomes.financas.model.entity.Usuario;
import com.dgomes.financas.model.repositories.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioServiceTest{
    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepository repository;

    @Test(expected = Test.None.class) //declarando que não espera nenhuma exception
    public void testeValidandoEmailExistente(){
        //cenário
        repository.deleteAll();

        //ação
        service.validarEmail("user@gmail.com");

    }
    @Test(expected = RegraNegocioException.class)
    public void testeLancarErroValidandoEmail(){
        //cenário
        Usuario user = new Usuario();
        user.setEmail("user@gmail.com");
        user.setNome("user");

        repository.save(user);

        //ação
        service.validarEmail("user@gmail.com");
    }
}
