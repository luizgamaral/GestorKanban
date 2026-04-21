/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package estga.lp.gestorkanban;

// Importando as classes da biblioteca 
import lp.trabalho1.IODataClass;
import lp.trabalho1.GroupInfo;
import lp.trabalho1.StudentInfo;

// Importando os exemplos
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import estga.lp.gestorkanban.controller.Controlo;
import estga.lp.gestorkanban.view.Interface;
import javax.swing.SwingUtilities;

/*
 *
 * @author luizi
 */

public class GestorKanban {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                // 1. Cria a Interface (View)
                Interface gui = new Interface();
                
                // 2. Cria o Controlo (Controller) e passa a Interface para ele controlar
                Controlo controlo = new Controlo(gui);
                
                // 3. Mostra a interface
                gui.setVisible(true);
            }
        });
    }
}
