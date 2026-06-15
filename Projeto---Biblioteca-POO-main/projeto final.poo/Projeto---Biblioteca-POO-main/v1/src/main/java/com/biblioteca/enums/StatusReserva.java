package com.biblioteca.enums;

public enum StatusReserva {
    ATIVA,
    CANCELADA,
    FINALIZADA;

    private StatusReserva status;

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }
}


