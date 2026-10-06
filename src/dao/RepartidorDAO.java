package dao;

import main.ConexionBD;
import modelo.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    // CREATE
    public boolean crear(Repartidor repartidor) {

        String sql = "INSERT INTO repartidor (id, nombre) VALUES (?, ?)";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, repartidor.getId());
            ps.setString(2, repartidor.getNombre());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al crear repartidor: " + e.getMessage());
            return false;
        }
    }

    // READ
    public List<Repartidor> listarTodos() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql = "SELECT id, nombre FROM repartidor ORDER BY id";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Repartidor repartidor = new Repartidor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );

                repartidores.add(repartidor);
            }

        } catch (Exception e) {

            System.out.println("Error al listar repartidores: "
                    + e.getMessage());
        }

        return repartidores;
    }

    // UPDATE
    public boolean actualizar(Repartidor repartidor) {

        String sql = "UPDATE repartidor SET nombre = ? WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setString(1, repartidor.getNombre());
            ps.setInt(2, repartidor.getId());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al actualizar repartidor: "
                    + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean eliminar(int id) {

        String sql = "DELETE FROM repartidor WHERE id = ?";

        try (
                Connection conexion = ConexionBD.conectar();
                PreparedStatement ps = conexion.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println("Error al eliminar repartidor: "
                    + e.getMessage());
            return false;
        }
    }
}