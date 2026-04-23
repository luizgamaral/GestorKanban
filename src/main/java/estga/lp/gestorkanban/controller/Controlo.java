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
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
            String nomeProjeto = JOptionPane.showInputDialog(view, "Introduza o nome do novo projeto: ", "Novo Projeto", JOptionPane.QUESTION_MESSAGE);
            if (nomeProjeto != null && !nomeProjeto.trim().isEmpty()){
                // 1. Pedir a descrição 
                String descProjeto = JOptionPane.showInputDialog(view, "Introduza uma descrição para o projeto: ", "Descrição do Projeto", JOptionPane.QUESTION_MESSAGE);
                if(descProjeto == null) descProjeto = ""; // Se cancelar, fica vazia
                
                // 2. Adiciona aos dados 
                Projeto novoProj = new Projeto(nomeProjeto.toUpperCase(), descProjeto);
                listaProjetos.add(novoProj);
                
                // 3. Adiciona à interface 
                view.getListaProjetosModel().addElement(novoProj.getNome());
                
                // Seleciona o projeto recém-criado automaticamente
                view.getListaProjetos().setSelectedIndex(listaProjetos.size() - 1);
            }
        });
        
        // Lógica de Editar Projeto 
        view.getBtnEditarProjeto().addActionListener(e -> {
            int index = view.getListaProjetos().getSelectedIndex();
            if (index != -1){
                Projeto projetoSelecionado = listaProjetos.get(index);
                
                // 1. Editar o Nome
                String novoNome = (String) JOptionPane.showInputDialog(
                        view, 
                        "Editar nome do projeto:", 
                        "Editar Projeto", 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        null, 
                        projetoSelecionado.getNome()
                );
                
                if (novoNome != null && !novoNome.trim().isEmpty()){
                    // 2. Editar a Descrição 
                    String novaDesc = (String) JOptionPane.showInputDialog(
                            view, 
                            "Editar descrição do projeto:", 
                            "Editar Descrição", 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            null, 
                            projetoSelecionado.getDescricao()
                    );
                    
                    if (novaDesc != null) {
                        // Atualiza o Model
                        projetoSelecionado.setNome(novoNome.toUpperCase());
                        projetoSelecionado.setDescricao(novaDesc);
                        
                        // Atualiza a View (Lista Lateral)
                        view.getListaProjetosModel().set(index, novoNome.toUpperCase());
                        
                        // Se este for o projeto selecionado, atualiza o título na barra de topo se necessário
                        JOptionPane.showMessageDialog(view, "Projeto atualizado com sucesso!");
                    }
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
                    view.limparQuadro(); // Limpa o ecrã para não ficarem tarefas fantasmas
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
        
        // Lógica de Gerir Equipa
        view.getBtnEquipa().addActionListener(e -> {
            int index = view.getListaProjetos().getSelectedIndex();
            if (index != -1) {
                Projeto projetoSelecionado = listaProjetos.get(index);
                //  Adicionada a opção Atribuir Tarefa
                String[] opcoes = {"Adicionar Membro", "Remover Membro", "Ver Equipa", "Atribuir Tarefa"};
                
                int escolha = JOptionPane.showOptionDialog(view, 
                        "Gestão de Equipa - O que deseja fazer?", 
                        "Equipa: " + projetoSelecionado.getNome(),
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

                if (escolha == 0) { // Adicionar
                    String novoMembro = JOptionPane.showInputDialog(view, "Introduza o nome do novo membro:", "Novo Membro", JOptionPane.QUESTION_MESSAGE);
                    if (novoMembro != null && !novoMembro.trim().isEmpty()) {
                        projetoSelecionado.adicionarMembro(novoMembro);
                        JOptionPane.showMessageDialog(view, "Membro '" + novoMembro + "' adicionado com sucesso!");
                    }
                } 
                else if (escolha == 1) { // Remover
                    ArrayList<String> equipa = projetoSelecionado.getEquipa();
                    if (equipa.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "A equipa não tem membros para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    String membroRemover = (String) JOptionPane.showInputDialog(view, 
                            "Selecione o membro a remover:", "Remover Membro", 
                            JOptionPane.QUESTION_MESSAGE, null, equipa.toArray(), equipa.get(0));
                    
                    if (membroRemover != null) {
                        projetoSelecionado.removerMembro(membroRemover);
                        // Se remover o membro, as tarefas dele passam a Sem Membro
                        for (Tarefa t : projetoSelecionado.getTarefas()) {
                            if (t.getMembro().equals(membroRemover)) {
                                t.setMembro("Sem Membro");
                            }
                        }
                        atualizarQuadroTarefas(); // Atualiza a UI para refletir tarefas sem membro
                        JOptionPane.showMessageDialog(view, "Membro removido com sucesso!");
                    }
                } 
                else if (escolha == 2) { // Ver Equipa
                    ArrayList<String> equipa = projetoSelecionado.getEquipa();
                    if (equipa.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "A equipa está vazia.", "Equipa", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(view, "Membros da Equipa:\n- " + String.join("\n- ", equipa), "Equipa", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else if (escolha == 3) { //  Atribuir Tarefa
                    ArrayList<Tarefa> tarefas = projetoSelecionado.getTarefas();
                    ArrayList<String> equipa = projetoSelecionado.getEquipa();

                    // Validações de segurança
                    if (tarefas.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Não há tarefas neste projeto para atribuir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (equipa.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "A equipa não tem membros. Adicione membros primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 1 seleciona a tarefa
                    Tarefa tarefaSelecionada = (Tarefa) JOptionPane.showInputDialog(view, 
                            "Selecione a tarefa que deseja atribuir:", "Atribuir Tarefa", 
                            JOptionPane.QUESTION_MESSAGE, null, tarefas.toArray(), tarefas.get(0));

                    if (tarefaSelecionada != null) {
                        // 2 prepara a lista de membros
                        String[] opcoesMembros = new String[equipa.size() + 1];
                        opcoesMembros[0] = "Sem Membro";
                        for (int i = 0; i < equipa.size(); i++) {
                            opcoesMembros[i + 1] = equipa.get(i);
                        }

                        // 3 seleciona o membro
                        String membroAtribuido = (String) JOptionPane.showInputDialog(view, 
                                "Atribuir a tarefa '" + tarefaSelecionada.getTitulo() + "' a qual membro?", "Selecionar Membro", 
                                JOptionPane.QUESTION_MESSAGE, null, opcoesMembros, tarefaSelecionada.getMembro());

                        if (membroAtribuido != null) {
                            tarefaSelecionada.setMembro(membroAtribuido);
                            atualizarQuadroTarefas(); // Redesenha os cartões instantaneamente
                            JOptionPane.showMessageDialog(view, "Tarefa atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(view, "Por favor, selecione um projeto para gerir a sua equipa.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Lógica de Criar Tarefa
        view.getBtnNovaTarefa().addActionListener(e -> {
            int indexProjeto = view.getListaProjetos().getSelectedIndex();
            
            if (indexProjeto != -1) {
                String titulo = JOptionPane.showInputDialog(view, "Título da Tarefa:", "Nova Tarefa", JOptionPane.QUESTION_MESSAGE);
                if (titulo != null && !titulo.trim().isEmpty()) {
                    
                    // Pedir Descrição
                    String descricao = JOptionPane.showInputDialog(view, "Descrição da Tarefa:", "Nova Tarefa - Descrição", JOptionPane.QUESTION_MESSAGE);
                    if(descricao == null) descricao = "";
                    
                    String[] opcoesPrioridade = {"Alta", "Média", "Baixa"};
                    String prioridade = (String) JOptionPane.showInputDialog(
                            view, "Selecione a Prioridade:", "Prioridade",
                            JOptionPane.QUESTION_MESSAGE, null, opcoesPrioridade, "Média"
                    );
                    if (prioridade == null) return; // Se o utilizador cancelar nesta fase, aborta a criação
                    
                    // Validação de Data
                    String data = "";
                    boolean dataValida = false;
                    while (!dataValida) {
                        data = JOptionPane.showInputDialog(view, "Data de Entrega (DD/MM/AAAA):", "Nova Tarefa - Data", JOptionPane.QUESTION_MESSAGE);
                        if (data == null) return; // Se o utilizador clicar em Cancelar, aborta a criação
                        
                        // Expressão regular Regex simples para verificar o formato DD/MM/AAAA
                        if (data.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$")) {
                            dataValida = true;
                        } else {
                            JOptionPane.showMessageDialog(view, "Data inválida! Por favor use o formato DD/MM/AAAA.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    
                    // Atribuir a um membro
                    Projeto projetoSelecionado = listaProjetos.get(indexProjeto);
                    ArrayList<String> equipa = projetoSelecionado.getEquipa();
                    String membroAtribuido = "Sem Membro";
                    
                    if (!equipa.isEmpty()) {
                        // Cria array com os membros + a opção de não atribuir a ninguém
                        String[] opcoesMembros = new String[equipa.size() + 1];
                        opcoesMembros[0] = "Sem Membro";
                        for (int i = 0; i < equipa.size(); i++) {
                            opcoesMembros[i + 1] = equipa.get(i);
                        }
                        
                        membroAtribuido = (String) JOptionPane.showInputDialog(view, 
                                "Atribuir tarefa a qual membro?", "Atribuir Tarefa", 
                                JOptionPane.QUESTION_MESSAGE, null, opcoesMembros, opcoesMembros[0]);
                        
                        if (membroAtribuido == null) return; // Se o utilizador cancelar
                    }
                    
                    // Passar o membroAtribuido no construtor
                    Tarefa novaTarefa = new Tarefa(titulo, descricao, prioridade, data, "A Fazer", membroAtribuido);
                    projetoSelecionado.adicionarTarefa(novaTarefa);
                    
                    atualizarQuadroTarefas();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Por favor, selecione um projeto na lista lateral antes de criar uma tarefa.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Lógica de Guardar Dados
        view.getBtnGuardar().addActionListener(e -> {
            // Pede o nome do ficheiro ao utilizador
            String nomeFicheiro = JOptionPane.showInputDialog(view, "Introduza o nome do ficheiro para guardar (ex: projeto_versao1):", "Guardar Dados", JOptionPane.QUESTION_MESSAGE);
            
            // Verifica se o utilizador escreveu algo e não cancelou
            if (nomeFicheiro != null && !nomeFicheiro.trim().isEmpty()) {
                
                // Adiciona a extensão .dat automaticamente se o utilizador não o fizer
                if (!nomeFicheiro.endsWith(".dat")) {
                    nomeFicheiro += ".dat";
                }
                
                // Garante que a pasta FicheirosSalvos existe
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

        // Lógica de Guardar Dados (Usando IODataLibrary)
        view.getBtnGuardar().addActionListener(e -> {
            String nomeFicheiro = JOptionPane.showInputDialog(view, "Introduza o nome do ficheiro para guardar:", "Guardar Dados", JOptionPane.QUESTION_MESSAGE);
            
            if (nomeFicheiro != null && !nomeFicheiro.trim().isEmpty()) {
                // Forçar extensão .txt para cumprir o enunciado
                if (!nomeFicheiro.endsWith(".txt")) {
                    nomeFicheiro += ".txt";
                }
                
                java.io.File diretorio = new java.io.File("FicheirosSalvos");
                if (!diretorio.exists()) diretorio.mkdirs(); 
                
                String caminho = "FicheirosSalvos/" + nomeFicheiro;
                
                try {
                    // Prepara a lista de Strings para a biblioteca
                    ArrayList<String> linhasToSave = new ArrayList<>();
                    
                    for (Projeto p : listaProjetos) {
                        // Guarda o projeto
                        linhasToSave.add("PROJETO|" + p.getNome() + "|" + p.getDescricao());
                        
                        // Guarda a equipa do projeto
                        for (String membro : p.getEquipa()) {
                            linhasToSave.add("MEMBRO|" + membro);
                        }
                        
                        // Guarda as tarefas do projeto
                        for (Tarefa t : p.getTarefas()) {
                            linhasToSave.add("TAREFA|" + t.getTitulo() + "|" + t.getDescricao() + "|" + t.getPrioridade() + "|" + t.getData() + "|" + t.getEstado() + "|" + t.getMembro());
                        }
                    }
                    
                    // Converte para String[] e usa a IODataLibrary para guardar
                    trabalho2.IODataClass io = new trabalho2.IODataClass();
                    io.writeData(caminho, linhasToSave.toArray(new String[0]));
                    
                    JOptionPane.showMessageDialog(view, "Dados guardados com sucesso em formato de texto!\n" + caminho, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao guardar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Lógica de Carregar Dados (Usando IODataLibrary)
        view.getBtnCarregar().addActionListener(e -> {
            String nomeFicheiro = JOptionPane.showInputDialog(view, "Introduza o nome do ficheiro a carregar:", "Carregar Dados", JOptionPane.QUESTION_MESSAGE);
            
            if (nomeFicheiro != null && !nomeFicheiro.trim().isEmpty()) {
                if (!nomeFicheiro.endsWith(".txt")) {
                    nomeFicheiro += ".txt";
                }
                
                String caminho = "FicheirosSalvos/" + nomeFicheiro;
                
                try {
                    // Usa a IODataLibrary para ler as linhas
                    trabalho2.IODataClass io = new trabalho2.IODataClass();
                    String[] linhasCarregadas = io.loadData(caminho);
                    
                    
                    Projeto projetoAtual = null; // Ajuda a saber a qual projeto as tarefas e membros pertencem
                    
                    // Reconstrói os objetos a partir do texto e adiciona à lista existente
                    for (String linha : linhasCarregadas) {
                        // O regex "\\|" é usado para separar pelo caractere |
                        String[] partes = linha.split("\\|"); 
                        
                        if (partes[0].equals("PROJETO") && partes.length >= 3) {
                            projetoAtual = new Projeto(partes[1], partes[2]);
                            listaProjetos.add(projetoAtual);
                            view.getListaProjetosModel().addElement(projetoAtual.getNome());
                            
                        } else if (partes[0].equals("MEMBRO") && partes.length >= 2 && projetoAtual != null) {
                            projetoAtual.adicionarMembro(partes[1]);
                            
                        } else if (partes[0].equals("TAREFA") && partes.length >= 7 && projetoAtual != null) {
                            Tarefa t = new Tarefa(partes[1], partes[2], partes[3], partes[4], partes[5], partes[6]);
                            projetoAtual.adicionarTarefa(t);
                        }
                    }
                    
                    JOptionPane.showMessageDialog(view, "Dados adicionados com sucesso a partir de:\n" + caminho, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao carregar ficheiro. Verifique se o ficheiro '" + nomeFicheiro + "' existe na pasta FicheirosSalvos.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Erro ao processar dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        
        // Lógica de Configurações / Detalhes do Projeto
        view.getBtnConfig().addActionListener(e -> {
            int index = view.getListaProjetos().getSelectedIndex();
            
            if (index != -1) {
                Projeto projetoSelecionado = listaProjetos.get(index);
                
                // Construir a mensagem com os dados do projeto
                StringBuilder info = new StringBuilder();
                info.append("TÍTULO DO PROJETO:\n").append(projetoSelecionado.getNome()).append("\n\n");
                
                info.append("DESCRIÇÃO:\n");
                String desc = projetoSelecionado.getDescricao();
                info.append(desc != null && !desc.trim().isEmpty() ? desc : "Sem descrição.").append("\n\n");
                
                info.append("TAREFAS (").append(projetoSelecionado.getTarefas().size()).append("):\n");
                
                if (projetoSelecionado.getTarefas().isEmpty()) {
                    info.append("- Nenhuma tarefa criada.");
                } else {
                    for (Tarefa t : projetoSelecionado.getTarefas()) {
                        // Adiciona o nome da tarefa e o estado atual entre parênteses retos
                        info.append("- ").append(t.getTitulo()).append(" [").append(t.getEstado()).append("]\n");
                    }
                }
                
                // Usar uma JTextArea para permitir scroll caso haja muitas tarefas ou uma descrição muito longa
                JTextArea textArea = new JTextArea(info.toString());
                textArea.setEditable(false); // Para que o utilizador não possa apagar o texto
                textArea.setOpaque(false);
                textArea.setFont(new Font("Arial", Font.PLAIN, 14));
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 250)); // Define um tamanho fixo para a janela
                scrollPane.setBorder(null); // Tira as bordas feias do scroll

                JOptionPane.showMessageDialog(view, scrollPane, "Detalhes do Projeto", JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                JOptionPane.showMessageDialog(view, "Por favor, selecione um projeto para ver os seus detalhes.", "Aviso", JOptionPane.WARNING_MESSAGE);
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
                
                // 1. Lógica para mover a tarefa
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

                // 2. Lógica para editar a tarefa
                java.awt.event.ActionListener acaoEditar = e -> {
                    String novoTitulo = (String) JOptionPane.showInputDialog(view, "Editar Título:", "Editar Tarefa", JOptionPane.QUESTION_MESSAGE, null, null, t.getTitulo());
                    if (novoTitulo != null && !novoTitulo.trim().isEmpty()) {
                        t.setTitulo(novoTitulo);

                        String novaDesc = (String) JOptionPane.showInputDialog(view, "Editar Descrição:", "Editar Tarefa", JOptionPane.QUESTION_MESSAGE, null, null, t.getDescricao());
                        if (novaDesc != null) {
                            t.setDescricao(novaDesc);
                        }
                        String[] opcoesPrioridade = {"Alta", "Média", "Baixa"};
                        String novaPrioridade = (String) JOptionPane.showInputDialog(
                                view, "Editar Prioridade:", "Prioridade",
                                JOptionPane.QUESTION_MESSAGE, null, opcoesPrioridade, t.getPrioridade()
                        );
                        if (novaPrioridade != null) {
                            t.setPrioridade(novaPrioridade);
                        }
                        
                        // Editar o membro atribuído
                        ArrayList<String> equipa = projetoSelecionado.getEquipa();
                        if (!equipa.isEmpty()) {
                            String[] opcoesMembros = new String[equipa.size() + 1];
                            opcoesMembros[0] = "Sem Membro";
                            for (int i = 0; i < equipa.size(); i++) opcoesMembros[i + 1] = equipa.get(i);
                            
                            String novoMembro = (String) JOptionPane.showInputDialog(view, 
                                    "Reatribuir tarefa:", "Editar Membro", 
                                    JOptionPane.QUESTION_MESSAGE, null, opcoesMembros, t.getMembro());
                            
                            if(novoMembro != null) t.setMembro(novoMembro);
                        }

                        atualizarQuadroTarefas();
                    }
                };

                // 3. Lógica para eliminar a tarefa
                java.awt.event.ActionListener acaoEliminar = e -> {
                    int confirm = JOptionPane.showConfirmDialog(view, "Tem a certeza que deseja eliminar a tarefa '" + t.getTitulo() + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        projetoSelecionado.removerTarefa(t);
                        atualizarQuadroTarefas();
                    }
                };

                // Cria o cartão passando as ações
                view.adicionarCartaoTarefa(t.getTitulo(), t.getDescricao(), t.getPrioridade(), t.getData(), t.getEstado(), t.getMembro(), acaoEditar, acaoEliminar, acaoMover);
            }
        }
    }
}