package com.biblioteca.model;

public class Emprestimo {
    private int id;
    private String dataSaida;
    private String dataDevolucao;
    private Copia copia;
    private Leitor leitor;
    private Funcionario funcionario;
    private String status;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDataSaida() {
        return dataSaida;
    }
    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }
    public String getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    public Copia getCopia() {
        return copia;
    }
    public void setCopia(Copia copia) {
        this.copia = copia;
    }
    public Leitor getLeitor() {
        return leitor;
    }
    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }
    public Funcionario getFuncionario() {
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return leitor.getNome() + " - " + copia.getCodigoPatrimonio();
    }

    public boolean estaAtivo(){
        return status.equalsIgnoreCase("ATIVO");
    }

     public boolean estaFinalizado(){
        return status.equalsIgnoreCase("FINALIZADO");
    }

    public void ativar(){
        status = "ATIVO";
    }

    public void finalizar(){
        status = "FINALIZADO";
    }
}