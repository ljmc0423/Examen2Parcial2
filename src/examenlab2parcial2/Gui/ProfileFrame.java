/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.Gui;

import javax.swing.*;
import java.awt.*;

public class ProfileFrame extends JFrame {
    public JLabel lblFoto, lblNombre, lblUser, lblEdad, lblDescargas;
    public JTable historial;

    public ProfileFrame() {
        setTitle("Mi Perfil");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel info = new JPanel(new GridLayout(5,1));
        info.setBackground(new Color(30,30,30));
        lblFoto = new JLabel(new ImageIcon("default.png"));
        lblNombre = label("Nombre: ---");
        lblUser = label("Usuario: ---");
        lblEdad = label("Edad: ---");
        lblDescargas = label("Descargas: 0");
        info.add(lblNombre); info.add(lblUser); info.add(lblEdad); info.add(lblDescargas);

        historial = new JTable(new Object[][]{}, new String[]{"Fecha","Download ID","Juego","Precio"});
        JScrollPane scroll = new JScrollPane(historial);

        add(lblFoto, BorderLayout.WEST);
        add(info, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    private JLabel label(String text){ JLabel l=new JLabel(text); l.setForeground(Color.WHITE); return l; }
}

