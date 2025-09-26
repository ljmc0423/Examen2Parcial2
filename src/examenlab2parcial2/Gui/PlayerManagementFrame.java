/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.Gui;

import javax.swing.*;
import java.awt.*;

public class PlayerManagementFrame extends JFrame {
    public JTable table;
    public JButton btnEdit, btnDeactivate, btnActivate;

    public PlayerManagementFrame() {
        setTitle("Gestión de Jugadores");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        table = new JTable(new Object[][]{}, new String[]{"Código","Username","Nombre","Tipo","Estado","Descargas"});
        JScrollPane scroll = new JScrollPane(table);

        JPanel botones = new JPanel();
        btnEdit = button("Modificar");
        btnDeactivate = button("Desactivar");
        btnActivate = button("Activar");
        botones.add(btnEdit); botones.add(btnDeactivate); botones.add(btnActivate);

        add(scroll, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private JButton button(String text){ JButton b=new JButton(text); b.setBackground(new Color(70,130,180)); b.setForeground(Color.WHITE); return b; }
}
