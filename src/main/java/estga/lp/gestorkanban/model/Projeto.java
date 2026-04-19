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
    private ArrayList<Tarefa> tarefas; // Lista que guarda as tarefas do Kanban

    public Projeto(String nome) {
        this.nome = nome;
        this.tarefas = new ArrayList<>();
    }

    // Getters e Setters
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public ArrayList<Tarefa> getTarefas() { return tarefas; }
    public void setTarefas(ArrayList<Tarefa> tarefas) { this.tarefas = tarefas; }

    // Logica

    //  Adiciona uma nova tarefa ao projeto
    
    public void adicionarTarefa(Tarefa t) {
        this.tarefas.add(t);
    }

     // Remove uma tarefa do projeto
    
    public void removerTarefa(Tarefa t) {
        this.tarefas.remove(t);
    }

    @Override
    public String toString() {
        return nome + " (" + tarefas.size() + " tarefas)";
    }
}
