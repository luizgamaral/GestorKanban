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
    private ArrayList<String> equipa;

    // Construtor atualizado
    public Projeto(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.tarefas = new ArrayList<>();
        this.equipa = new ArrayList<>();
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
    
    // Métodos de Equipa
    public ArrayList<String> getEquipa() { return equipa; }
    public void adicionarMembro(String membro) {
        if (!equipa.contains(membro)) {
            this.equipa.add(membro);
        }
    }
    public void removerMembro(String membro) { this.equipa.remove(membro); }

    @Override
    public String toString() {
        return nome + " (" + tarefas.size() + " tarefas)";
    }
}