package com.projeto;

public class Edge {
    private final Node destino;
    private final double peso;
    private final Node origem;
    private final String nomeRua;

    public Edge(Node origem ,Node destino, double peso, String nome) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
        this.nomeRua = nome;
    }

    public Node getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public Node getOrigem() {
        return origem;
    }

    public String getNome() {
        return nomeRua;
    }

    public String getNomeRua() {
        return nomeRua;
    }

    @Override
    public String toString() {
        return 
        "Edge [destino=" + destino + 
        ", peso=" + peso + 
        ", origem=" + origem + 
        ", nomeRua=" + nomeRua + "]";
    }

   

    
}