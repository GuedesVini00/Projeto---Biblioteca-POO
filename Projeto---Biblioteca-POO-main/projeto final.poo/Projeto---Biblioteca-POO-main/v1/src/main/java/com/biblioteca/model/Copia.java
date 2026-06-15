package com.biblioteca.model;

import com.biblioteca.enums.StatusCopia;

public class Copia {

    private int id;
    private String codigoPatrimonio;
    private StatusCopia status;
    private Obra obra;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoPatrimonio() {
        return codigoPatrimonio;
    }

    public void setCodigoPatrimonio(String codigoPatrimonio) {
        this.codigoPatrimonio = codigoPatrimonio;
    }

    public StatusCopia getStatus() {
        return status;
    }

    public void setStatus(StatusCopia status) {
        this.status = status;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    @Override
    public String toString() {

        if (codigoPatrimonio == null) {
            return "Cópia ID: " + id;
        }

        return codigoPatrimonio;
    }

    public boolean estaDisponivel() {
    return status == StatusCopia.DISPONIVEL;
    }

    public boolean estaReservada() {
        return status == StatusCopia.RESERVADO;
    }

    public void reservar() {
        status = StatusCopia.RESERVADO;
    }

    public void cancelarReserva() {
        status = StatusCopia.DISPONIVEL;
    }

    public void emprestar() {
        status = StatusCopia.EMPRESTADO;
    }

    public void devolver() {
        status = StatusCopia.DISPONIVEL;
    }
}