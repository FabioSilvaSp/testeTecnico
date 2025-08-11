package br.com.prover.mdb;

import br.com.prover.models.AnaliseHistorico;
import br.com.prover.services.AnaliseService;
import com.google.gson.Gson;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Map;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:jboss/jms/queue/AnaliseFraseQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class AnaliseMDB implements MessageListener {

    @Inject
    private AnaliseService analiseService;

    @PersistenceContext(unitName = "analisadorPU")
    private EntityManager em;

    private final Gson gson = new Gson();

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                Thread.sleep(3000); // 3 segundos

                TextMessage textMessage = (TextMessage) message;
                String frase = textMessage.getText();

                Map<String, Long> resultado = analiseService.analisar(frase);

                AnaliseHistorico historico = new AnaliseHistorico();
                historico.setFraseOriginal(frase);
                historico.setResultadoJson(gson.toJson(resultado));
                historico.setDataAnalise(new Date());

                em.persist(historico);

                System.out.println("Análise da frase concluída e salva no histórico.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}