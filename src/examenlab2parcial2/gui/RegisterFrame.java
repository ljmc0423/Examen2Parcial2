/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;

public class RegisterFrame extends JFrame {
    private JTextField txtNombre, txtUser;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbTipo;
    private JFormattedTextField fechaNacimiento;
    private JButton btnRegistrar, btnCancelar, btnFoto, btnCalendar;
    private File fotoSeleccionada;
    private Steam steam;
    private JLabel lblFotoStatus;

    public RegisterFrame(Steam steam) {
        this.steam = steam;

        setTitle("Steam - Registro");
        setSize(650, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fondo con gradiente mejorado
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradiente más suave
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

        // Panel central con mejor diseño
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sombra
                g2d.setColor(new Color(0, 0, 0, 80));
                g2d.fill(new RoundRectangle2D.Float(5, 5, getWidth()-10, getHeight()-10, 30, 30));
                
                // Fondo principal
                g2d.setColor(new Color(32, 45, 62, 250));
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 25, 25));
                
                // Borde sutil
                g2d.setColor(new Color(102, 192, 244, 100));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 25, 25));
                g2d.dispose();
            }
        };
        formPanel.setOpaque(false);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        formPanel.setPreferredSize(new Dimension(550, 580));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        // Título mejorado
        JLabel lblTitle = new JLabel("CREAR CUENTA");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(102, 192, 244));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 30, 0);
        formPanel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 8, 8, 8);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Nombre completo"), gbc);
        txtNombre = createModernTextField("Ingresa tu nombre completo");
        gbc.gridx = 1; formPanel.add(txtNombre, gbc);

        // Usuario
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Nombre de usuario"), gbc);
        txtUser = createModernTextField("Elige un nombre de usuario único");
        gbc.gridx = 1; formPanel.add(txtUser, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel("Contraseña"), gbc);
        txtPassword = createModernPasswordField("Crea una contraseña segura");
        gbc.gridx = 1; formPanel.add(txtPassword, gbc);

        // Fecha con calendario
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabel("Fecha de nacimiento"), gbc);
        
        JPanel fechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        fechaPanel.setOpaque(false);
        
        fechaNacimiento = new JFormattedTextField(new java.text.SimpleDateFormat("dd/MM/yyyy"));
        fechaNacimiento.setValue(new Date());
        styleModernField(fechaNacimiento);
        fechaNacimiento.setColumns(12);
        fechaNacimiento.setEditable(false); // Solo lectura, se modifica con calendario
        
        btnCalendar = createCalendarButton();
        
        fechaPanel.add(fechaNacimiento);
        fechaPanel.add(Box.createHorizontalStrut(10));
        fechaPanel.add(btnCalendar);
        
        gbc.gridx = 1; formPanel.add(fechaPanel, gbc);

        // Tipo usuario mejorado
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createLabel("Tipo de cuenta"), gbc);
        cmbTipo = createModernComboBox();
        gbc.gridx = 1; formPanel.add(cmbTipo, gbc);

        // Foto mejorada
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(createLabel("Foto de perfil"), gbc);
        
        JPanel fotoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        fotoPanel.setOpaque(false);
        
        btnFoto = createModernButton("📁 Seleccionar foto", new Color(68, 130, 180));
        lblFotoStatus = new JLabel("Ningún archivo seleccionado");
        lblFotoStatus.setForeground(new Color(160, 160, 160));
        lblFotoStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        
        fotoPanel.add(btnFoto);
        fotoPanel.add(Box.createVerticalStrut(5));
        
        JPanel fotoContainer = new JPanel(new BorderLayout());
        fotoContainer.setOpaque(false);
        fotoContainer.add(fotoPanel, BorderLayout.NORTH);
        fotoContainer.add(lblFotoStatus, BorderLayout.SOUTH);
        
        gbc.gridx = 1; formPanel.add(fotoContainer, gbc);

        // Botones finales mejorados
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        btnRegistrar = createModernButton("✓ REGISTRAR", new Color(67, 181, 129));
        btnCancelar = createModernButton("✕ CANCELAR", new Color(220, 85, 85));
        
        buttonPanel.add(btnRegistrar);
        buttonPanel.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);

        mainPanel.add(formPanel);
        add(mainPanel);

        // Acciones
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> dispose());
        btnFoto.addActionListener(e -> seleccionarFoto());
        btnCalendar.addActionListener(e -> mostrarCalendario());
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
            lblFotoStatus.setForeground(new Color(102, 192, 244));
        }
    }

    private void mostrarCalendario() {
        // Crear un diálogo personalizado con calendario estilo Windows
        JDialog calendarDialog = new JDialog(this, "Seleccionar fecha de nacimiento", true);
        calendarDialog.setSize(400, 450);
        calendarDialog.setLocationRelativeTo(this);
        calendarDialog.setResizable(false);
        
        // Panel principal del calendario
        JPanel calendarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(32, 45, 62),
                        0, getHeight(), new Color(25, 35, 50)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        calendarPanel.setLayout(new BorderLayout());
        calendarPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Obtener fecha actual del campo
        Calendar cal = Calendar.getInstance();
        if (fechaNacimiento.getValue() != null) {
            cal.setTime((Date) fechaNacimiento.getValue());
        }
        
        // Panel superior con selectores
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        headerPanel.setOpaque(false);
        
        JLabel lblYear = new JLabel("Año:");
        lblYear.setForeground(Color.WHITE);
        lblYear.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JComboBox<Integer> yearBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = 1950; year <= currentYear; year++) {
            yearBox.addItem(year);
        }
        yearBox.setSelectedItem(cal.get(Calendar.YEAR));
        styleComboBox(yearBox);
        
        JLabel lblMonth = new JLabel("Mes:");
        lblMonth.setForeground(Color.WHITE);
        lblMonth.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                          "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        JComboBox<String> monthBox = new JComboBox<>(months);
        monthBox.setSelectedIndex(cal.get(Calendar.MONTH));
        styleComboBox(monthBox);
        
        headerPanel.add(lblYear);
        headerPanel.add(yearBox);
        headerPanel.add(Box.createHorizontalStrut(20));
        headerPanel.add(lblMonth);
        headerPanel.add(monthBox);
        
        // Panel de días de la semana
        JPanel weekPanel = new JPanel(new GridLayout(1, 7, 2, 2));
        weekPanel.setOpaque(false);
        weekPanel.setBorder(new EmptyBorder(10, 0, 5, 0));
        
        String[] weekDays = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        for (String day : weekDays) {
            JLabel dayLabel = new JLabel(day, JLabel.CENTER);
            dayLabel.setForeground(new Color(102, 192, 244));
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            weekPanel.add(dayLabel);
        }
        
        // Panel para los días del mes
        JPanel daysPanel = new JPanel(new GridLayout(6, 7, 2, 2));
        daysPanel.setOpaque(false);
        
        // Array de botones para los días
        JButton[] dayButtons = new JButton[42];
        for (int i = 0; i < 42; i++) {
            dayButtons[i] = new JButton();
            dayButtons[i].setPreferredSize(new Dimension(45, 35));
            styleCalendarDayButton(dayButtons[i]);
            
            final int dayIndex = i;
            dayButtons[i].addActionListener(e -> {
                if (!dayButtons[dayIndex].getText().isEmpty() && dayButtons[dayIndex].isEnabled()) {
                    int selectedDay = Integer.parseInt(dayButtons[dayIndex].getText());
                    Calendar newDate = Calendar.getInstance();
                    newDate.set((Integer) yearBox.getSelectedItem(), 
                              monthBox.getSelectedIndex(), 
                              selectedDay);
                    fechaNacimiento.setValue(newDate.getTime());
                    calendarDialog.dispose();
                }
            });
            daysPanel.add(dayButtons[i]);
        }
        
        // Función para actualizar los días mostrados
        Runnable updateCalendar = () -> {
            Calendar tempCal = Calendar.getInstance();
            tempCal.set((Integer) yearBox.getSelectedItem(), monthBox.getSelectedIndex(), 1);
            
            int firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1;
            int daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            
            // Limpiar todos los botones
            for (int i = 0; i < 42; i++) {
                dayButtons[i].setText("");
                dayButtons[i].setEnabled(false);
                dayButtons[i].setBackground(new Color(45, 58, 78));
            }
            
            // Llenar días del mes actual
            for (int day = 1; day <= daysInMonth; day++) {
                int buttonIndex = firstDayOfWeek + day - 1;
                if (buttonIndex < 42) {
                    dayButtons[buttonIndex].setText(String.valueOf(day));
                    dayButtons[buttonIndex].setEnabled(true);
                    
                    // Resaltar día seleccionado actualmente
                    Calendar currentSelected = Calendar.getInstance();
                    if (fechaNacimiento.getValue() != null) {
                        currentSelected.setTime((Date) fechaNacimiento.getValue());
                        if (currentSelected.get(Calendar.YEAR) == (Integer) yearBox.getSelectedItem() &&
                            currentSelected.get(Calendar.MONTH) == monthBox.getSelectedIndex() &&
                            currentSelected.get(Calendar.DAY_OF_MONTH) == day) {
                            dayButtons[buttonIndex].setBackground(new Color(102, 192, 244));
                        }
                    }
                }
            }
        };
        
        yearBox.addActionListener(e -> updateCalendar.run());
        monthBox.addActionListener(e -> updateCalendar.run());
        updateCalendar.run();
        
        // Panel inferior con botones
        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setOpaque(false);
        
        JButton btnHoy = createModernButton("Hoy", new Color(102, 192, 244));
        btnHoy.addActionListener(e -> {
            Calendar today = Calendar.getInstance();
            yearBox.setSelectedItem(today.get(Calendar.YEAR));
            monthBox.setSelectedIndex(today.get(Calendar.MONTH));
            updateCalendar.run();
        });
        
        JButton btnCerrar = createModernButton("Cerrar", new Color(120, 120, 120));
        btnCerrar.addActionListener(e -> calendarDialog.dispose());
        
        footerPanel.add(btnHoy);
        footerPanel.add(Box.createHorizontalStrut(10));
        footerPanel.add(btnCerrar);
        
        // Ensamblar el calendario
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(weekPanel, BorderLayout.NORTH);
        centerPanel.add(daysPanel, BorderLayout.CENTER);
        
        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        calendarPanel.add(centerPanel, BorderLayout.CENTER);
        calendarPanel.add(footerPanel, BorderLayout.SOUTH);
        
        calendarDialog.add(calendarPanel);
        calendarDialog.setVisible(true);
    }

    // ==== MÉTODOS DE ESTILO MEJORADOS ====

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(230, 230, 230));
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !hasFocus()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(new Color(120, 120, 120));
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    g2.drawString(placeholder, 10, getHeight() / 2 + 5);
                    g2.dispose();
                }
            }
        };
        styleModernField(field);
        addFocusEffects(field);
        return field;
    }

    private JPasswordField createModernPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getPassword().length == 0 && !hasFocus()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(new Color(120, 120, 120));
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    g2.drawString(placeholder, 10, getHeight() / 2 + 5);
                    g2.dispose();
                }
            }
        };
        styleModernField(field);
        addFocusEffects(field);
        return field;
    }

    private void styleModernField(JTextField field) {
        field.setOpaque(true);
        field.setBackground(new Color(45, 58, 78));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(102, 192, 244));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(70, 85, 105), 2, true),
                new EmptyBorder(8, 12, 8, 12)
        ));
    }

    private void addFocusEffects(JTextField field) {
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(new CompoundBorder(
                        new LineBorder(new Color(102, 192, 244), 2, true),
                        new EmptyBorder(8, 12, 8, 12)
                ));
                field.setBackground(new Color(50, 65, 88));
                field.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(new CompoundBorder(
                        new LineBorder(new Color(70, 85, 105), 2, true),
                        new EmptyBorder(8, 12, 8, 12)
                ));
                field.setBackground(new Color(45, 58, 78));
                field.repaint();
            }
        });
    }

    private JComboBox<String> createModernComboBox() {
        JComboBox<String> combo = new JComboBox<>(new String[]{"NORMAL", "ADMIN"});
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(new Color(45, 58, 78));
        combo.setForeground(Color.WHITE);
        combo.setBorder(new CompoundBorder(
                new LineBorder(new Color(70, 85, 105), 2, true),
                new EmptyBorder(8, 12, 8, 12)
        ));
        return combo;
    }

    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(bgColor.brighter());
                } else {
                    g2d.setColor(bgColor);
                }
                
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 40));
        
        return button;
    }

    private JButton createCalendarButton() {
        JButton btn = new JButton("📅");
        btn.setBackground(new Color(102, 192, 244));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(new Color(80, 160, 220), 2, true));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(35, 35));
        return btn;
    }

    private void styleCalendarDayButton(JButton button) {
        button.setBackground(new Color(45, 58, 78));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(70, 85, 105), 1, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled() && !button.getText().isEmpty()) {
                    button.setBackground(new Color(102, 192, 244));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (button.isEnabled() && !button.getBackground().equals(new Color(102, 192, 244))) {
                    button.setBackground(new Color(45, 58, 78));
                }
            }
        });
    }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        combo.setBackground(new Color(45, 58, 78));
        combo.setForeground(Color.WHITE);
        combo.setBorder(new LineBorder(new Color(70, 85, 105), 1, true));
        combo.setPreferredSize(new Dimension(100, 30));
    }
}