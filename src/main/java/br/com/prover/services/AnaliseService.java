package br.com.prover.services;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnaliseService {

    public Map<String, Long> analisar(String frase) {
        if (frase == null || frase.trim().isEmpty()) {
            return java.util.Collections.emptyMap();
        }

        return Arrays.stream(frase.toLowerCase().split("\\s+"))
                .filter(palavra -> !palavra.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
