package com.projeto;

import java.util.*;

public class Main {
    // Mapa auxiliar para acessar os nos pelo codigo (ex: "SL_DS")
    private static Map<String, Node> nos = new HashMap<>();

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        criarNos();
        adicionarArestas(grafo);

        Node origem = nos.get("SL_JP");
        Node destino = nos.get("JB_NJ");

        System.out.println("===== Rota mais segura: " + origem.getNome() + " -> " + destino.getNome() + " =====");
        Algoritmo dijkstra = new Algoritmo();
        dijkstra.executar(grafo, origem);
        dijkstra.imprimirCaminho(origem, destino);
    }

    private static void criarNos() {
        // ===== Av. Senador Lemos (via secundaria) =====
        nos.put("SL_JP", new Node("Av. Senador Lemos x Travessa Jose Pio"));
        nos.put("SL_DC", new Node("Av. Senador Lemos x Travessa Dom Romualdo Coelho"));
        nos.put("SL_DS", new Node("Av. Senador Lemos x Travessa Dom Romualdo de Seixas"));
        nos.put("SL_AW", new Node("Av. Senador Lemos x Travessa Almirante Wandenkolk"));
        nos.put("SL_QM", new Node("Av. Senador Lemos x Travessa 14 de Marco"));

        // ===== Rua Bernal do Couto (via residencial/terciaria - ALTERNA sentido na DS) =====
        nos.put("BC_DC", new Node("Rua Bernal do Couto x Travessa Dom Romualdo Coelho"));
        nos.put("BC_DS", new Node("Rua Bernal do Couto x Travessa Dom Romualdo de Seixas"));
        nos.put("BC_AW", new Node("Rua Bernal do Couto x Travessa Almirante Wandenkolk"));
        nos.put("BC_QM", new Node("Rua Bernal do Couto x Travessa 14 de Marco"));
        nos.put("BC_NJ", new Node("Rua Bernal do Couto x Travessa Nove de Janeiro"));

        // ===== Rua Boaventura da Silva (via terciaria) =====
        nos.put("BS_DS", new Node("Rua Boaventura da Silva x Travessa Dom Romualdo de Seixas"));
        nos.put("BS_AW", new Node("Rua Boaventura da Silva x Travessa Almirante Wandenkolk"));
        nos.put("BS_QM", new Node("Rua Boaventura da Silva x Travessa 14 de Marco"));
        nos.put("BS_NJ", new Node("Rua Boaventura da Silva x Travessa Nove de Janeiro"));

        // ===== Rua Antonio Barreto (via secundaria) =====
        nos.put("AB_DS", new Node("Rua Antonio Barreto x Travessa Dom Romualdo de Seixas"));
        nos.put("AB_AW", new Node("Rua Antonio Barreto x Travessa Almirante Wandenkolk"));
        nos.put("AB_QM", new Node("Rua Antonio Barreto x Travessa 14 de Marco"));
        nos.put("AB_NJ", new Node("Rua Antonio Barreto x Travessa Nove de Janeiro"));

        // ===== Rua Joao Balbi (via residencial) =====
        nos.put("JB_DS", new Node("Rua Joao Balbi x Travessa Dom Romualdo de Seixas"));
        nos.put("JB_AW", new Node("Rua Joao Balbi x Travessa Almirante Wandenkolk"));
        nos.put("JB_QM", new Node("Rua Joao Balbi x Travessa 14 de Marco"));
        nos.put("JB_NJ", new Node("Rua Joao Balbi x Travessa Nove de Janeiro"));
    }

    private static void adicionarArestas(Grafo grafo) {
        // Pesos por tipo de via (hardcoded, baseado na classificacao do levantamento):
        // Via secundaria = 2.4 | Via terciaria = 3.1 | Via residencial = 3.9
        // Rua Bernal do Couto (residencial/terciaria, classificacao mista) = 3.5
        double secundaria = 2.4;
        double terciaria = 3.1;
        double residencial = 3.9;
        double mista = 3.5;

        // ===================== HORIZONTAIS (vias transversais) =====================
        // SUPOSICAO: sentido Oeste (Doca) -> Leste, exceto onde ha alternancia documentada

        // --- Av. Senador Lemos: JP -> DC -> DS -> AW -> QM ---
        grafo.adicionarAresta(nos.get("SL_JP"), nos.get("SL_DC"), secundaria, "Av. Senador Lemos");
        grafo.adicionarAresta(nos.get("SL_DC"), nos.get("SL_DS"), secundaria, "Av. Senador Lemos");
        grafo.adicionarAresta(nos.get("SL_DS"), nos.get("SL_AW"), secundaria, "Av. Senador Lemos");
        grafo.adicionarAresta(nos.get("SL_AW"), nos.get("SL_QM"), secundaria, "Av. Senador Lemos");

        // --- Rua Bernal do Couto: ALTERNA sentido ao cruzar a Travessa Dom Romualdo de Seixas ---
        // Antes da DS (Oeste -> Leste):
        grafo.adicionarAresta(nos.get("BC_DC"), nos.get("BC_DS"), mista, "Rua Bernal do Couto");
        // Depois da DS, sentido invertido (Leste -> Oeste):
        grafo.adicionarAresta(nos.get("BC_NJ"), nos.get("BC_QM"), mista, "Rua Bernal do Couto");
        grafo.adicionarAresta(nos.get("BC_QM"), nos.get("BC_AW"), mista, "Rua Bernal do Couto");
        grafo.adicionarAresta(nos.get("BC_AW"), nos.get("BC_DS"), mista, "Rua Bernal do Couto");

        // --- Rua Boaventura da Silva: DS -> AW -> QM -> NJ ---
        grafo.adicionarAresta(nos.get("BS_DS"), nos.get("BS_AW"), terciaria, "Rua Boaventura da Silva");
        grafo.adicionarAresta(nos.get("BS_AW"), nos.get("BS_QM"), terciaria, "Rua Boaventura da Silva");
        grafo.adicionarAresta(nos.get("BS_QM"), nos.get("BS_NJ"), terciaria, "Rua Boaventura da Silva");

        // --- Rua Antonio Barreto: DS -> AW -> QM -> NJ ---
        grafo.adicionarAresta(nos.get("AB_DS"), nos.get("AB_AW"), secundaria, "Rua Antonio Barreto");
        grafo.adicionarAresta(nos.get("AB_AW"), nos.get("AB_QM"), secundaria, "Rua Antonio Barreto");
        grafo.adicionarAresta(nos.get("AB_QM"), nos.get("AB_NJ"), secundaria, "Rua Antonio Barreto");

        // --- Rua Joao Balbi: DS -> AW -> QM -> NJ ---
        grafo.adicionarAresta(nos.get("JB_DS"), nos.get("JB_AW"), residencial, "Rua Joao Balbi");
        grafo.adicionarAresta(nos.get("JB_AW"), nos.get("JB_QM"), residencial, "Rua Joao Balbi");
        grafo.adicionarAresta(nos.get("JB_QM"), nos.get("JB_NJ"), residencial, "Rua Joao Balbi");

        // ===================== VERTICAIS (travessas) =====================
        // SUPOSICAO: sentido Norte -> Sul, ordem: Senador Lemos -> Bernal do Couto
        // -> Boaventura da Silva -> Antonio Barreto -> Joao Balbi

        // --- Travessa Dom Romualdo Coelho: SL -> BC (nao alcanca as demais ruas) ---
        grafo.adicionarAresta(nos.get("SL_DC"), nos.get("BC_DC"), residencial, "Travessa Dom Romualdo Coelho");

        // --- Travessa Dom Romualdo de Seixas: SL -> BC -> BS -> AB -> JB ---
        grafo.adicionarAresta(nos.get("SL_DS"), nos.get("BC_DS"), residencial, "Travessa Dom Romualdo de Seixas");
        grafo.adicionarAresta(nos.get("BC_DS"), nos.get("BS_DS"), residencial, "Travessa Dom Romualdo de Seixas");
        grafo.adicionarAresta(nos.get("BS_DS"), nos.get("AB_DS"), residencial, "Travessa Dom Romualdo de Seixas");
        grafo.adicionarAresta(nos.get("AB_DS"), nos.get("JB_DS"), residencial, "Travessa Dom Romualdo de Seixas");

        // --- Travessa Almirante Wandenkolk: SL -> BC -> BS -> AB -> JB ---
        grafo.adicionarAresta(nos.get("SL_AW"), nos.get("BC_AW"), residencial, "Travessa Almirante Wandenkolk");
        grafo.adicionarAresta(nos.get("BC_AW"), nos.get("BS_AW"), residencial, "Travessa Almirante Wandenkolk");
        grafo.adicionarAresta(nos.get("BS_AW"), nos.get("AB_AW"), residencial, "Travessa Almirante Wandenkolk");
        grafo.adicionarAresta(nos.get("AB_AW"), nos.get("JB_AW"), residencial, "Travessa Almirante Wandenkolk");

        // --- Travessa 14 de Marco: SL -> BC -> BS -> AB -> JB ---
        grafo.adicionarAresta(nos.get("SL_QM"), nos.get("BC_QM"), terciaria, "Travessa 14 de Marco");
        grafo.adicionarAresta(nos.get("BC_QM"), nos.get("BS_QM"), terciaria, "Travessa 14 de Marco");
        grafo.adicionarAresta(nos.get("BS_QM"), nos.get("AB_QM"), terciaria, "Travessa 14 de Marco");
        grafo.adicionarAresta(nos.get("AB_QM"), nos.get("JB_QM"), terciaria, "Travessa 14 de Marco");

        // --- Travessa Nove de Janeiro: BC -> BS -> AB -> JB (nao alcanca Senador Lemos) ---
        grafo.adicionarAresta(nos.get("BC_NJ"), nos.get("BS_NJ"), residencial, "Travessa Nove de Janeiro");
        grafo.adicionarAresta(nos.get("BS_NJ"), nos.get("AB_NJ"), residencial, "Travessa Nove de Janeiro");
        grafo.adicionarAresta(nos.get("AB_NJ"), nos.get("JB_NJ"), residencial, "Travessa Nove de Janeiro");

    }
}