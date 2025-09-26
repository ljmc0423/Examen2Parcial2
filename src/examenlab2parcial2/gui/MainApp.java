/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;

import examenlab2parcial2.gui.LoginFrame;

public class MainApp {
    public static void main(String[] args) {
        Steam steam = new Steam();
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame(steam).setVisible(true);
        });
    }
}
