package com.biblioteca.model;

public class Reserva {
    private int id;
    private String dataReserva;
    private Obra obra;
    private Leitor leitor;
    private String status;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDataReserva() {
        return dataReserva;
    }
    public void setDataReserva(String dataReserva) {
        this.dataReserva = dataReserva;
    }
    public Obra getObra() {
        return obra;
    }
    public void setObra(Obra obra) {
        this.obra = obra;
    }
    public Leitor getLeitor() {
        return leitor;
    }
    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public boolean estaAtiva(){
        return status.equalsIgnoreCase("Ativa");
    }

    public boolean estaCancelada(){
        return status.equalsIgnoreCase("Cancelada");
    }

    public void cancelar(){
        status = "Cancelada";
    }

    public void finalizar(){
        status = "Finalizada";
    }
}
