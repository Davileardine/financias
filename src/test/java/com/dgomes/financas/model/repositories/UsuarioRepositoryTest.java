package com.dgomes.financas.model.repositories;

import com.dgomes.financas.model.entity.Usuario;
import com.dgomes.financas.service.UsuarioService;
import com.dgomes.financas.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test") //declarando que o application é o test
//@DataJpaTest -> desfaz transações que foram feitas durante o teste

//Recomendado usar anotations acima, pois além de ser em um banco de dados exclusivo para teste, evita de ter erros em checagem e uso de repository.deleteAll()

@SpringBootTest // -> essa anotation não é necessária, além de carregar várias funcionalidades. Quando se está utilizadando testes em memória
@RunWith(SpringRunner.class)
public class UsuarioRepositoryTest {

    //Por serem testes unitários, não há necessidade de @AUTOWIRED
    @Autowired
    UsuarioRepository repository;
    @Autowired
    UsuarioService usuarioService;

    //@Autowired    - utilizado para injetar a instancia original
    // TestEntityManager entityManager; --> o entity manager é o responsável por ações no banco, nela está uma instancia também do UsuarioRepository, porém para testes, o ideal
    // é utilizar a entidade de testes, não o repository direto
    public static Usuario criandoUser() {
        Usuario user = new Usuario();
        user.setEmail("user@gmail.com");
        user.setNome("user");

        return user;
    }

    @Test
    public void testeEmailExistente() {
        //cenário
        var user = criandoUser();

        repository.save(user);   // era -> repository.save(user);

        //ação/execução
        boolean result = repository.existsByEmail("user@gmail.com");

        //verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testeEmailNaoExiste() {
        //cenário
        repository.deleteAll();

        //ação/execução
        boolean result = repository.existsByEmail("user@gmail.com");

        //verificação
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testeVerificarUsuarioEmBaseDeDados() {

        //cenário
        var user = criandoUser();

        //ação/execução
        repository.save(user);

        //verificação
        Assertions.assertThat(user.getId()).isNotNull();
    }

    @Test
    public void testeBuscarUserPorEmail() {
        //cenário
        repository.deleteAll();
        var user = criandoUser();
//        Usuario user = new Usuario();
//        user.setEmail("user@gmail.com");
//        user.setNome("user");
        repository.save(user);

        //ação/execução
        Optional<Usuario> result = repository.findByEmail("user@gmail.com");

        //verificação
        Assertions.assertThat(result.isPresent()).isTrue(); // método isPresent é utilizado para verificar a presença de um optional
    }

    @Test
    public void testeRetornarVazioAoBuscarEmailNaoExistente() {
        //cenário
        repository.deleteAll();

        //ação/execução
        Optional<Usuario> result = repository.findByEmail("user@gmail.com");

        //verificação
        Assertions.assertThat(result.isPresent()).isFalse();
    }
}
