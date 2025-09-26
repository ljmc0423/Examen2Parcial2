/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class RegisterFrame extends JFrame {
    private JTextField txtNombre, txtUser;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbTipo;
    private JButton btnRegistrar, btnCancelar, btnFoto;
    private JSpinner fechaNacimiento;
    private File fotoSeleccionada;
    private Steam steam;

    public RegisterFrame(Steam steam) {
        this.steam = steam;

        setTitle("Steam - Registro");
        setSize(500, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30,30,30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.anchor = GridBagConstraints.WEST;

        int y=0;
        panel.add(label("Nombre:"), gbc(0,y)); txtNombre = new JTextField(20); panel.add(txtNombre, gbc(1,y++));
        panel.add(label("Usuario:"), gbc(0,y)); txtUser = new JTextField(20); panel.add(txtUser, gbc(1,y++));
        panel.add(label("Contraseña:"), gbc(0,y)); txtPassword = new JPasswordField(20); panel.add(txtPassword, gbc(1,y++));
        panel.add(label("Fecha Nac.:"), gbc(0,y)); fechaNacimiento = new JSpinner(new SpinnerDateModel()); panel.add(fechaNacimiento, gbc(1,y++));
        panel.add(label("Tipo:"), gbc(0,y)); cmbTipo = new JComboBox<>(new String[]{"NORMAL","ADMIN"}); panel.add(cmbTipo, gbc(1,y++));
        panel.add(label("Foto:"), gbc(0,y)); btnFoto = button("Subir Foto"); panel.add(btnFoto, gbc(1,y++));

        JPanel botones = new JPanel();
        btnRegistrar = button("Registrar");
        btnCancelar = button("Cancelar");
        botones.setBackground(new Color(30,30,30));
        botones.add(btnRegistrar); botones.add(btnCancelar);

        gbc.gridx=0; gbc.gridy=y; gbc.gridwidth=2;
        panel.add(botones, gbc);

        add(panel);

        // Botón foto
        btnFoto.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                fotoSeleccionada = chooser.getSelectedFile();
            }
        });

        // Botón registrar
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void registrarUsuario() {
        try {
            String nombre = txtNombre.getText();
            String user = txtUser.getText();
            String pass = new String(txtPassword.getPassword());
            Date fecha = (Date) fechaNacimiento.getValue();
            String tipo = (String) cmbTipo.getSelectedItem();

            byte[] img = null;
            if (fotoSeleccionada != null) {
                FileInputStream fis = new FileInputStream(fotoSeleccionada);
                img = fis.readAllBytes();
                fis.close();
            }

            steam.addPlayer(user, pass, nombre, fecha.getTime(), img, tipo, true);
            JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
            dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error registrando: " + ex.getMessage());
        }
    }

    private JLabel label(String text){ JLabel l=new JLabel(text); l.setForeground(Color.LIGHT_GRAY); return l; }
    private JButton button(String text){ JButton b=new JButton(text); b.setBackground(new Color(70,130,180)); b.setForeground(Color.WHITE); return b; }
    private GridBagConstraints gbc(int x,int y){ GridBagConstraints g=new GridBagConstraints(); g.gridx=x; g.gridy=y; g.insets=new Insets(8,8,8,8); g.anchor=GridBagConstraints.WEST; return g; }
}
