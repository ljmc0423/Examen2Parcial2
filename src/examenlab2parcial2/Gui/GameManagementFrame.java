/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.Gui;

import javax.swing.*;
import java.awt.*;

public class GameManagementFrame extends JFrame {
    public JTable table;
    public JButton btnAdd, btnEdit, btnDelete;

    public GameManagementFrame() {
        setTitle("Gestión de Juegos");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        table = new JTable(new Object[][]{}, new String[]{"Código","Título","Género","SO","Edad","Precio","Descargas"});
        JScrollPane scroll = new JScrollPane(table);

        JPanel botones = new JPanel();
        btnAdd = button("Agregar"); btnEdit = button("Modificar"); btnDelete = button("Eliminar");
        botones.add(btnAdd); botones.add(btnEdit); botones.add(btnDelete);

        add(scroll, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private JButton button(String text){ JButton b=new JButton(text); b.setBackground(new Color(70,130,180)); b.setForeground(Color.WHITE); return b; }
}
