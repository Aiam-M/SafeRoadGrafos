package com.projeto;

import java.util.*;

public class Algoritmo {
    // map para armazenar o menor nível encontrado para chegar naquele nó
    // Obs:  Média da segurança é inversa, então quanto menor melhor
    private Map<No, Double> seguranca;

    //Salva qual foi a aresta predecessora pra chegar naquele nó
    private Map<No, Aresta> predecessor;

    public Algoritmo() {
        this.seguranca = new HashMap<>();
        this.predecessor = new HashMap<>();
    }

    /**
     * Execura o algoritmo de Dijkstra de todos os nós até o nó de origem
     * @param grafo a lista de adjacência
     * @param origem ponto de partida
     */
    public void executar(Grafo grafo, No origem){
        //Inicia montando o map e colocando a distancia máxima em todos os nós
        for (No no: grafo.obterTodosNos()) {
            seguranca.put(no, Double.MAX_VALUE);
        }
        //O nó de origem começa sendo 0 já que é a origem :>
        seguranca.put(origem, 0.0);

        //Fila de prioridade que vai sempre comparar e devolver o no com menor valor
        PriorityQueue<No> fila = new PriorityQueue<>((a,b) -> Double.compare(seguranca.get(a),seguranca.get(b)));
        fila.add(origem);

        Set<No> visitados = new HashSet<>();

        while (!fila.isEmpty()){

            //Pega o no com menor valor
            No noAtual = fila.poll();

            //Verifica se esse no já foi visitado
            if (visitados.contains(noAtual)){
                //Caso sim, pula para a próxima iteração
                continue;
            }
            visitados.add(noAtual);

            //Olha todas as arestas que saem do nó atual
            for (Aresta aresta : grafo.obterVizinhos()){
                No vizinho = aresta.getDestino();
                double novoPeso = seguranca.get(noAtual) + aresta.getPeso();
                //Se o valor for menor, encontrou uma rota melhor para aquele nó
                if (novoPeso < seguranca.get(vizinho)){
                    //Atualiza o valor do destino
                    seguranca.put(vizinho, novoPeso);
                    //Atualiza o predecessor
                    predecessor.put(vizinho, aresta);

                    fila.add(vizinho);
                }

            }
        }
    }

    /**
     * Reconstroi o caminho até o destino escolhido usando os predecessores guardados no algoritmo
     * @param destino destino, né
     * @return a lista com o caminho montado
     */
    public List<Aresta> reconstuirCaminho(No destino){
        List<Aresta> caminho = new ArrayList<>();

        // Faz um caminho invertido, começando no destino e indo até a origem
        No atual = destino;

        //Enquanto existir predecessores, vai haver iteração
        while (predecessor.containsKey(atual)){
            Aresta aresta = predecessor.get(atual);
            caminho.add(0,aresta); //Adiciona no começo da lista e o elemento que já estava vai para a segunda posição
            atual = aresta.getOrigem(); //Pega o no de origem daquela aresta
        }
        return caminho;
    }

    /**
     * Imprime o caminho de forma legível
     */
    public void imprimirCaminho(No origem, No destino) {
        System.out.println("Caminho de " + origem.getNome() + " até " + destino.getNome() + ":");

        List<Aresta> caminho = reconstruirCaminho(destino);

        if (caminho.isEmpty()) {
            System.out.println("  Sem rota possível!");
            return;
        }

        double riscTotal = 0;
        for (int i = 0; i < caminho.size(); i++) {
            Aresta aresta = caminho.get(i);
            riscTotal += aresta.getPeso();
            System.out.printf("%d. %s (de %s para %s) - Risco: %.2f%n",
                    i + 1,
                    aresta.getNomeRua(),
                    aresta.getOrigem().getId(),
                    aresta.getDestino().getId(),
                    aresta.getPeso()
            );
        }
        System.out.printf("Risco total: %.2f%n", riscTotal);
    }
}
