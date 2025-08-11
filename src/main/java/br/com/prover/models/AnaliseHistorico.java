package br.com.prover.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "analise_historico")
public class AnaliseHistorico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "frase_original", length = 2000)
    private String fraseOriginal;

    @Lob // Para textos longos, caso o resultado seja grande
    @Column(name = "resultado_json")
    private String resultadoJson;

    @Column(name = "data_analise")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAnalise;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFraseOriginal() {
        return fraseOriginal;
    }

    public void setFraseOriginal(String fraseOriginal) {
        this.fraseOriginal = fraseOriginal;
    }

    public String getResultadoJson() {
        return resultadoJson;
    }

    public void setResultadoJson(String resultadoJson) {
        this.resultadoJson = resultadoJson;
    }

    public Date getDataAnalise() {
        return dataAnalise;
    }

    public void setDataAnalise(Date dataAnalise) {
        this.dataAnalise = dataAnalise;
    }
}