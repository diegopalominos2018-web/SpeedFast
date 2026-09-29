package dao;

import main.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public List<String> listarTodos() {

        List<String> repartidores = new ArrayList<>();

        String sql = "SELECT nombre FROM repartidor";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                repartidores.add(rs.getString("nombre"));
            }

        } catch (Exception e) {
            System.out.println("Error al listar repartidores: " + e.getMessage());
        }

        return repartidores;
    }
}