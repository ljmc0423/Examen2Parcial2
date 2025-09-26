/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class RegisterFrame extends JFrame {
    private JTextField txtNombre, txtUser;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbTipo;
    private JFormattedTextField fechaNacimiento;
    private JButton btnRegistrar, btnCancelar, btnFoto, btnCalendar, btnRegresar;
    private File fotoSeleccionada;
    private Steam steam;
    private JLabel lblFotoStatus, lblFotoPreview;

    public RegisterFrame(Steam steam) {
        this.steam = steam;

        setTitle("Steam - Registro");
        setSize(650, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ==== Fondo con gradiente azul-verde ====
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(23, 26, 33),   // Azul marino
                        0, getHeight(), new Color(16, 44, 36) // Verde oscuro
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(40, 55, 71, 220));
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        formPanel.setPreferredSize(new Dimension(550, 580));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        // ==== Título ====
        JLabel lblTitle = new JLabel("CREAR CUENTA");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(102, 192, 244));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // ==== Nombre ====
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nombre completo:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1; formPanel.add(txtNombre, gbc);

        // ==== Usuario ====
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Usuario:"), gbc);
        txtUser = new JTextField(20);
        gbc.gridx = 1; formPanel.add(txtUser, gbc);

        // ==== Contraseña ====
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Contraseña:"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1; formPanel.add(txtPassword, gbc);

        // ==== Fecha de nacimiento ====
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Fecha de nacimiento:"), gbc);
        fechaNacimiento = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
        fechaNacimiento.setValue(new Date());
        fechaNacimiento.setColumns(15);
        gbc.gridx = 1; formPanel.add(fechaNacimiento, gbc);

        // ==== Tipo de cuenta ====
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Tipo de cuenta:"), gbc);
        cmbTipo = new JComboBox<>(new String[]{"NORMAL", "ADMIN"});
        gbc.gridx = 1; formPanel.add(cmbTipo, gbc);

        // ==== Foto de perfil ====
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Foto de perfil:"), gbc);

        JPanel fotoPanel = new JPanel(new BorderLayout());
        fotoPanel.setOpaque(false);

        btnFoto = new JButton("Seleccionar foto");
        lblFotoStatus = new JLabel("Ningún archivo seleccionado");
        lblFotoPreview = new JLabel();
        lblFotoPreview.setPreferredSize(new Dimension(120, 120));

        fotoPanel.add(btnFoto, BorderLayout.NORTH);
        fotoPanel.add(lblFotoStatus, BorderLayout.CENTER);
        fotoPanel.add(lblFotoPreview, BorderLayout.SOUTH);

        gbc.gridx = 1; formPanel.add(fotoPanel, gbc);

        // ==== Botones ====
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        buttonPanel.setOpaque(false);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(67, 181, 129));
        btnRegistrar.setForeground(Color.BLACK);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(220, 85, 85));
        btnCancelar.setForeground(Color.BLACK);

        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(102, 192, 244));
        btnRegresar.setForeground(Color.BLACK);

        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnRegresar);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel);
        add(mainPanel);

        // ==== Acciones ====
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> dispose());
        btnFoto.addActionListener(e -> seleccionarFoto());
        btnRegresar.addActionListener(e -> {
            dispose();
            new LoginFrame(steam).setVisible(true);
        });
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

    private void seleccionarFoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes (*.jpg, *.png, *.gif)", "jpg", "png", "gif"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fotoSeleccionada = chooser.getSelectedFile();
            lblFotoStatus.setText("📷 " + fotoSeleccionada.getName());
            ImageIcon icon = new ImageIcon(fotoSeleccionada.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblFotoPreview.setIcon(new ImageIcon(img));
        }
    }
}
