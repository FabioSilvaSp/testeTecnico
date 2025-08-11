package br.com.prover.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HistoricoItem implements Serializable {

    private final Date dataAnalise;
    private final String fraseOriginal;
    private final Map<String, Long> resultado;

    public HistoricoItem(String fraseOriginal, Map<String, Long> resultado) {
        this.dataAnalise = new Date();
        this.fraseOriginal = fraseOriginal;
        this.resultado = resultado;
    }

    // Getters
    public Date getDataAnalise() {
        return dataAnalise;
    }

    public String getFraseOriginal() {
        return fraseOriginal;
    }

    public Map<String, Long> getResultado() {
        return resultado;
    }

    public int getContagemPalavrasDistintas() {
        return resultado != null ? resultado.size() : 0;
    }

    public List<Map.Entry<String, Long>> getResultadoAsList() {
        return resultado == null ? new ArrayList<>() : new ArrayList<>(resultado.entrySet());
    }
}