/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {
    private JButton btnGames, btnPlayers, btnReports, btnCatalog;
    private Steam steam;

    public AdminFrame(Steam steam) {
        this.steam = steam;

        setTitle("Steam - Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2,2,20,20));
        panel.setBackground(new Color(40,40,40));
        btnGames = button("Gestión Juegos");
        btnPlayers = button("Gestión Jugadores");
        btnReports = button("Reportes Clientes");
        btnCatalog = button("Ver Catálogo");
        panel.add(btnGames); panel.add(btnPlayers); panel.add(btnReports); panel.add(btnCatalog);

        add(panel, BorderLayout.CENTER);
    }

    private JButton button(String text){ JButton b=new JButton(text); b.setBackground(new Color(70,130,180)); b.setForeground(Color.WHITE); return b; }
}

