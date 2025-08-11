package br.com.prover.views;

import br.com.prover.models.HistoricoItem;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class HistoricoSessaoBean implements Serializable {

    private final List<HistoricoItem> historico = new ArrayList<>();

    public void adicionarAoHistorico(String frase, Map<String, Long> resultado) {
        if (frase != null && !frase.trim().isEmpty() && resultado != null && !resultado.isEmpty()) {
            historico.add(0, new HistoricoItem(frase, resultado));
        }
    }

    public List<HistoricoItem> getHistorico() {
        return historico;
    }
}