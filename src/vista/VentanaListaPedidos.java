package vista;

import dao.PedidoDAO;
import modelo.EstadoPedido;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import main.ConexionBD;

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

        PedidoDAO pedidoDAO = new PedidoDAO();

        for (Pedido pedido : pedidoDAO.listarTodos()) {

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

        nombreRepartidor = nombreRepartidor.trim();

        // Actualizamos el estado del pedido en la base de datos
        actualizarEstadoEnBD(
                idPedido,
                EstadoPedido.EN_REPARTO
        );

        // Actualizamos visualmente la tabla
        tablaPedidos.setValueAt(
                EstadoPedido.EN_REPARTO,
                filaSeleccionada,
                4
        );

        JOptionPane.showMessageDialog(
                this,
                "Repartidor asignado correctamente.\n"
                        + "Repartidor: " + nombreRepartidor
                        + "\nPedido en reparto."
        );
    }

    private void actualizarEstadoEnBD(
            int idPedido,
            EstadoPedido nuevoEstado) {

        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps =
                        conexion.prepareStatement(sql)
        ) {

            ps.setString(1, nuevoEstado.toString());
            ps.setInt(2, idPedido);

            ps.executeUpdate();

            System.out.println(
                    "Estado actualizado correctamente en la BD."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al actualizar el estado:\n"
                            + e.getMessage()
            );
        }
    }
}