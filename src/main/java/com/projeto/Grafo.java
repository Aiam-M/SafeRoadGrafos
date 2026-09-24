package com.projeto;

import java.util.*;

public class Grafo {
    private final Map<Node, List<Edge>> adjacencia = new HashMap<>();

    public void adicionarNode(Node node) {
        adjacencia.putIfAbsent(node, new ArrayList<>());
    }

    public void adicionarAresta(Node origem, Node destino, double peso) {
        adicionarAresta(origem, destino, peso, "");
    }

    public void adicionarAresta(Node origem, Node destino, double peso, String nomeRua) {
        adicionarNode(origem);
        adicionarNode(destino);

        adjacencia.get(origem).add(new Edge(origem, destino, peso, nomeRua));
    }

    public List<Edge> getVizinhos(Node node) {
        return adjacencia.getOrDefault(node, Collections.emptyList());
    }

    public Set<Node> getTodosNodes() {
        return adjacencia.keySet();
    }
}