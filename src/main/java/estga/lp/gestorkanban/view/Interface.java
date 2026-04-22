/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estga.lp.gestorkanban.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 *
 * @author luizi
 */

public class Interface extends JFrame {
    
    // Atributos acessíveis ao Controlo
    private DefaultListModel<String> listaProjetosModel;
    private JList<String> listaProjetos;
    
    // Botões
    private JButton btnNovoProjeto;
    private JButton btnEditarProjeto;
    private JButton btnEliminarProjeto;
    private JButton btnNovaTarefa; // Novo botão para tarefas
    private JButton btnGuardar;  // NOVO
    private JButton btnCarregar; // NOVO
    private JButton btnEquipa;
    
    // Painéis das Colunas Kanban
    private JPanel painelAFazer;
    private JPanel painelEmProgresso;
    private JPanel painelConcluido;

    public Interface() {
        setTitle("Gestor Kanban - TP2");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Inicializa os modelos
        listaProjetosModel = new DefaultListModel<>();
        listaProjetos = new JList<>(listaProjetosModel);
        
        initUI();
    }
    
    private void initUI(){
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);
        
        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(new Color(240, 244, 248));
        
        JPanel topBar = createTopBar();
        mainArea.add(topBar, BorderLayout.NORTH);
        
        JPanel kanbanBoard = createKanbanBoard();
        mainArea.add(kanbanBoard, BorderLayout.CENTER);
        
