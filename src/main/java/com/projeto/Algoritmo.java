package com.projeto;

import java.util.*;

public class Algoritmo {
    // map para armazenar o menor nível encontrado para chegar naquele nó
    // Obs: Média da segurança é inversa, então quanto menor melhor
    private Map<Node, Double> seguranca;

    // Salva qual foi a aresta predecessora pra chegar naquele nó
    private Map<Node, Edge> predecessor;

    public Algoritmo() {
        this.seguranca = new HashMap<>();
        this.predecessor = new HashMap<>();
    }

    /**
     * Executa o algoritmo de Dijkstra a partir do nó de origem
     * @param grafo a lista de adjacência
     * @param origem ponto de partida
     */
    public void executar(Grafo grafo, Node origem){
        // Inicia montando o map e colocando a distância máxima em todos os nós
        for (Node no : grafo.getTodosNodes()) {
            seguranca.put(no, Double.MAX_VALUE);
        }
        // O nó de origem começa sendo 0 já que é a origem :>
        seguranca.put(origem, 0.0);

        // Fila de prioridade que vai sempre comparar e devolver o nó com menor valor
        PriorityQueue<Node> fila = new PriorityQueue<>((a, b) -> Double.compare(seguranca.get(a), seguranca.get(b)));
        fila.add(origem);

        Set<Node> visitados = new HashSet<>();

        while (!fila.isEmpty()) {

            // Pega o nó com menor valor
            Node noAtual = fila.poll();

            // Verifica se esse nó já foi visitado
            if (visitados.contains(noAtual)) {
                continue;
            }
            visitados.add(noAtual);

            // Olha todas as arestas que saem do nó atual
            for (Edge aresta : grafo.getVizinhos(noAtual)) {
                Node vizinho = aresta.getDestino();
                double novoPeso = seguranca.get(noAtual) + aresta.getPeso();

                // Se o valor for menor, encontrou uma rota melhor para aquele nó
                if (novoPeso < seguranca.get(vizinho)) {
                    seguranca.put(vizinho, novoPeso);
                    predecessor.put(vizinho, aresta);
                    fila.add(vizinho);
                }
            }
        }
    }

    /**
     * Reconstrói o caminho até o destino escolhido usando os predecessores guardados
     * @param destino nó de destino
     * @return a lista com o caminho montado
     */
    public List<Edge> reconstruirCaminho(Node destino) {
        List<Edge> caminho = new ArrayList<>();

        Node atual = destino;

        while (predecessor.containsKey(atual)) {
            Edge aresta = predecessor.get(atual);
            caminho.add(0, aresta);
            atual = aresta.getOrigem();
        }
        return caminho;
    }

    /**
     * Imprime o caminho de forma legível
     */
    public void imprimirCaminho(Node origem, Node destino) {
        System.out.println("Caminho de " + origem.getNome() + " até " + destino.getNome() + ":");

        List<Edge> caminho = reconstruirCaminho(destino);

        if (caminho.isEmpty()) {
            System.out.println("  Sem rota possível!");
            return;
        }

        double riscoTotal = 0;
        for (int i = 0; i < caminho.size(); i++) {
            Edge aresta = caminho.get(i);
            riscoTotal += aresta.getPeso();
            System.out.printf("%d. %s (de %s para %s) - Risco: %.2f%n",
                    i + 1,
                    aresta.getNomeRua(),
                    aresta.getOrigem().getNome(),
                    aresta.getDestino().getNome(),
                    aresta.getPeso()
            );
        }
        System.out.printf("Risco total: %.2f%n", riscoTotal);
    }
}