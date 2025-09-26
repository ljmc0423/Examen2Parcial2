/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Steam steam = new Steam(); // inicializa tu clase Steam
                new MainMenuFrame(steam).setVisible(true); // abrimos menú principal
            } catch (Exception e) {
                e.getMessage();
                JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación:\n" + e.getMessage());
            }
        });
    }
}

