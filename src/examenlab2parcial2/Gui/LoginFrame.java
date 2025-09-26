/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.Gui;


import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public JTextField txtUser;
    public JPasswordField txtPassword;
    public JButton btnLogin, btnRegister;

    public LoginFrame() {
        setTitle("Steam - Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30,30,30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel lblTitle = new JLabel("STEAM LOGIN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth=1; gbc.anchor=GridBagConstraints.WEST;
        gbc.gridx=0; gbc.gridy=1;
        panel.add(label("Usuario:"), gbc);
        txtUser = new JTextField(15);
        gbc.gridx=1; panel.add(txtUser, gbc);

        gbc.gridx=0; gbc.gridy=2;
        panel.add(label("Contraseña:"), gbc);
        txtPassword = new JPasswordField(15);
        gbc.gridx=1; panel.add(txtPassword, gbc);

        btnLogin = button("Entrar");
        btnRegister = button("Registrar");
        gbc.gridx=0; gbc.gridy=3; panel.add(btnLogin, gbc);
        gbc.gridx=1; panel.add(btnRegister, gbc);

        add(panel);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.LIGHT_GRAY);
        return l;
    }

    private JButton button(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(70,130,180));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }
}
