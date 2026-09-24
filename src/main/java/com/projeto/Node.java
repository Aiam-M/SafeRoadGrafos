package com.projeto;
public class Node {
    static private int counter = 1;
    private int id;
    private String nome;

    public Node(String nome) {
        this.id = counter++;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "Node [id=" + id + ", nome=" + nome + "]";
    }
    



}
