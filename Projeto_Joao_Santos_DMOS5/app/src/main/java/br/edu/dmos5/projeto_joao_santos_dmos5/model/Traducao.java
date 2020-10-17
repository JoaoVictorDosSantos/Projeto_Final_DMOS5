package br.edu.dmos5.projeto_joao_santos_dmos5.model;

public class Traducao {

    private String chave;
    private String valor;
    private String traducao;

    public Traducao(String chave, String valor, String traducao){
        this.chave = chave;
        this.valor = valor;
        this.traducao = traducao;
    }

    public String getTraducao() {
        return traducao;
    }

    public void setTraducao(String traducao) {
        this.traducao = traducao;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
