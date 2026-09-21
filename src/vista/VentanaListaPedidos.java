package vista;

import modelo.EstadoPedido;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;
    private JButton btnAsignar;

    public VentanaListaPedidos() {

        setTitle("Lista de Pedidos");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Distancia (km)");
        modeloTabla.addColumn("Estado");

        tablaPedidos = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaPedidos);

        btnActualizar = new JButton("Actualizar");
        btnAsignar = new JButton("Asignar repartidor / Iniciar entrega");

        btnActualizar.addActionListener(e -> cargarPedidos());

        btnAsignar.addActionListener(e -> asignarRepartidor());

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnActualizar);
        panelInferior.add(btnAsignar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        cargarPedidos();
    }

    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        for (Pedido pedido : VentanaRegistroPedido.listaPedidos) {

            modeloTabla.addRow(new Object[]{
                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipoPedido(),
                    pedido.getDistanciaKm(),
                    pedido.getEstado()
            });
        }
    }

    private void asignarRepartidor() {

        int filaSeleccionada = tablaPedidos.getSelectedRow();

        if (filaSeleccionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido de la tabla."
            );

            return;
        }

        int idPedido = (int) tablaPedidos.getValueAt(
                filaSeleccionada,
                0
        );

        String nombreRepartidor = JOptionPane.showInputDialog(
                this,
                "Ingrese el nombre del repartidor:"
        );

        if (nombreRepartidor == null ||
                nombreRepartidor.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un repartidor."
            );

            return;
        }

        for (Pedido pedido : VentanaRegistroPedido.listaPedidos) {

            if (pedido.getIdPedido() == idPedido) {

                pedido.asignarRepartidor(
                        nombreRepartidor.trim()
                );

                pedido.setEstado(
                        EstadoPedido.EN_REPARTO
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Repartidor asignado correctamente.\n"
                                + "Pedido en reparto."
                );

                cargarPedidos();

                return;
            }
        }
    }
}