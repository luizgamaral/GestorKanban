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

        // Atualizar o quadro Kanban quando um projeto diferente é clicado na lista lateral
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
                    
                    // Validação de Data
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
        
        // Lógica de Guardar Dados
        view.getBtnGuardar().addActionListener(e -> {
            // Pede o nome do ficheiro ao utilizador
            String nomeFicheiro = JOptionPane.showInputDialog(view, "Introduza o nome do ficheiro para guardar (ex: projeto_versao1):");
            
            // Verifica se o utilizador escreveu algo e não cancelou
            if (nomeFicheiro != null && !nomeFicheiro.trim().isEmpty()) {
                
                // Adiciona a extensão .dat automaticamente se o utilizador não o fizer
                if (!nomeFicheiro.endsWith(".dat")) {
                    nomeFicheiro += ".dat";
                }
                
                // Garante que a pasta "FicheirosSalvos" existe
                java.io.File diretorio = new java.io.File("FicheirosSalvos");
                if (!diretorio.exists()) {
                    diretorio.mkdirs(); // Cria a pasta se ela não existir
                }
                
                // Caminho final do ficheiro
                String caminho = "FicheirosSalvos/" + nomeFicheiro;
                
                try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(caminho))) {
                    oos.writeObject(listaProjetos);
                    JOptionPane.showMessageDialog(view, "Dados guardados com sucesso em:\n" + caminho, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao guardar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Lógica de Carregar Dados
        view.getBtnCarregar().addActionListener(e -> {
            // Pede o nome do ficheiro ao utilizador
            String nomeFicheiro = JOptionPane.showInputDialog(view, "Introduza o nome do ficheiro a carregar:");
            
            if (nomeFicheiro != null && !nomeFicheiro.trim().isEmpty()) {
                
                // Adiciona a extensão .dat automaticamente caso o utilizador se esqueça
                if (!nomeFicheiro.endsWith(".dat")) {
                    nomeFicheiro += ".dat";
                }
                
                String caminho = "FicheirosSalvos/" + nomeFicheiro;
                
                try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(caminho))) {
                    
                    // 1. Carrega os dados para o Model
                    listaProjetos = (ArrayList<Projeto>) ois.readObject();
                    
                    // 2. Limpa a View atual
                    view.getListaProjetosModel().clear();
                    view.limparQuadro();
                    
                    // 3. Preenche a lista lateral com os projetos carregados
                    for (Projeto p : listaProjetos) {
                        view.getListaProjetosModel().addElement(p.getNome());
                    }
                    
                    JOptionPane.showMessageDialog(view, "Dados carregados com sucesso de:\n" + caminho, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (java.io.FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(view, "O ficheiro '" + nomeFicheiro + "' não foi encontrado na pasta FicheirosSalvos.", "Erro - Ficheiro não encontrado", JOptionPane.WARNING_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao carregar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
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
                
                // 1. Lógica para MOVER a tarefa
                java.awt.event.ActionListener acaoMover = e -> {
                    String[] opcoes = {"A Fazer", "Em Progresso", "Concluído"};
                    String novoEstado = (String) JOptionPane.showInputDialog(
                            view, "Mover tarefa para a coluna:", "Mover Tarefa",
                            JOptionPane.QUESTION_MESSAGE, null, opcoes, t.getEstado()
                    );
                    if (novoEstado != null) {
                        t.setEstado(novoEstado); // Altera no Model
                        atualizarQuadroTarefas(); // Redesenha a View
                    }
                };

                // 2. Lógica para EDITAR a tarefa (Opcional, mas já fica a funcionar!)
                java.awt.event.ActionListener acaoEditar = e -> {
                    String novoTitulo = JOptionPane.showInputDialog(view, "Editar Título:", t.getTitulo());
                    if (novoTitulo != null && !novoTitulo.trim().isEmpty()) {
                        t.setTitulo(novoTitulo);
                        t.setDescricao(JOptionPane.showInputDialog(view, "Editar Descrição:", t.getDescricao()));
                        t.setPrioridade(JOptionPane.showInputDialog(view, "Editar Prioridade:", t.getPrioridade()));
                        atualizarQuadroTarefas();
                    }
                };

                // 3. Lógica para ELIMINAR a tarefa
                java.awt.event.ActionListener acaoEliminar = e -> {
                    int confirm = JOptionPane.showConfirmDialog(view, "Tem a certeza que deseja eliminar a tarefa '" + t.getTitulo() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        projetoSelecionado.removerTarefa(t);
                        atualizarQuadroTarefas();
                    }
                };

                // Cria o cartão passando as ações
                view.adicionarCartaoTarefa(t.getTitulo(), t.getDescricao(), t.getPrioridade(), t.getData(), t.getEstado(), acaoEditar, acaoEliminar, acaoMover);
            }
        }
    }
}