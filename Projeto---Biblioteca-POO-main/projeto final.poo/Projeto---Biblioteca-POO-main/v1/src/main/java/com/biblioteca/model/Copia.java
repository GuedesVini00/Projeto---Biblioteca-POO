package com.biblioteca.model;

public class Copia {

    private int id;
    private String codigoPatrimonio;
    private String status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

     public boolean estaDisponivel(){
        return status.equalsIgnoreCase("Disponivel");
    }

    public boolean estaReservada(){
        return status.equalsIgnoreCase("Reservado");
    }

    public void reservar(){
        status = "Reservado";
    }

    public void cancelarReserva(){
        status = "Disponivel";
    }

    public void emprestar(){
        status = "Emprestado";
    }

    public void devolver(){
        status = "Disponivel";
    }

}