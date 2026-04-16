/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estga.lp.gestorkanban.model;

import java.io.Serializable;

/**
 *
 * @author luizi
 */

public class Tarefa implements Serializable { // Uso de serizalização para guardar as tarefas.
    // Atributos privados para proteger a integridade dos dados.
    private String titulo;
    private String prioridade;
    private String descricao;
    private String data;
    private String membro;
    private String estado;
    
    // Construtor da classe
    public Tarefa(String titulo, String descricao, String prioridade, String data, String estado, String membro) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.data = data;
        this.estado = estado;
        this.membro = membro;
    }
    
    // Getters e Setters.
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }
    
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMembro() { return membro; }
    public void setMembro(String membro) { this.membro = membro; }
    
    @Override
    public String toString() {
        return "Tarefa: " + titulo + " [" + estado + "] - Responsável: " + membro;
    }
    
}
