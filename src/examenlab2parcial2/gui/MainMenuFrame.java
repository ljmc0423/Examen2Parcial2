/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private JButton btnLogin, btnRegister, btnGestionJuegos, btnExit;
    private Steam steam;

    public MainMenuFrame(Steam steam) {
        this.steam = steam;

        setTitle("FakeSteam - Menú Principal");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🎨 Panel principal con fondo azul marino degradado
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(10, 25, 47),
                        0, getHeight(), new Color(0, 77, 64)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        // 📦 Panel del menú
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 20, 20));
        menuPanel.setOpaque(false);
        menuPanel.setPreferredSize(new Dimension(400, 350));

        JLabel lblTitle = new JLabel("FAKESTEAM", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(Color.WHITE);

        btnLogin = createButton("Login", new Color(129, 199, 132));
        btnRegister = createButton("Registrar", new Color(100, 181, 246));
        btnGestionJuegos = createButton("Gestión de Juegos", new Color(255, 213, 79));
        btnExit = createButton("Salir", new Color(239, 83, 80));

        menuPanel.add(lblTitle);
        menuPanel.add(btnLogin);
        menuPanel.add(btnRegister);
        menuPanel.add(btnGestionJuegos);
        menuPanel.add(btnExit);

        mainPanel.add(menuPanel);

        add(mainPanel);

        // Eventos
        btnLogin.addActionListener(e -> {
            new LoginFrame(steam).setVisible(true);
            dispose();
        });

        btnRegister.addActionListener(e -> {
            new RegisterFrame(steam).setVisible(true);
            dispose();
        });

        btnGestionJuegos.addActionListener(e -> {
            new GestionJuegosJugadoresFrame().setVisible(true);
            dispose();
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    // 🎨 Botones modernos con texto negro
    private JButton createButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.BLACK);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setFocusPainted(false);
        return b;
    }
}