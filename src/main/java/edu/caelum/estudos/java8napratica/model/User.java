package edu.caelum.estudos.java8napratica.model;

public class User {

    private String nome;
    private Integer pontos;
    private boolean moderador;

    public User(String nome, Integer pontos) {
        this.nome = nome;
        this.pontos = pontos;
    }

    public User(String nome, Integer pontos, boolean moderador) {
        this.nome = nome;
        this.pontos = pontos;
        this.moderador = moderador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public boolean isModerador() {
        return moderador;
    }

    public void setModerador(boolean moderador) {
        this.moderador = moderador;
    }

    public void toModerator(){
        this.setModerador(true);
    }

    @Override
    public String toString() {
        return "User{" +
                "nome='" + nome + '\'' +
                ", pontos=" + pontos +
                ", moderador=" + moderador +
                '}';
    }
}
