package br.edu.dmos5.projeto_joao_santos_dmos5.model;

import java.math.BigDecimal;
import java.util.Map;

public class MoedaApi {

    private boolean valid;
    private String updated;
    private String base;
    private Map<String, BigDecimal> rates;

    public MoedaApi(boolean valid, String updated, String base, Map<String, BigDecimal> rates) {
        this.valid = valid;
        this.updated = updated;
        this.base = base;
        this.rates = rates;
    }

    public MoedaApi() {
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }
}
