/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DownloadFrame extends JFrame {
    private JTextField txtGameCode;
    private JComboBox<String> cmbSO;
    private JButton btnDescargar;
    private Steam steam;
    private String username;

    public DownloadFrame(Steam steam, String username) {
        this.steam = steam;
        this.username = username;

        setTitle("Descargar Juego");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(40, 40, 40));

        JLabel lblCode = new JLabel("Código de Juego:");
        lblCode.setForeground(Color.WHITE);
        txtGameCode = new JTextField();

        JLabel lblSO = new JLabel("Sistema Operativo:");
        lblSO.setForeground(Color.WHITE);
        cmbSO = new JComboBox<>(new String[]{"Windows", "Mac", "Linux"});

        btnDescargar = new JButton("Descargar");
        btnDescargar.setBackground(new Color(34, 139, 34));
        btnDescargar.setForeground(Color.WHITE);

        panel.add(lblCode);
        panel.add(txtGameCode);
        panel.add(lblSO);
        panel.add(cmbSO);
        panel.add(new JLabel());
        panel.add(btnDescargar);

        add(panel);

        btnDescargar.addActionListener(e -> descargarJuego());
    }

    private void descargarJuego() {
        try {
            int gameCode = Integer.parseInt(txtGameCode.getText());
            int playerCode = steam.findPlayerByCode(username);

            if (playerCode == -1) {
                JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
                return;
            }

            char os;
            switch (cmbSO.getSelectedItem().toString()) {
                case "Windows": os = 'W'; break;
                case "Mac": os = 'M'; break;
                case "Linux": os = 'L'; break;
                default: os = 'W'; break;
            }

            boolean ok = steam.downloadGame(gameCode, playerCode, os);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Descarga completada con éxito ✅");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo descargar el juego. Verifique edad, SO o código.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Código inválido.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en descarga: " + ex.getMessage());
        }
    }
}


