/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class AdminFrame extends JFrame {
    private JButton btnGames, btnPlayers, btnReports, btnCatalog, btnRegresar;
    private Steam steam;

    public AdminFrame(Steam steam) {
        this.steam = steam;

        setTitle("Steam - Administrador");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ==== Fondo con gradiente ====
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(21, 24, 31),
                        0, getHeight(), new Color(14, 26, 41)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        // ==== Panel central con sombra y borde redondeado ====
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(new Color(0, 0, 0, 80));
                g2d.fill(new RoundRectangle2D.Float(5, 5, getWidth()-10, getHeight()-10, 25, 25));

                g2d.setColor(new Color(32, 45, 62, 240));
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 25, 25));

                g2d.setColor(new Color(102, 192, 244, 120));
                g2d.setStroke(new BasicStroke(2f));
                g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 25, 25));

                g2d.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));
        panel.setPreferredSize(new Dimension(550, 380));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        // ==== Título ====
        JLabel lblTitle = new JLabel("Panel de Administración");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(102, 192, 244));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitle, gbc);

        // ==== Botones ====
        btnGames   = createModernButton("Gestión Juegos", new Color(67, 181, 129));
        btnPlayers = createModernButton("Gestión Jugadores", new Color(102, 192, 244));
        btnReports = createModernButton("Reportes Clientes", new Color(220, 120, 85));
        btnCatalog = createModernButton("Ver Catálogo", new Color(123, 104, 238));
        btnRegresar = createModernButton("Regresar", new Color(255, 255, 255));
        btnRegresar.setForeground(Color.BLACK);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(btnGames, gbc);
        gbc.gridy = 1; gbc.gridx = 1; panel.add(btnPlayers, gbc);
        gbc.gridy = 2; gbc.gridx = 0; panel.add(btnReports, gbc);
        gbc.gridy = 2; gbc.gridx = 1; panel.add(btnCatalog, gbc);

        gbc.gridwidth = 2;
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(btnRegresar, gbc);

        mainPanel.add(panel);
        add(mainPanel);
    }

    private JButton createModernButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color current = baseColor;
                if (getModel().isPressed()) current = baseColor.darker();
                else if (getModel().isRollover()) current = baseColor.brighter();
                g2d.setColor(current);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(220, 70));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
