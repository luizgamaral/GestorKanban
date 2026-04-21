/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estga.lp.gestorkanban.controller;

/**
 *
 * @author luizi
 */

import estga.lp.gestorkanban.model.Projeto;
import estga.lp.gestorkanban.model.Tarefa;
import estga.lp.gestorkanban.view.Interface;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Controlo {
    
    private Interface view;
    private ArrayList<Projeto> listaProjetos; // Model principal
    
    public Controlo(Interface view) {
        this.view = view;
        this.listaProjetos = new ArrayList<>();
        
        inicializarEventos();
    }
    
    // Método onde ligamos os botões da View às ações lógicas
private void inicializarEventos() {
        
        // Lógica de Novo Projeto
        view.getBtnNovoProjeto().addActionListener(e -> {
            String nomeProjeto = JOptionPane.showInputDialog(view, "Introduza o nome do novo projeto: ");
            if (nomeProjeto != null && !nomeProjeto.trim().isEmpty()){
                // 1. Pedir a descrição (NOVO)
                String descProjeto = JOptionPane.showInputDialog(view, "Introduza uma descrição para o projeto: ");
                if(descProjeto == null) descProjeto = ""; // Se cancelar, fica vazia
                
                // 2. Adiciona aos dados (Model)
                Projeto novoProj = new Projeto(nomeProjeto.toUpperCase(), descProjeto);
                listaProjetos.add(novoProj);
                
                // 3. Adiciona à interface (View)
                view.getListaProjetosModel().addElement(novoProj.getNome());
                
                // Opcional: Seleciona o projeto recém-criado automaticamente
                view.getListaProjetos().setSelectedIndex(listaProjetos.size() - 1);
            }
        });
        
        // Lógica de Editar Projeto
        view.getBtnEditarProjeto().addActionListener(e -> {
            int index = view.getListaProjetos().getSelectedIndex();
            if (index != -1){
                String projetoAtual = view.getListaProjetosModel().getElementAt(index);
                String novoNome = JOptionPane.showInputDialog(view, "Editar nome do projeto: ", projetoAtual);
                if (novoNome != null && !novoNome.trim().isEmpty()){
                    listaProjetos.get(index).setNome(novoNome.toUpperCase());
                    view.getListaProjetosModel().set(index, novoNome.toUpperCase());
                }
            } else {
                JOptionPane.showMessageDialog(view, "Por favor, selecione um projeto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Lógica de Eliminar Projeto
        view.getBtnEliminarProjeto().addActionListener(e -> {
            int index = view.getListaProjetos().getSelectedIndex();
            if (index != -1){
                int confirmacao = JOptionPane.showConfirmDialog(view, "Tem a certeza que deseja eliminar este projeto?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION){
                    listaProjetos.remove(index);
                    view.getListaProjetosModel().remove(index);
                    view.limparQuadro(); // NOVO: Limpa o ecrã para não ficarem tarefas "fantasmas"
                }
            } else {
                JOptionPane.showMessageDialog(view, "Por favor, selecione um projeto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // NOVO: Atualizar o quadro Kanban quando um projeto diferente é clicado na lista lateral
        view.getListaProjetos().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarQuadroTarefas();
            }
        });
        
        // Lógica de Criar Tarefa
        view.getBtnNovaTarefa().addActionListener(e -> {
            int indexProjeto = view.getListaProjetos().getSelectedIndex();
            
            if (indexProjeto != -1) {
                String titulo = JOptionPane.showInputDialog(view, "Título da Tarefa:");
                if (titulo != null && !titulo.trim().isEmpty()) {
                    
                    // Pedir Descrição
                    String descricao = JOptionPane.showInputDialog(view, "Descrição da Tarefa:");
                    if(descricao == null) descricao = "";
                    
                    String prioridade = JOptionPane.showInputDialog(view, "Prioridade (Alta, Média, Baixa):", "Média");
                    
                    // Validação de Data (NOVO)
                    String data = "";
                    boolean dataValida = false;
                    while (!dataValida) {
                        data = JOptionPane.showInputDialog(view, "Data de Entrega (DD/MM/AAAA):");
                        if (data == null) return; // Se o utilizador clicar em "Cancelar", aborta a criação
                        
                        // Expressão regular (Regex) simples para verificar o formato DD/MM/AAAA
                        if (data.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$")) {
                            dataValida = true;
                        } else {
                            JOptionPane.showMessageDialog(view, "Data inválida! Por favor use o formato DD/MM/AAAA.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    
                    Tarefa novaTarefa = new Tarefa(titulo, descricao, prioridade, data, "A Fazer", "Sem Membro");
                    Projeto projetoSelecionado = listaProjetos.get(indexProjeto);
                    projetoSelecionado.adicionarTarefa(novaTarefa);
                    
                    atualizarQuadroTarefas(); // Atualiza a vista com todas as tarefas do projeto
                }
            } else {
                JOptionPane.showMessageDialog(view, "Por favor, selecione um projeto na lista lateral antes de criar uma tarefa.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Limpa o quadro e desenha todas as tarefas do projeto selecionado
    private void atualizarQuadroTarefas() {
        view.limparQuadro(); // Limpa as tarefas antigas
        int index = view.getListaProjetos().getSelectedIndex();
        
        if (index != -1) {
            Projeto projetoSelecionado = listaProjetos.get(index);
            // Percorre todas as tarefas do projeto selecionado e desenha-as
            for (Tarefa t : projetoSelecionado.getTarefas()) {
                view.adicionarCartaoTarefa(t.getTitulo(), t.getDescricao(), t.getPrioridade(), t.getData(), t.getEstado());
            }
        }
    }

}