        add(mainArea, BorderLayout.CENTER);
    }
    
    private JPanel createSidebar(){
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel panelBotoes = new JPanel(new GridLayout(2, 1, 5, 5));
        panelBotoes.setBackground(Color.WHITE);
        
        btnNovoProjeto = new JButton("+ NOVO PROJETO");
        btnNovoProjeto.setBackground(new Color(70, 130, 180));
        btnNovoProjeto.setForeground(Color.WHITE);
        
        JPanel subBotoes = new JPanel(new GridLayout(1, 2, 5, 5));
        btnEditarProjeto = new JButton("EDITAR");
        btnEliminarProjeto = new JButton("ELIMINAR");
        
        subBotoes.add(btnEditarProjeto);
        subBotoes.add(btnEliminarProjeto);
        
        panelBotoes.add(btnNovoProjeto);
        panelBotoes.add(subBotoes);
        
        sidebar.add(panelBotoes, BorderLayout.NORTH);
        
        JScrollPane scrollProjetos = new JScrollPane(listaProjetos);
        scrollProjetos.setBorder(BorderFactory.createTitledBorder("PROJETOS"));
        scrollProjetos.getViewport().setBackground(Color.WHITE);
        
        sidebar.add(scrollProjetos, BorderLayout.CENTER);
        return sidebar;
    }
    
    private JPanel createTopBar(){
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 244, 248));
        topBar.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JPanel panelTitulos = new JPanel(new GridLayout(2, 1));
        panelTitulos.setOpaque(false);
        JLabel tituloGeral = new JLabel("QUANBO TP2");
        tituloGeral.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel tituloProjeto = new JLabel("IMPLEMENTAÇÃO TP2 (LP)");
        tituloProjeto.setFont(new Font ("Arial", Font.BOLD, 14));
        panelTitulos.add(tituloGeral);
        panelTitulos.add(tituloProjeto);
        
        // Ajustado para incluir o botão de Nova Tarefa
        JPanel panelBotoesDir = new JPanel(new GridLayout(2, 3, 10, 10));
        panelBotoesDir.setOpaque(false);
        
        btnNovaTarefa = new JButton("➕ NOVA TAREFA");
        JButton btnConfig = new JButton("⚙ CONFIGURAÇÕES");
        btnEquipa = new JButton("👥 GERIR EQUIPA");
        btnGuardar = new JButton("💾 GUARDAR DADOS");
        btnCarregar = new JButton("📂 CARREGAR DADOS");
        
        panelBotoesDir.add(btnNovaTarefa);
        panelBotoesDir.add(btnConfig);
        panelBotoesDir.add(btnEquipa);
        panelBotoesDir.add(btnGuardar);
        panelBotoesDir.add(btnCarregar);
        
        topBar.add(panelTitulos, BorderLayout.WEST);
        topBar.add(panelBotoesDir, BorderLayout.EAST);
        return topBar;
    }
    
    private JPanel createKanbanBoard(){
        JPanel board = new JPanel(new GridLayout(1, 3, 15, 0));
        board.setBackground(new Color(240, 244, 248));
        board.setBorder(new EmptyBorder(10, 20, 20, 20));
        
        board.add(createKanbanColumn("A FAZER"));
        board.add(createKanbanColumn("EM PROGRESSO"));
        board.add(createKanbanColumn("CONCLUÍDO"));
        return board;
    }
    
    private JPanel createKanbanColumn(String titulo){
        JPanel coluna = new JPanel(new BorderLayout());
        coluna.setBackground(new Color(225, 230, 235));
        coluna.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBorder(new EmptyBorder(10, 0, 10, 0));
        coluna.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel painelTarefas = new JPanel();
        painelTarefas.setLayout(new BoxLayout(painelTarefas, BoxLayout.Y_AXIS));
        painelTarefas.setBackground(new Color(225, 230, 235));
        
        // Guarda a referência do painel interno dependendo da coluna
        if(titulo.equals("A FAZER")) painelAFazer = painelTarefas;
        else if(titulo.equals("EM PROGRESSO")) painelEmProgresso = painelTarefas;
        else if(titulo.equals("CONCLUÍDO")) painelConcluido = painelTarefas;
        
        JScrollPane scroll = new JScrollPane(painelTarefas);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        coluna.add(scroll, BorderLayout.CENTER);
        
        return coluna;
    }
    // Limpa todas as tarefas da interface
    public void limparQuadro() {
        painelAFazer.removeAll();
        painelEmProgresso.removeAll();
        painelConcluido.removeAll();
        
        painelAFazer.revalidate(); painelAFazer.repaint();
        painelEmProgresso.revalidate(); painelEmProgresso.repaint();
        painelConcluido.revalidate(); painelConcluido.repaint();
    }
    
    // Método que o Controller vai chamar para adicionar um cartão visual
    public void adicionarCartaoTarefa(String titulo, String descricao, String prioridade, String data, String estado, String membro,
                                      java.awt.event.ActionListener acaoEditar, 
                                      java.awt.event.ActionListener acaoEliminar,
                                      java.awt.event.ActionListener acaoMover) {
                                          
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); 
        
        // --- TOPO: Título e Botões ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        JLabel lblTitulo = new JLabel("<html><b>" + titulo + "</b></html>");
        
        JPanel btnBoxes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));
        btnBoxes.setBackground(Color.WHITE);
        
        JButton btnMove = new JButton("➔"); // NOVO: Botão de mover
        JButton btnEdit = new JButton("✎"); 
        JButton btnDelete = new JButton("🗑"); 
        
        btnMove.setMargin(new Insets(2, 4, 2, 4));
        btnEdit.setMargin(new Insets(2, 4, 2, 4));
        btnDelete.setMargin(new Insets(2, 4, 2, 4));
        
        // Ligar os botões às ações passadas pelo Controller
        if (acaoMover != null) btnMove.addActionListener(acaoMover);
        if (acaoEditar != null) btnEdit.addActionListener(acaoEditar);
        if (acaoEliminar != null) btnDelete.addActionListener(acaoEliminar);
        
        btnBoxes.add(btnMove);
        btnBoxes.add(btnEdit);
        btnBoxes.add(btnDelete);
        
        topPanel.add(lblTitulo, BorderLayout.CENTER);
        topPanel.add(btnBoxes, BorderLayout.EAST);
        
        // --- MEIO: Descrição ---
        JLabel lblDescricao = new JLabel("<html><i style='color:gray;'>" + descricao + "</i></html>");
        lblDescricao.setBorder(new EmptyBorder(5, 0, 5, 0));

        // --- BASE: Informações ---
        JPanel infoPanel = new JPanel(new GridLayout(3, 2)); // 3 Linhas, 2 Colunas
        infoPanel.setBackground(Color.WHITE);
        
        // Linha 1: Cabeçalhos
        infoPanel.add(new JLabel("Prioridade: "));
        infoPanel.add(new JLabel("Data: "));
        
        // Linha 2: Valores (COM CORES)
        JLabel lblValorPrioridade = new JLabel(prioridade);
        lblValorPrioridade.setFont(new Font("Arial", Font.BOLD, 12)); // Fica a negrito para destacar a cor
        
        // Aplicar a cor dependendo da prioridade
        if (prioridade.equals("Alta")) {
            lblValorPrioridade.setForeground(Color.RED); // Vermelho
        } else if (prioridade.equals("Média")) {
            lblValorPrioridade.setForeground(new Color(204, 153, 0)); // Amarelo/Laranja escuro (para se ler bem no fundo branco)
        } else if (prioridade.equals("Baixa")) {
            lblValorPrioridade.setForeground(new Color(0, 153, 0)); // Verde escuro
        }
        
        infoPanel.add(lblValorPrioridade); // Adiciona a etiqueta com cor
        infoPanel.add(new JLabel(data));
        
        // Linha 3: Membro
        infoPanel.add(new JLabel("Membro: "));
        infoPanel.add(new JLabel(membro != null && !membro.trim().isEmpty() ? membro : "Sem Membro"));
        
        // Agrupar tudo
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(lblDescricao, BorderLayout.NORTH);
        centerPanel.add(infoPanel, BorderLayout.CENTER);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(centerPanel, BorderLayout.CENTER);
        
        // Adiciona à coluna correta
        if(estado.equals("A Fazer")) {
            painelAFazer.add(card);
            painelAFazer.revalidate(); painelAFazer.repaint();
        } else if(estado.equals("Em Progresso")) {
            painelEmProgresso.add(card);
            painelEmProgresso.revalidate(); painelEmProgresso.repaint();
        } else if(estado.equals("Concluído")) {
            painelConcluido.add(card);
            painelConcluido.revalidate(); painelConcluido.repaint();
        }
    }

    // GETTERS para o Controller 
    public DefaultListModel<String> getListaProjetosModel() { return listaProjetosModel; }
    public JList<String> getListaProjetos() { return listaProjetos; }
    public JButton getBtnNovoProjeto() { return btnNovoProjeto; }
    public JButton getBtnEditarProjeto() { return btnEditarProjeto; }
    public JButton getBtnEliminarProjeto() { return btnEliminarProjeto; }
    public JButton getBtnNovaTarefa() { return btnNovaTarefa; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCarregar() { return btnCarregar; }
    public JButton getBtnEquipa() { return btnEquipa; }
}