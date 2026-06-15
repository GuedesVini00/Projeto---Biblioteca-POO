package com.biblioteca.exceptions;

public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem){
        super(mensagem);
    }
}