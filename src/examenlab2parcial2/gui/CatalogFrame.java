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
    private JTable tabla;
    private DefaultTableModel modelo;
    private Steam steam;

    public CatalogFrame(Steam steam) {
        this.steam = steam;

        setTitle("Catálogo de Juegos");
        setSize(700, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        modelo = new DefaultTableModel(new Object[]{"Código", "Título", "Género", "SO", "Edad mínima", "Precio", "Descargas"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        cargarCatalogo();
    }

    private void cargarCatalogo() {
        try {
            steam.printGames(); // imprime en consola
            modelo.setRowCount(0); // limpiar tabla

            // Leer juegos directamente del archivo
            var raf = new java.io.RandomAccessFile("steam/games.stm", "r");
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                int code = raf.readInt();
                String title = raf.readUTF();
                String genre = raf.readUTF();
                char os = raf.readChar();
                int edad = raf.readInt();
                double price = raf.readDouble();
                int downloads = raf.readInt();
                int imgSize = raf.readInt();
                raf.skipBytes(imgSize);

                modelo.addRow(new Object[]{code, title, genre, os, edad, price, downloads});
            }
            raf.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error cargando catálogo: " + e.getMessage());
        }
    }
}
