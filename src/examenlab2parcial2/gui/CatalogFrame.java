/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;

import examenlab2parcial2.Steam;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class CatalogFrame extends JFrame {
    private JTable table;

    public CatalogFrame(Steam steam) {
        setTitle("Catálogo de Juegos");
        setSize(700, 450);
        setLocationRelativeTo(null);

        String[] columnas = {"Código", "Título", "Género", "SO", "Edad", "Precio", "Descargas"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        table = new JTable(model);

        // estilo tabla
        table.setBackground(new Color(45, 58, 78));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setBackground(new Color(30, 30, 30));
        table.getTableHeader().setForeground(new Color(102, 192, 244));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        try {
            steam.gamesFile.seek(0);
            while (steam.gamesFile.getFilePointer() < steam.gamesFile.length()) {
                int code = steam.gamesFile.readInt();
                String title = steam.gamesFile.readUTF();
                String genre = steam.gamesFile.readUTF();
                char os = steam.gamesFile.readChar();
                int edad = steam.gamesFile.readInt();
                double price = steam.gamesFile.readDouble();
                int downloads = steam.gamesFile.readInt();
                int imgSize = steam.gamesFile.readInt();
                steam.gamesFile.skipBytes(imgSize);

                model.addRow(new Object[]{code, title, genre, os, edad, price, downloads});
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error cargando catálogo: " + ex.getMessage());
        }

        add(new JScrollPane(table));
    }
}
