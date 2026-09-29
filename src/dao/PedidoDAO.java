package dao;

import main.ConexionBD;
import modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public boolean guardar(Pedido pedido) {

        String sql = "INSERT INTO pedido " +
                "(id, direccion, tipo, distancia, estado) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, pedido.getIdPedido());
            ps.setString(2, pedido.getDireccionEntrega());
            ps.setString(3, pedido.getTipoPedido());
            ps.setDouble(4, pedido.getDistanciaKm());
            ps.setString(5, pedido.getEstado().toString());

            ps.executeUpdate();

            System.out.println("Pedido guardado correctamente en la BD.");
            return true;

        } catch (Exception e) {
            System.out.println("Error al guardar pedido: " + e.getMessage());
            return false;
        }
    }

    public List<Pedido> listarTodos() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT id, direccion, tipo, distancia, estado " +
                "FROM pedido ORDER BY id";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("distancia")
                );

                pedido.setEstado(
                        modelo.EstadoPedido.valueOf(
                                rs.getString("estado")
                        )
                );

                pedidos.add(pedido);
            }

        } catch (Exception e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }

        return pedidos;
    }
}