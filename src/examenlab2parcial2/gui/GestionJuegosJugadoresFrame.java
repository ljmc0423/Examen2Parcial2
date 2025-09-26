/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examenlab2parcial2.gui;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;


public class GestionJuegosJugadoresFrame extends JFrame {

    private final DefaultTableModel juegosModel = new DefaultTableModel(
            new String[]{"ID", "Título", "Género", "Precio"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    private JTable tblJuegos;
    private JTextField txtId, txtTitulo;
    private JComboBox<String> cmbGenero;
    private JSpinner spPrecio;

    public GestionJuegosJugadoresFrame() {
        setTitle("Gestión de Juegos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel formulario
        JPanel form = new JPanel();
        form.setBorder(new TitledBorder("Datos del Juego"));
        GroupLayout gl = new GroupLayout(form);
        form.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);

        txtId = new JTextField();
        txtTitulo = new JTextField();
        cmbGenero = new JComboBox<>(new String[]{"Acción", "Aventura", "Estrategia", "RPG", "Deportes", "Otro"});
        spPrecio = new JSpinner(new SpinnerNumberModel(9.99, 0.0, 9999.99, 0.5));

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnRegistrar.addActionListener(e -> registrarJuego());
        btnModificar.addActionListener(e -> modificarJuego());
        btnEliminar.addActionListener(e -> eliminarJuego());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JLabel l1 = new JLabel("ID:"), l2 = new JLabel("Título:"), l3 = new JLabel("Género:"), l4 = new JLabel("Precio:");

        gl.setHorizontalGroup(gl.createParallelGroup()
                .addGroup(gl.createSequentialGroup().addComponent(l1, 70, 70, 70).addComponent(txtId))
                .addGroup(gl.createSequentialGroup().addComponent(l2, 70, 70, 70).addComponent(txtTitulo))
                .addGroup(gl.createSequentialGroup().addComponent(l3, 70, 70, 70).addComponent(cmbGenero))
                .addGroup(gl.createSequentialGroup().addComponent(l4, 70, 70, 70).addComponent(spPrecio))
                .addGroup(gl.createSequentialGroup().addComponent(btnRegistrar).addComponent(btnModificar).addComponent(btnEliminar).addComponent(btnLimpiar))
        );
        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l1).addComponent(txtId))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l2).addComponent(txtTitulo))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l3).addComponent(cmbGenero))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(l4).addComponent(spPrecio))
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnRegistrar).addComponent(btnModificar).addComponent(btnEliminar).addComponent(btnLimpiar))
        );

        // Tabla
        tblJuegos = new JTable(juegosModel);
        tblJuegos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblJuegos.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { if (e.getClickCount()==1) cargarSeleccion(); }
        });

        add(form, BorderLayout.WEST);
        add(new JScrollPane(tblJuegos), BorderLayout.CENTER);
    }

    private void registrarJuego() {
        String id = txtId.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String genero = (String) cmbGenero.getSelectedItem();
        double precio = (double) spPrecio.getValue();
        if (id.isEmpty() || titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa ID y Título");
            return;
        }
        juegosModel.addRow(new Object[]{id, titulo, genero, precio});
        limpiarFormulario();
    }

    private void modificarJuego() {
        int row = tblJuegos.getSelectedRow();
        if (row == -1) return;
        juegosModel.setValueAt(txtId.getText().trim(), row, 0);
        juegosModel.setValueAt(txtTitulo.getText().trim(), row, 1);
        juegosModel.setValueAt(cmbGenero.getSelectedItem(), row, 2);
        juegosModel.setValueAt(spPrecio.getValue(), row, 3);
    }

    private void eliminarJuego() {
        int row = tblJuegos.getSelectedRow();
        if (row == -1) return;
        juegosModel.removeRow(row);
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtTitulo.setText("");
        cmbGenero.setSelectedIndex(0);
        spPrecio.setValue(9.99);
    }

    private void cargarSeleccion() {
        int row = tblJuegos.getSelectedRow();
        if (row == -1) return;
        txtId.setText(juegosModel.getValueAt(row, 0).toString());
        txtTitulo.setText(juegosModel.getValueAt(row, 1).toString());
        cmbGenero.setSelectedItem(juegosModel.getValueAt(row, 2));
        spPrecio.setValue(Double.parseDouble(juegosModel.getValueAt(row, 3).toString()));
    }

    
}
