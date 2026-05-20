package com.biblioteca.model;

public class Obra {
    private int id;
    private String titulo;
    private String autor;
    private String dataPublicacao;
    private String categoria;
    private String tipo;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }
    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }    

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Obra(){

    }
    
    @Override
    public String toString() {
        return "titulo= " + titulo + '\n' + "autor= " + autor + '\n' +"Data da Publicação= " + dataPublicacao + '\n' + "categoria= " + categoria + '\n' +"tipo= " + tipo;
    }
    
    public boolean livro(){
        return tipo.equalsIgnoreCase("Livro");
    }

    public boolean revista(){
        return tipo.equalsIgnoreCase("Revista");
    }

    public boolean hq(){
        return tipo.equalsIgnoreCase("hq");
    }

    public String descrição(){
        return getTitulo()+getAutor();
    }
    
}


