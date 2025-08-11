package br.com.prover.views;

import br.com.prover.services.AnaliseService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("analiseBean")
@ViewScoped
public class AnaliseBean implements Serializable {

    @Inject
    private AnaliseService analiseService;

    @Inject
    private HistoricoSessaoBean historicoSessaoBean;

    @Inject
    private JMSContext jmsContext;

    @PostConstruct
    public void init() {
        frase = "";
        listaDeResultados = new ArrayList<>();
        submetido = false;
    }

    @Resource(lookup = "java:jboss/jms/queue/AnaliseFraseQueue")
    private Queue queue;
    private String frase;
    private List<Map.Entry<String, Long>> listaDeResultados;
    private List<Map.Entry<String, Long>> resultadoFiltrado;
    private boolean submetido = false;

    public void analisarFrase() {

        Map<String, Long> mapaResultados = analiseService.analisar(this.frase);

        if (mapaResultados != null && !mapaResultados.isEmpty()) {
            this.listaDeResultados = new ArrayList<>(mapaResultados.entrySet());

            historicoSessaoBean.adicionarAoHistorico(this.frase, mapaResultados);

        } else {
            this.listaDeResultados = new ArrayList<>();
        }

        this.resultadoFiltrado = null;
        this.submetido = true;

        int tamanhoAtual = historicoSessaoBean.getHistorico().size();
    }

    public void submeterParaAnaliseAssincrona() {
        if(this.frase != null && !this.frase.trim().isEmpty()) {
            jmsContext.createProducer().send(queue, this.frase);
            this.submetido = true;
            this.listaDeResultados = null;
        }
    }
    public List<Map.Entry<String, Long>> getListaDeResultados() {
        return listaDeResultados;
    }

    public void setListaDeResultados(List<Map.Entry<String, Long>> listaDeResultados) {
        this.listaDeResultados = listaDeResultados;
    }

    public List<Map.Entry<String, Long>> getResultadoFiltrado() {
        return resultadoFiltrado;
    }

    public void setResultadoFiltrado(List<Map.Entry<String, Long>> resultadoFiltrado) {
        this.resultadoFiltrado = resultadoFiltrado;
    }

    public int getContagemPalavrasDistintas() {
        return listaDeResultados == null ? 0 : listaDeResultados.size();
    }

    public String getFrase() {
        return frase;
    }
    public void setFrase(String frase) {
        this.frase = frase;
    }
    public boolean isSubmetido() {
        return submetido;
    }
}