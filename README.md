# Analisador de Frases - Projeto de Avaliação Técnica

Este projeto é uma aplicação web Java EE desenvolvida como solução para um desafio técnico para uma posição de Desenvolvedor Java Sênior. O objetivo é demonstrar proficiência em tecnologias do ecossistema Java, boas práticas de arquitetura de software, e a construção de uma aplicação web funcional, robusta e com uma interface de usuário moderna.

## Objetivo da Aplicação

O sistema permite que um usuário insira uma frase ou um texto qualquer e, ao submeter para análise, recebe como resposta a quantidade total de palavras distintas e uma lista detalhada com cada palavra e o número de suas ocorrências no texto.

---

## Tecnologias Utilizadas (Stack)

* **Linguagem:** Java 8 (com uso de Lambdas e Streams API)
* **Servidor de Aplicação:** WildFly 10
* **Frontend:**
    * JavaServer Faces (JSF) 2.2
    * PrimeFaces 8.0 (para componentes ricos e AJAX)
* **Backend & Persistência:**
    * Contexts and Dependency Injection (CDI) 1.2
    * Enterprise JavaBeans (EJB - para MDB)
    * Java Persistence API (JPA) 2.1 com Hibernate
    * Java Message Service (JMS) 2.0 (para processamento assíncrono)
* **Build:** Apache Maven

---

## Arquitetura da Solução

A aplicação foi desenhada com uma arquitetura de múltiplas camadas, focando em desacoplamento e responsabilidades bem definidas, como esperado de um sistema de nível sênior.

1.  **View (Frontend):** Construída com **JSF e PrimeFaces**, a interface é responsável por capturar a entrada do usuário e exibir os resultados de forma interativa. Componentes AJAX são usados extensivamente para criar uma experiência de usuário fluida, sem a necessidade de recarregar a página.

2.  **Controller (Backing Beans):**
    * **`AnaliseBean` (`@ViewScoped`):** Controla a tela principal de análise, gerenciando o estado da view (o texto inserido, a lista de resultados atual) enquanto o usuário interage com a página (filtrando, paginando, etc.).
    * **`HistoricoSessaoBean` (`@SessionScoped`):** Demonstra o uso de escopos diferentes. Este bean é responsável por manter um histórico de todas as análises realizadas *apenas durante a sessão do usuário*, sem persistência em banco de dados.

3.  **Camada de Serviço (`AnaliseService`):** Contém a lógica de negócio pura para analisar o texto. É um bean `@ApplicationScoped` que pode ser injetado em qualquer parte do sistema.

4.  **Processamento Assíncrono (JMS e MDB):** Para demonstrar conhecimento de arquiteturas mais complexas e escaláveis, o sistema conta com um fluxo de análise assíncrono (não utilizado na tela principal, mas disponível). A ideia é que uma análise possa ser enviada para uma fila **JMS**, e um **Message-Driven Bean (`AnaliseMDB`)** a processe em background, persistindo o resultado no banco de dados para auditoria. Isso atende ao requisito de processar "uma requisição por vez" de forma elegante.

5.  **Persistência (JPA):** A entidade `AnaliseHistorico` e a configuração do `persistence.xml` demonstram a capacidade de mapear objetos para um banco de dados relacional e persistir informações para fins de log ou auditoria a longo prazo.

---

## Funcionalidades Implementadas

* Análise de texto para contagem de palavras e suas ocorrências.
* Interface reativa com feedback de carregamento em todas as operações AJAX.
* Tabela de resultados com funcionalidades avançadas:
    * Paginação.
    * Ordenação por palavra ou por número de ocorrências.
    * Filtro dinâmico por palavra.
    * Seleção de quantidade de itens por página.
* Aba de "Histórico da Sessão" que armazena todas as análises realizadas e permite a consulta dos detalhes de cada uma.
* Estado da tela de análise é reiniciado a cada recarregamento da página, enquanto o histórico da sessão é mantido.

---

## Como Executar o Projeto

**Pré-requisitos:**
* JDK 8
* Apache Maven 3.6+
* WildFly 10.1.0.Final

**Passos:**
1.  **Configure o WildFly:**
    * Inicie o servidor usando o perfil `full`, que inclui JMS: `standalone.bat -c standalone-full.xml` (ou `./standalone.sh`).
    * Garanta que a fila JMS `java:jboss/jms/queue/AnaliseFraseQueue` esteja configurada no `standalone-full.xml`.

2.  **Construa o Projeto:**
    * Na raiz do projeto, execute o comando Maven para limpar e empacotar a aplicação:
        ```bash
        mvn clean install
        ```

3.  **Faça o Deploy:**
    * Com o WildFly em execução, execute o comando do plugin do WildFly para fazer o deploy:
        ```bash
        mvn wildfly:deploy
        ```

4.  **Acesse a Aplicação:**
    * Abra seu navegador e acesse: [http://localhost:8080/analisador-frases/](http://localhost:8080/analisador-frases/)
