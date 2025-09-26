/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;


import examenlab2parcial2.Steam;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DownloadFrame extends JFrame {
    private JTextField txtCodigo;
    private JButton btnDescargar;
    private Steam steam;
    private String username;

    public DownloadFrame(Steam steam, String username) {
        this.steam = steam;
        this.username = username;

        setTitle("Descargar Juego");
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(30, 30, 30));

        panel.add(label("Código del juego:"));
        txtCodigo = new JTextField();
        panel.add(txtCodigo);

        btnDescargar = new JButton("⬇ Descargar");
        btnDescargar.setBackground(new Color(67, 181, 129));
        btnDescargar.setForeground(Color.WHITE);
        btnDescargar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(btnDescargar);

        btnDescargar.addActionListener(e -> {
            try {
                int gameCode = Integer.parseInt(txtCodigo.getText());
                boolean ok = steam.downloadGame(gameCode, buscarPlayerCode(steam, username), 'W');
                if (ok) {
                    JOptionPane.showMessageDialog(this, "✅ Descarga completada");
                } else {
                    JOptionPane.showMessageDialog(this, "❌ No se pudo descargar");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        add(panel);
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return l;
    }

    private int buscarPlayerCode(Steam steam, String username) throws Exception {
        steam.playersFile.seek(0);
        while (steam.playersFile.getFilePointer() < steam.playersFile.length()) {
            int code = steam.playersFile.readInt();
            String u = steam.playersFile.readUTF();
            steam.playersFile.readUTF(); // password
            steam.playersFile.readUTF(); // nombre
            steam.playersFile.readLong(); // nac
            steam.playersFile.readInt(); // downloads
            int imgSize = steam.playersFile.readInt();
            steam.playersFile.skipBytes(imgSize);
            steam.playersFile.readUTF(); // tipo
            steam.playersFile.readBoolean(); // estado

            if (u.equals(username)) {
                return code;
            }
        }
        throw new Exception("Usuario no encontrado");
    }
}

