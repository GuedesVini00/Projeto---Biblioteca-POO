package com.biblioteca.model;
import com.enums.StatusReserva;

public class Reserva {
    private int id;
    private String dataReserva;
    private Obra obra;
    private Leitor leitor;
    private StatusReserva status;

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
    public StatusReserva getStatus() {
        return status;
    }
    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public boolean estaAtiva(){
        return status == StatusReserva.ATIVA;
    }

    public boolean estaCancelada(){
        return status == StatusReserva.CANCELADA;
    }

    public void cancelar(){
        status = StatusReserva.CANCELADA;
    }

    public void finalizar(){
        status = StatusReserva.FINALIZADA;
    }
}
