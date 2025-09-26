/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package examenlab2parcial2.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ProfileFrame extends JFrame {
    public JLabel lblFoto, lblNombre, lblUser, lblEdad, lblDescargas;
    public JTable historial;

    private String username;

    public ProfileFrame(String username) {
        this.username = username;

        setTitle("Mi Perfil - " + username);
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
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ==== Panel de información ====
        JPanel infoPanel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(32, 45, 62, 240));
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2d.setColor(new Color(102, 192, 244, 120));
                g2d.setStroke(new BasicStroke(2f));
                g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 20, 20));
                g2d.dispose();
            }
        };
        infoPanel.setOpaque(false);
        infoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ==== Foto de perfil ====
        lblFoto = new JLabel();
        lblFoto.setPreferredSize(new Dimension(120, 120));
        lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblFoto.setBorder(new LineBorder(new Color(102, 192, 244), 2, true));
        lblFoto.setOpaque(true);
        lblFoto.setBackground(new Color(45, 58, 78));
        lblFoto.setIcon(new ImageIcon("default.png")); // placeholder

        // ==== Datos del usuario ====
        JPanel datosPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        datosPanel.setOpaque(false);

        lblNombre = createLabel("Nombre: ---");
        lblUser = createLabel("Usuario: " + username);
        lblEdad = createLabel("Edad: ---");
        lblDescargas = createLabel("Descargas: 0");

        datosPanel.add(lblNombre);
        datosPanel.add(lblUser);
        datosPanel.add(lblEdad);
        datosPanel.add(lblDescargas);

        infoPanel.add(lblFoto, BorderLayout.WEST);
        infoPanel.add(datosPanel, BorderLayout.CENTER);

        // ==== Tabla de historial ====
        historial = new JTable(new Object[][]{}, new String[]{"Fecha", "Download ID", "Juego", "Precio"});
        historial.setBackground(new Color(45, 58, 78));
        historial.setForeground(Color.WHITE);
        historial.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        historial.getTableHeader().setBackground(new Color(30, 30, 30));
        historial.getTableHeader().setForeground(new Color(102, 192, 244));
        historial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane scroll = new JScrollPane(historial);
        scroll.getViewport().setBackground(new Color(32, 45, 62));

        // ==== Ensamblar ====
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return l;
    }
}
