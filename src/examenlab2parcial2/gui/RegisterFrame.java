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
    private JButton btnRegistrar, btnCancelar, btnFoto, btnRegresar;
    private File fotoSeleccionada;
    private Steam steam;
    private JLabel lblFotoStatus, lblFotoPreview;

    public RegisterFrame(Steam steam) {
        this.steam = steam;

        setTitle("Steam - Registro");
        setSize(650, 750);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fondo con gradiente azul-verde
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(23, 26, 33),
                        0, getHeight(), new Color(16, 44, 36)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(40, 55, 71, 220));
        formPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("CREAR CUENTA");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

// Nombre
gbc.gridx = 0; gbc.gridy = 1;
JLabel lblNombre = new JLabel("Nombre completo");
lblNombre.setForeground(Color.WHITE);
formPanel.add(lblNombre, gbc);

txtNombre = new JTextField(18);
gbc.gridx = 1; formPanel.add(txtNombre, gbc);

// Usuario
gbc.gridx = 0; gbc.gridy = 2;
JLabel lblUser = new JLabel("Usuario");
lblUser.setForeground(Color.WHITE);
formPanel.add(lblUser, gbc);

txtUser = new JTextField(18);
gbc.gridx = 1; formPanel.add(txtUser, gbc);

// Contraseña
gbc.gridx = 0; gbc.gridy = 3;
JLabel lblPass = new JLabel("Contraseña");
lblPass.setForeground(Color.WHITE);
formPanel.add(lblPass, gbc);

txtPassword = new JPasswordField(18);
gbc.gridx = 1; formPanel.add(txtPassword, gbc);

// Fecha de nacimiento
gbc.gridx = 0; gbc.gridy = 4;
JLabel lblFecha = new JLabel("Fecha de nacimiento");
lblFecha.setForeground(Color.WHITE);
formPanel.add(lblFecha, gbc);

fechaNacimiento = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
fechaNacimiento.setValue(new Date());
fechaNacimiento.setColumns(12);
gbc.gridx = 1; formPanel.add(fechaNacimiento, gbc);

// Tipo de cuenta
gbc.gridx = 0; gbc.gridy = 5;
JLabel lblTipo = new JLabel("Tipo de cuenta");
lblTipo.setForeground(Color.WHITE);
formPanel.add(lblTipo, gbc);

cmbTipo = new JComboBox<>(new String[]{"NORMAL", "ADMIN"});
gbc.gridx = 1; formPanel.add(cmbTipo, gbc);

// Foto
gbc.gridx = 0; gbc.gridy = 6;
JLabel lblFoto = new JLabel("Foto de perfil");
lblFoto.setForeground(Color.WHITE);
formPanel.add(lblFoto, gbc);

btnFoto = new JButton("Seleccionar foto");
lblFotoStatus = new JLabel("Ningún archivo seleccionado");
lblFotoStatus.setForeground(Color.WHITE); // 👈 también blanco
gbc.gridx = 1; formPanel.add(btnFoto, gbc);

// Status debajo del botón
gbc.gridx = 1; gbc.gridy = 7;
formPanel.add(lblFotoStatus, gbc);

// Preview debajo de todo el formulario
lblFotoPreview = new JLabel();
lblFotoPreview.setPreferredSize(new Dimension(150, 150));
lblFotoPreview.setHorizontalAlignment(JLabel.CENTER);
lblFotoPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
gbc.anchor = GridBagConstraints.CENTER;
formPanel.add(lblFotoPreview, gbc);

// Botones
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

        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Acciones
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
            new LoginFrame(steam).setVisible(true);
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

            // Mostrar preview redimensionado cuadrado
            ImageIcon icon = new ImageIcon(fotoSeleccionada.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblFotoPreview.setIcon(new ImageIcon(img));
        }
    }
}
