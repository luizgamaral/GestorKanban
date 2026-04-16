/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estga.lp.gestorkanban.model;

/**
 *
 * @author luizi
 */
public class Tarefa {
    private String titulo;
    private String prioridade;
    private String descricao;
    private String data;
    private String membro;
    private String estado;
    
    public Tarefa(String titulo, String descricao, String prioridade, String data, String estado, String membro) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.data = data;
        this.estado = estado;
        this.membro = membro;
    }
    
    
}
