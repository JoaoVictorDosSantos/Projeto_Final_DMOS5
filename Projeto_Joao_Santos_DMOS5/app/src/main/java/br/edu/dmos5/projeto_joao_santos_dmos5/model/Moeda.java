package br.edu.dmos5.projeto_joao_santos_dmos5.model;


import java.math.BigDecimal;
import java.util.Date;

import br.edu.dmos5.projeto_joao_santos_dmos5.util.DataUtil;

public class Moeda {

    private Long id;
    private String sigla;
    private String nome;
    private String traducao;
    private BigDecimal cotacao;
    private Date dataCadastramento;

    public Moeda(Long id, String sigla, String nome, String traducao, BigDecimal cotacao, Date dataCadastramento) {
        this.id = id;
        this.sigla = sigla;
        this.nome = nome;
        this.traducao = traducao;
        this.cotacao = cotacao;
        this.dataCadastramento = dataCadastramento;
    }

    public Moeda(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTraducao() {
        return traducao;
    }

    public void setTraducao(String traducao) {
        this.traducao = traducao;
    }

    public BigDecimal getCotacao() {
        return cotacao;
    }

    public void setCotacao(BigDecimal cotacao) {
        this.cotacao = cotacao;
    }

    public void setDataCadastramento(Date dataCadastramento) {
        this.dataCadastramento = dataCadastramento;
    }

    public Date getDataCadastramento() {
        return dataCadastramento;
    }

    public String getDateToString(){
        if(dataCadastramento != null){
            return DataUtil.converteDateToString(dataCadastramento);
        }
        return "";
    }
}
