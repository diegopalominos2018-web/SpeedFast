package dao;

import main.ConexionBD;
import modelo.EstadoPedido;
import modelo.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // CREATE
    public boolean guardar(Pedido pedido) {

        String sql = "INSERT INTO pedido " +
                "(id, direccion, tipo, distancia, estado) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, pedido.getIdPedido());
            ps.setString(2, pedido.getDireccionEntrega());
            ps.setString(3, pedido.getTipoPedido());
            ps.setDouble(4, pedido.getDistanciaKm());
            ps.setString(5, pedido.getEstado().toString());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al guardar pedido: "
                    + e.getMessage());

            return false;
        }
    }

    // READ
    public List<Pedido> listarTodos() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql = "SELECT id, direccion, tipo, distancia, estado " +
                "FROM pedido ORDER BY id";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getDouble("distancia")
                );

                pedido.setEstado(
                        EstadoPedido.valueOf(
                                rs.getString("estado")
                        )
                );

                pedidos.add(pedido);
            }

        } catch (Exception e) {

            System.out.println("Error al listar pedidos: "
                    + e.getMessage());
        }

        return pedidos;
    }

    // UPDATE
    public boolean actualizar(Pedido pedido) {

        String sql = "UPDATE pedido SET " +
                "direccion = ?, " +
                "tipo = ?, " +
                "distancia = ?, " +
                "estado = ? " +
                "WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, pedido.getDireccionEntrega());
            ps.setString(2, pedido.getTipoPedido());
            ps.setDouble(3, pedido.getDistanciaKm());
            ps.setString(4, pedido.getEstado().toString());
            ps.setInt(5, pedido.getIdPedido());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al actualizar pedido: "
                    + e.getMessage());

            return false;
        }
    }

    // DELETE
    public boolean eliminar(int id) {

        String sql = "DELETE FROM pedido WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al eliminar pedido: "
                    + e.getMessage());

            return false;
        }
    }
}