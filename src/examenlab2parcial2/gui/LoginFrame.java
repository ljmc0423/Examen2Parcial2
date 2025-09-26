/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;
    private Steam steam;

    public LoginFrame(Steam steam) {
        this.steam = steam;

        setTitle("Steam - Login");
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
                        0, 0, new Color(10, 25, 47),  // azul marino
                        0, getHeight(), new Color(0, 77, 64) // verde oscuro
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        // 📦 Formulario centrado
        JPanel formPanel = new JPanel(new GridLayout(4,1,15,15));
        formPanel.setOpaque(false);
        formPanel.setPreferredSize(new Dimension(400, 300));

        JLabel lblTitle = new JLabel("INICIO DE SESIÓN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);

        txtUser = new JTextField();
        styleTextField(txtUser, "Usuario");

        txtPassword = new JPasswordField();
        styleTextField(txtPassword, "Contraseña");

        JPanel buttonPanel = new JPanel(new GridLayout(1,2,20,0));
        buttonPanel.setOpaque(false);

        btnLogin = createButton("Entrar", new Color(129, 199, 132)); // verde suave
        btnRegister = createButton("Registrar", new Color(100, 181, 246)); // azul claro
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        formPanel.add(lblTitle);
        formPanel.add(txtUser);
        formPanel.add(txtPassword);
        formPanel.add(buttonPanel);

        mainPanel.add(formPanel);

        add(mainPanel);

        // Eventos
        btnLogin.addActionListener(e -> new AdminFrame(steam).setVisible(true));
        btnRegister.addActionListener(e -> new RegisterFrame(steam).setVisible(true));
    }

    // 🎨 TextFields modernos con placeholder
    private void styleTextField(JTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBackground(new Color(236, 239, 241));
        field.setForeground(Color.DARK_GRAY);
        field.setCaretColor(Color.BLACK);
        field.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        field.setText(placeholder);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.DARK_GRAY);
                }
            }
        });
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

    private void login() {
        String user = txtUser.getText();
        String pass = new String(txtPassword.getPassword());
        try {
            int code = steam.login(user, pass);
            if (code == -1) {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas");
            } else if (steam.isAdmin(code)) {
                new AdminFrame(steam).setVisible(true);
                dispose();
            } else {
                new UserFrame(steam, user).setVisible(true);
                dispose();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
