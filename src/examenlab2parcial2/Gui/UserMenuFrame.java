/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.Gui;


import javax.swing.*;
import java.awt.*;

public class UserMenuFrame extends JFrame {
    public JButton btnCatalog, btnDownload, btnProfile;

    public UserMenuFrame() {
        setTitle("Steam - Usuario");
        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(1,3,15,15));
        panel.setBackground(new Color(40,40,40));
        btnCatalog = button("Ver Catálogo");
        btnDownload = button("Descargar Juegos");
        btnProfile = button("Mi Perfil");
        panel.add(btnCatalog); panel.add(btnDownload); panel.add(btnProfile);

        add(panel, BorderLayout.CENTER);
    }

    private JButton button(String text){ JButton b=new JButton(text); b.setBackground(new Color(34,139,34)); b.setForeground(Color.WHITE); return b; }
}
