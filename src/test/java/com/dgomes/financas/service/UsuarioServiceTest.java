package com.dgomes.financas.service;

import com.dgomes.financas.exceptions.RegraNegocioException;
import com.dgomes.financas.model.repositories.UsuarioRepository;
import com.dgomes.financas.service.impl.UsuarioServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UsuarioServiceTest{

    UsuarioService service;
    UsuarioRepository repository;
    @Before
    public void mkt(){ // utilizando o Mockito para simular comportamentos de chamadas
        repository = Mockito.mock(UsuarioRepository.class);
        service = new UsuarioServiceImpl(repository); //necessário criar a instancia de mock a ser testada

    }

    @Test(expected = Test.None.class) //declarando que não espera nenhuma exception
    public void testeValidandoEmailExistente(){
        //cenário
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

        //ação
        service.validarEmail("user@gmail.com");

    }
    @Test(expected = RegraNegocioException.class)
    public void testeLancarErroValidandoEmail(){
        //cenário
//        Usuario user = new Usuario();
//        user.setEmail("user@gmail.com");
//        user.setNome("user");
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);


        //ação
        service.validarEmail("user@gmail.com");
    }
    //TODO: Criar teste do método autenticar
}
