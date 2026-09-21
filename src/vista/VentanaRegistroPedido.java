package vista;

import modelo.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaRegistroPedido extends JFrame {

    private JTextField txtId;
    private JTextField txtDireccion;
    private JTextField txtDistancia;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar;

    // Lista temporal de pedidos en memoria
    public static List<Pedido> listaPedidos = new ArrayList<>();

    public VentanaRegistroPedido() {

        setTitle("Registrar Pedido");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblId = new JLabel("ID del pedido:");
        JLabel lblDireccion = new JLabel("Dirección:");
        JLabel lblTipo = new JLabel("Tipo de pedido:");
        JLabel lblDistancia = new JLabel("Distancia (km):");

        txtId = new JTextField();
        txtDireccion = new JTextField();
        txtDistancia = new JTextField();

        cmbTipo = new JComboBox<>();
        cmbTipo.addItem("Comida");
        cmbTipo.addItem("Encomienda");
        cmbTipo.addItem("Express");

        btnGuardar = new JButton("Guardar");

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        panel.add(lblId);
        panel.add(txtId);

        panel.add(lblDireccion);
        panel.add(txtDireccion);

        panel.add(lblTipo);
        panel.add(cmbTipo);

        panel.add(lblDistancia);
        panel.add(txtDistancia);

        panel.add(new JLabel());
        panel.add(btnGuardar);

        add(panel, BorderLayout.CENTER);

        // Evento del botón Guardar
        btnGuardar.addActionListener(e -> guardarPedido());
    }

    private void guardarPedido() {

        // Validar ID
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el ID del pedido."
            );
            return;
        }

        // Validar dirección
        if (txtDireccion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la dirección."
            );
            return;
        }

        // Validar distancia
        if (txtDistancia.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la distancia."
            );
            return;
        }

        try {

            int id = Integer.parseInt(txtId.getText().trim());

            double distancia = Double.parseDouble(
                    txtDistancia.getText().trim()
            );

            String direccion = txtDireccion.getText().trim();

            String tipo = cmbTipo.getSelectedItem().toString();

            // Crear el pedido usando tu clase Pedido
            Pedido pedido = new Pedido(
                    id,
                    direccion,
                    tipo,
                    distancia
            );

            // Guardar el pedido en memoria
            listaPedidos.add(pedido);

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente."
            );

            // Limpiar los campos
            txtId.setText("");
            txtDireccion.setText("");
            txtDistancia.setText("");
            cmbTipo.setSelectedIndex(0);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un número entero y la distancia debe ser un número."
            );
        }
    }
}