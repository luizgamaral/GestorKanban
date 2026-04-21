/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estga.lp.gestorkanban.model;

/**
 *
 * @author luizi
 */


import java.io.Serializable;
import java.util.ArrayList;

public class Projeto implements Serializable {
    private String nome;
    private String descricao; // NOVO ATRIBUTO
    private ArrayList<Tarefa> tarefas;

    // Construtor atualizado
    public Projeto(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.tarefas = new ArrayList<>();
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public ArrayList<Tarefa> getTarefas() { return tarefas; }
    public void setTarefas(ArrayList<Tarefa> tarefas) { this.tarefas = tarefas; }

    public void adicionarTarefa(Tarefa t) { this.tarefas.add(t); }
    public void removerTarefa(Tarefa t) { this.tarefas.remove(t); }

    @Override
    public String toString() {
        return nome + " (" + tarefas.size() + " tarefas)";
    }
}