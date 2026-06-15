package com.biblioteca.exceptions;

public class EntidadeNaoEncontradaException extends RegraNegocioException{
    
    public EntidadeNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
