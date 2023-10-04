package com.dgomes.financas.exceptions;

public class RegraNegocioException extends RuntimeException{
    public RegraNegocioException(String msg){ //Criando método personalizado de mensagens de erro
        super(msg); //Chamando o Runtime exception para gerar a mensagem
    }
}
