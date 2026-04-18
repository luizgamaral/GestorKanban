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

import estga.lp.gestorkanban.view.Interface;
import javax.swing.SwingUtilities;


/*
 *
 * @author luizi
 */

public class GestorKanban {

    public static void main(String[] args) {
        //Iniciar a interface Swing
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                Interface gui = new Interface();
                gui.setVisible(true);
            }
        });
    }
}
