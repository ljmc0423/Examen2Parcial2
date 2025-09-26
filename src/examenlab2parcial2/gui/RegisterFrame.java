/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class RegisterFrame extends JFrame {
    private JTextField txtNombre, txtUser;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbTipo;
    private JFormattedTextField fechaNacimiento;
    private JButton btnRegistrar, btnCancelar, btnFoto, btnCalendar;
    private File fotoSeleccionada;
    private Steam steam;
    private JLabel lblFotoStatus, lblPreview;

    public RegisterFrame(Steam steam) {
        this.steam = steam;

        setTitle("Steam - Registro");
        setSize(700, 750);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(20, 30, 50));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitle = new JLabel("CREAR CUENTA", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(102, 192, 244));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(30, 40, 60));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Nombre completo"), gbc);
        txtNombre = createTextField();
        gbc.gridx = 1; formPanel.add(txtNombre, gbc);

        // Usuario
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Usuario"), gbc);
        txtUser = createTextField();
        gbc.gridx = 1; formPanel.add(txtUser, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Contraseña"), gbc);
        txtPassword = new JPasswordField(20);
        styleTextField(txtPassword);
        gbc.gridx = 1; formPanel.add(txtPassword, gbc);

        // Fecha de nacimiento
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel("Fecha de nacimiento"), gbc);
        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fechaPanel.setBackground(new Color(30, 40, 60));
        fechaNacimiento = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
        fechaNacimiento.setValue(new Date());
        styleTextField(fechaNacimiento);
        fechaNacimiento.setColumns(12);
        fechaNacimiento.setEditable(false);
        btnCalendar = createButton("📅", new Color(102, 192, 244));
        fechaPanel.add(fechaNacimiento);
        fechaPanel.add(btnCalendar);
        gbc.gridx = 1; formPanel.add(fechaPanel, gbc);

        // Tipo de cuenta
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabel("Tipo de cuenta"), gbc);
        cmbTipo = new JComboBox<>(new String[]{"NORMAL", "ADMIN"});
        cmbTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbTipo.setBackground(new Color(50, 70, 90));
        cmbTipo.setForeground(Color.WHITE);
        gbc.gridx = 1; formPanel.add(cmbTipo, gbc);

        // Foto con preview
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createLabel("Foto de perfil"), gbc);
        JPanel fotoPanel = new JPanel(new BorderLayout());
        fotoPanel.setBackground(new Color(30, 40, 60));
        btnFoto = createButton("📁 Seleccionar foto", new Color(68, 130, 180));
        lblFotoStatus = new JLabel("Ningún archivo seleccionado");
        lblFotoStatus.setForeground(Color.LIGHT_GRAY);
        lblFotoStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblPreview = new JLabel("", JLabel.CENTER);
        lblPreview.setPreferredSize(new Dimension(120, 120));
        lblPreview.setBorder(new LineBorder(new Color(102, 192, 244), 2, true));
        fotoPanel.add(btnFoto, BorderLayout.NORTH);
        fotoPanel.add(lblFotoStatus, BorderLayout.CENTER);
        fotoPanel.add(lblPreview, BorderLayout.SOUTH);
        gbc.gridx = 1; formPanel.add(fotoPanel, gbc);

        // Botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setBackground(new Color(30, 40, 60));
        btnRegistrar = createButton("✓ REGISTRAR", new Color(67, 181, 129));
        btnCancelar = createButton("✕ CANCELAR", new Color(220, 85, 85));
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Eventos
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> dispose());
        btnFoto.addActionListener(e -> seleccionarFoto());
        btnCalendar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Aquí debería abrir tu calendario (simple)."));
    }

    // ==== LÓGICA ====

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
            lblFotoStatus.setForeground(new Color(102, 192, 244));

            // Mostrar preview escalado
            ImageIcon icon = new ImageIcon(fotoSeleccionada.getAbsolutePath());
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblPreview.setIcon(new ImageIcon(img));
        }
    }

    // ==== ESTILOS SIMPLES ====

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        styleTextField(field);
        return field;
    }

    private void styleTextField(JTextField field) {
        field.setBackground(new Color(50, 70, 90));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new LineBorder(new Color(70, 85, 105), 2, true));
    }
    
    private void mostrarCalendario() {
    JDialog calendarDialog = new JDialog(this, "Seleccionar fecha", true);
    calendarDialog.setSize(400, 300);
    calendarDialog.setLocationRelativeTo(this);
    calendarDialog.setLayout(new BorderLayout());

    // Panel superior con Año y Mes
    JPanel topPanel = new JPanel(new FlowLayout());
    JLabel lblYear = new JLabel("Año:");
    JComboBox<Integer> yearBox = new JComboBox<>();
    int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    for (int y = 1950; y <= currentYear; y++) {
        yearBox.addItem(y);
    }
    yearBox.setSelectedItem(currentYear);

    JLabel lblMonth = new JLabel("Mes:");
    String[] months = {"Enero","Febrero","Marzo","Abril","Mayo","Junio",
                       "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    JComboBox<String> monthBox = new JComboBox<>(months);
    monthBox.setSelectedIndex(java.util.Calendar.getInstance().get(java.util.Calendar.MONTH));

    topPanel.add(lblYear);
    topPanel.add(yearBox);
    topPanel.add(lblMonth);
    topPanel.add(monthBox);

    // Panel central con los días
    JPanel daysPanel = new JPanel(new GridLayout(6, 7, 5, 5));
    JButton[] dayButtons = new JButton[42];

    for (int i = 0; i < 42; i++) {
        dayButtons[i] = new JButton();
        dayButtons[i].setBackground(new Color(200, 230, 250));
        int index = i;
        dayButtons[i].addActionListener(e -> {
            if (!dayButtons[index].getText().isEmpty()) {
                int day = Integer.parseInt(dayButtons[index].getText());
                int year = (int) yearBox.getSelectedItem();
                int month = monthBox.getSelectedIndex();
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.set(year, month, day);
                fechaNacimiento.setValue(cal.getTime()); // 👈 Poner fecha seleccionada
                calendarDialog.dispose();
            }
        });
        daysPanel.add(dayButtons[i]);
    }

    // Función para actualizar el calendario
    Runnable updateDays = () -> {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set((int) yearBox.getSelectedItem(), monthBox.getSelectedIndex(), 1);
        int firstDay = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        for (int i = 0; i < 42; i++) {
            if (i < firstDay || i >= firstDay + daysInMonth) {
                dayButtons[i].setText("");
                dayButtons[i].setEnabled(false);
            } else {
                dayButtons[i].setText(String.valueOf(i - firstDay + 1));
                dayButtons[i].setEnabled(true);
            }
        }
    };

    yearBox.addActionListener(e -> updateDays.run());
    monthBox.addActionListener(e -> updateDays.run());
    updateDays.run();

    calendarDialog.add(topPanel, BorderLayout.NORTH);
    calendarDialog.add(daysPanel, BorderLayout.CENTER);
    calendarDialog.setVisible(true);
}


    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
