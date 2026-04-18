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
public class Interface extends JFrame{
    //Atributos para podermos manipular a lista em qualquer botão
    private DefaultListModel<String> listaProjetosModel;
    private JList<String> listaProjetos;
    
    public Interface() {
        setTitle("Gestor Kanban - TP2");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra a janela no ecrã
        setLayout(new BorderLayout()); //Define o layout principal
        initUI(); //Inicializa os componentes
    }
    
    private void initUI(){
        //Barra Lateral (Projetos)
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);
        
        //Área Principal (Topo + Kanban Board)
        JPanel mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(new Color(240, 244, 248));
        
        JPanel topBar = createTopBar();
        mainArea.add(topBar, BorderLayout.NORTH);
        
        JPanel kanbanBoard = createKanbanBoard();
        mainArea.add(kanbanBoard, BorderLayout.CENTER);
        
        add(mainArea, BorderLayout.CENTER);
    }
    
    //Métodos de Construção da Interface
    private JPanel createSidebar(){
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(Color.WHITE);
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        //Topo da barra lateral (Botões)
        JPanel panelBotoes = new JPanel(new GridLayout(2, 1, 5, 5));
        panelBotoes.setBackground(Color.WHITE);
        
        JButton btnNovoProjeto = new JButton("+ NOVO PROJETO");
        btnNovoProjeto.setBackground(new Color(70, 130, 180));
        btnNovoProjeto.setForeground(Color.WHITE);
        
        btnNovoProjeto.addActionListener(e -> {
            String nomeProjeto = JOptionPane.showInputDialog(this, "Introduza o nome do novo projeto: ");
            if (nomeProjeto != null && !nomeProjeto.trim().isEmpty()){
                listaProjetosModel.addElement(nomeProjeto.toUpperCase());
            }
        });
        
        JPanel subBotoes = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnEditar = new JButton("EDITAR");
        JButton btnEliminar = new JButton("ELIMINAR");
        
        btnEditar.addActionListener(e -> {
            int index = listaProjetos.getSelectedIndex();
            if (index != -1){
                String projetoAtual = listaProjetosModel.getElementAt(index);
                String novoNome = JOptionPane.showInputDialog(this, "Editar nome do projeto: ", projetoAtual);
                if (novoNome != null && !novoNome.trim().isEmpty()){
                    listaProjetosModel.set(index, novoNome.toUpperCase());
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Por favor, selecione um projeto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        btnEliminar.addActionListener(e -> {
           int index =  listaProjetos.getSelectedIndex();
           if (index != -1){
               int confirmacao = JOptionPane.showConfirmDialog(this, "Tem a certeza que deseja eliminar este projeto?", "Confirmar Eliminação", JOptionPane.YES_NO_OPTION);
               if (confirmacao == JOptionPane.YES_OPTION){
                   listaProjetosModel.remove(index);
               }
           } else{
               JOptionPane.showMessageDialog(this, "Por favor, selecione um projeto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
           }
        });
        
        subBotoes.add(btnEditar);
        subBotoes.add(btnEliminar);
        
        panelBotoes.add(btnNovoProjeto);
        panelBotoes.add(subBotoes);
        
        sidebar.add(panelBotoes, BorderLayout.NORTH);
        
        //Lista de Projetos (Mockup)
        DefaultListModel<String> listaProjetosModel = new DefaultListModel<>();
        listaProjetosModel = new DefaultListModel<>();
        
        JList<String> listaProjetos = new JList<>(listaProjetosModel);
        listaProjetos = new JList<>(listaProjetosModel);
        
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
        
        //Títulos à esquerda
        JPanel panelTitulos = new JPanel(new GridLayout(2, 1));
        panelTitulos.setOpaque(false);
        JLabel tituloGeral = new JLabel("QUANBO TP2");
        tituloGeral.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel tituloProjeto = new JLabel("IMPLEMENTAÇÃO TP2 (LP)");
        tituloProjeto.setFont(new Font ("Arial", Font.BOLD, 14));
        panelTitulos.add(tituloGeral);
        panelTitulos.add(tituloProjeto);
        
        //Botões de configuração à direita
        JPanel panelBotoesDir = new JPanel(new GridLayout(2, 2, 10, 10));
        panelBotoesDir.setOpaque(false);
        
        JButton btnConfig = new JButton("⚙ CONFIGURAÇÕES DO PROJETO");
        JButton btnEquipa = new JButton("👥 GERIR EQUIPA");
        JButton btnGuardar = new JButton("💾 GUARDAR DADOS");
        JButton btnCarregar = new JButton("📂 CARREGAR DADOS");
        
        btnConfig.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir janela de Configurações..."));
        btnEquipa.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir janela de Gestão de Equipas..."));
        btnGuardar.addActionListener(e -> JOptionPane.showMessageDialog(this, "A guardar dados..."));
        btnCarregar.addActionListener(e -> JOptionPane.showMessageDialog(this, "A carregar dados..."));
        
        panelBotoesDir.add(btnConfig);
        panelBotoesDir.add(btnEquipa);
        panelBotoesDir.add(btnGuardar);
        panelBotoesDir.add(btnCarregar);
        
        topBar.add(panelTitulos, BorderLayout.WEST);
        topBar.add(panelBotoesDir, BorderLayout.EAST);
        return topBar;
    }
    
    private JPanel createKanbanBoard(){
        //Grid com 3 colunas, com espaço de 15px entre elas
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
        
        //Título da Coluna
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBorder(new EmptyBorder(10, 0, 10, 0));
        coluna.add(lblTitulo, BorderLayout.NORTH);
        
        //Área onde ficam os cartões (Tarefas)
        JPanel painelTarefas = new JPanel();
        painelTarefas.setLayout(new BoxLayout(painelTarefas, BoxLayout.Y_AXIS));
        painelTarefas.setBackground(new Color(225, 230, 235));
        
        //Adicionar scroll caso haja muitas tarfeas
        JScrollPane scroll = new JScrollPane(painelTarefas);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        coluna.add(scroll, BorderLayout.CENTER);
        
        return coluna;
    }
    
    //Cria um "Cartão" visual de tarefa para preencher o design
    private JPanel createTaskCardMockup(String titulo, String prioridade, String data){
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); //Limita a altura do cartão
        
        JLabel lblTitulo = new JLabel("<html><b>" + titulo + "</b></html>");
        card.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel infoPanel = new JPanel(new GridLayout(2, 2));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(new JLabel("Prioridade: "));
        infoPanel.add(new JLabel("Data: "));
        infoPanel.add(new JLabel(prioridade));
        infoPanel.add(new JLabel(data));
        
        card.add(infoPanel, BorderLayout.CENTER);
        return card;
    }
}
