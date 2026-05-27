package com.biblioteca.service;

public class LoginService {

    public boolean autenticar(String usuario, String senha) {

        return usuario.equals("admin") && senha.equals("1234");
    }
}
