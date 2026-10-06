package dao;

import main.ConexionBD;
import modelo.Entrega;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    // CREATE
    public boolean crear(Entrega entrega) {

        String sql = "INSERT INTO entrega " +
                "(id, id_pedido, id_repartidor, fecha, hora) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, entrega.getId());
            ps.setInt(2, entrega.getIdPedido());
            ps.setInt(3, entrega.getIdRepartidor());
            ps.setDate(4, Date.valueOf(entrega.getFecha()));
            ps.setTime(5, Time.valueOf(entrega.getHora()));

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al crear entrega: "
                    + e.getMessage());

            return false;
        }
    }

    // READ
    public List<Entrega> listarTodos() {

        List<Entrega> entregas = new ArrayList<>();

        String sql = "SELECT id, id_pedido, id_repartidor, fecha, hora " +
                "FROM entrega ORDER BY id";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Entrega entrega = new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getTime("hora").toLocalTime()
                );

                entregas.add(entrega);
            }

        } catch (Exception e) {

            System.out.println("Error al listar entregas: "
                    + e.getMessage());
        }

        return entregas;
    }

    // UPDATE
    public boolean actualizar(Entrega entrega) {

        String sql = "UPDATE entrega SET " +
                "id_pedido = ?, " +
                "id_repartidor = ?, " +
                "fecha = ?, " +
                "hora = ? " +
                "WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, Date.valueOf(entrega.getFecha()));
            ps.setTime(4, Time.valueOf(entrega.getHora()));
            ps.setInt(5, entrega.getId());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al actualizar entrega: "
                    + e.getMessage());

            return false;
        }
    }

    // DELETE
    public boolean eliminar(int id) {

        String sql = "DELETE FROM entrega WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al eliminar entrega: "
                    + e.getMessage());

            return false;
        }
    }
}