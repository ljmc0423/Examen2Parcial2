/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.Gui;


import javax.swing.*;
import java.awt.*;

public class CatalogFrame extends JFrame {
    public JComboBox<String> cmbGenero;
    public JPanel panelCatalogo;

    public CatalogFrame() {
        setTitle("Catálogo de Juegos");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cmbGenero = new JComboBox<>(new String[]{"Todos","Acción","Aventura","Deportes","Estrategia"});
        JPanel top = new JPanel(); top.add(new JLabel("Filtrar por género:")); top.add(cmbGenero);

        panelCatalogo = new JPanel(new GridLayout(0,3,15,15));
        panelCatalogo.setBackground(new Color(50,50,50));

        JScrollPane scroll = new JScrollPane(panelCatalogo);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}
