/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LoginFrame extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;
    private Steam steam;
    
    public LoginFrame(Steam steam) {
        this.steam = steam;
        setTitle("Steam - Login");
        setSize(550, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal con gradiente Steam
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Gradiente de fondo estilo Steam
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(23, 26, 33),
                    0, getHeight(), new Color(16, 29, 44)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Patrón de puntos sutil
                g2d.setColor(new Color(255, 255, 255, 8));
                for (int x = 0; x < getWidth(); x += 40) {
                    for (int y = 0; y < getHeight(); y += 40) {
                        g2d.fillOval(x, y, 2, 2);
                    }
                }
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        
        // Panel del formulario con diseño moderno
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Sombra del panel
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fill(new RoundRectangle2D.Float(5, 5, getWidth()-5, getHeight()-5, 25, 25));
                
                // Fondo del panel principal
                g2d.setColor(new Color(27, 40, 56, 240));
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth()-5, getHeight()-5, 25, 25));
                
                // Borde con gradiente
                GradientPaint borderGradient = new GradientPaint(
                    0, 0, new Color(102, 192, 244, 100),
                    getWidth(), getHeight(), new Color(76, 175, 80, 50)
                );
                g2d.setPaint(borderGradient);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth()-7, getHeight()-7, 25, 25));
            }
        };
        formPanel.setOpaque(false);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        formPanel.setPreferredSize(new Dimension(400, 350));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Logo/Título con efecto Steam
        JLabel lblTitle = new JLabel("STEAM") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Sombra del texto
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2;
                g2d.drawString(getText(), x + 2, y + 2);
                
                // Texto principal con gradiente
                GradientPaint textGradient = new GradientPaint(
                    0, 0, new Color(102, 192, 244),
                    0, getHeight(), new Color(76, 175, 80)
                );
                g2d.setPaint(textGradient);
                g2d.drawString(getText(), x, y);
            }
        };
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setPreferredSize(new Dimension(200, 50));
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(lblTitle, gbc);
        
        // Subtítulo
        JLabel lblSubtitle = new JLabel("LOGIN");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(180, 180, 180));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 15, 25, 15);
        formPanel.add(lblSubtitle, gbc);
        
        // Reset para campos
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 15, 10, 15);
        
        // Campo Usuario
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createModernLabel("Usuario:"), gbc);
        
        txtUser = createModernTextField("Ingresa tu usuario");
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(txtUser, gbc);
        
        // Campo Contraseña
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(createModernLabel("Contraseña:"), gbc);
        
        txtPassword = createModernPasswordField("Ingresa tu contraseña");
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(txtPassword, gbc);
        
        // Espaciador
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 15, 10, 15);
        formPanel.add(Box.createVerticalStrut(10), gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        
        btnLogin = createModernButton("Entrar", new Color(76, 175, 80), new Color(56, 142, 60));
        btnRegister = createModernButton("Registrar", new Color(102, 192, 244), new Color(68, 138, 255));
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);
        
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);
        
        // Agregar panel al main
        mainPanel.add(formPanel);
        add(mainPanel);
        
        // Acciones (tu lógica original)
        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> new RegisterFrame(steam).setVisible(true));
        
        // Enter key para login
        getRootPane().setDefaultButton(btnLogin);
    }
    
    private JLabel createModernLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(200, 200, 200));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }
    
    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField(20) {
            private boolean showingPlaceholder = true;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo del campo
                if (hasFocus()) {
                    g2d.setColor(new Color(45, 55, 72));
                } else {
                    g2d.setColor(new Color(35, 45, 62));
                }
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                
                // Borde
                if (hasFocus()) {
                    g2d.setColor(new Color(102, 192, 244));
                } else {
                    g2d.setColor(new Color(70, 80, 100));
                }
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 12, 12));
                
                super.paintComponent(g);
            }
        };
        
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(102, 192, 244));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new EmptyBorder(12, 15, 12, 15));
        
        // Placeholder logic
        field.setText(placeholder);
        field.setForeground(new Color(120, 120, 120));
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.WHITE);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(120, 120, 120));
                }
            }
        });
        
        return field;
    }
    
    private JPasswordField createModernPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo del campo
                if (hasFocus()) {
                    g2d.setColor(new Color(45, 55, 72));
                } else {
                    g2d.setColor(new Color(35, 45, 62));
                }
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                
                // Borde
                if (hasFocus()) {
                    g2d.setColor(new Color(102, 192, 244));
                } else {
                    g2d.setColor(new Color(70, 80, 100));
                }
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 12, 12));
                
                super.paintComponent(g);
            }
        };
        
        field.setOpaque(false);
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(102, 192, 244));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new EmptyBorder(12, 15, 12, 15));
        
        return field;
    }
    
    private JButton createModernButton(String text, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text) {
            private boolean isHovered = false;
            
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Color currentColor = baseColor;
                if (getModel().isPressed()) {
                    currentColor = baseColor.darker().darker();
                } else if (isHovered) {
                    currentColor = hoverColor;
                }
                
                // Sombra del botón
                g2d.setColor(new Color(0, 0, 0, 60));
                g2d.fill(new RoundRectangle2D.Float(2, 2, getWidth()-2, getHeight()-2, 15, 15));
                
                // Gradiente del botón
                GradientPaint gradient = new GradientPaint(
                    0, 0, currentColor,
                    0, getHeight(), currentColor.darker()
                );
                g2d.setPaint(gradient);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth()-2, getHeight()-2, 15, 15));
                
                // Highlight superior
                g2d.setColor(new Color(255, 255, 255, 40));
                g2d.fill(new RoundRectangle2D.Float(2, 2, getWidth()-6, getHeight()/3, 13, 13));
                
                // Texto del botón
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                
                // Sombra del texto
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.drawString(getText(), x + 1, y + 1);
                
                // Texto principal
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efectos hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                ((JButton)e.getSource()).putClientProperty("isHovered", true);
                button.repaint();
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                ((JButton)e.getSource()).putClientProperty("isHovered", false);
                button.repaint();
            }
        });
        
        return button;
    }
    
    // Tu lógica original se mantiene intacta
    private void login() {
        String user = txtUser.getText();
        String pass = new String(txtPassword.getPassword());
        
        // Verificar placeholder
        if (user.equals("Ingresa tu usuario")) {
            user = "";
        }
        
        try {
            String tipo = validarLogin(user, pass);
            if (tipo == null) {
                JOptionPane.showMessageDialog(this, "Credenciales inválidas");
            } else if (tipo.equals("ADMIN")) {
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
    
    private String validarLogin(String user, String pass) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("steam/player.stm", "rw");
        raf.seek(0);
        while (raf.getFilePointer() < raf.length()) {
            int code = raf.readInt();
            String u = raf.readUTF();
            String p = raf.readUTF();
            String nombre = raf.readUTF();
            long nac = raf.readLong();
            int downloads = raf.readInt();
            int imgSize = raf.readInt();
            raf.skipBytes(imgSize);
            String tipo = raf.readUTF();
            boolean estado = raf.readBoolean();
            if (u.equals(user) && p.equals(pass) && estado) {
                raf.close();
                return tipo;
            }
        }
        raf.close();
        return null;
    }
}