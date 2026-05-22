package com.biblioteca.model;

public class Leitor extends Pessoa {

    @Override
    public String toString() {

        if (getNome() == null) {
            return "Leitor ID: " + getId();
        }

        return getNome();
    }
}