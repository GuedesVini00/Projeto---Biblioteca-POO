package com.biblioteca.model;

public class Funcionario extends Pessoa {

    private String cargo;

    public String getCargo() {

        return cargo;
    }

    public void setCargo(String cargo) {

        this.cargo = cargo;
    }

    @Override
    public String toString() {

        if (getNome() == null) {
            return "Funcionário ID: " + getId();
        }

        return getNome();
    }
}
