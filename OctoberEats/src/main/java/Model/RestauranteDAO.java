package Model;

import java.sql.*;

public class RestauranteDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/prueba_bd2";
    private static final String USER = "root";
    private static final String PASSWORD = "prueba1";

    public void crearRestaurante(Restaurante restaurante) {
        String sql = "INSERT INTO informacion_restaurante (nombre, direccion, opcion_uno, opcion_dos) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, restaurante.getNombre());
            statement.setString(2, restaurante.getDireccion());
            statement.setString(3, restaurante.getOpcionUno());
            statement.setString(4, restaurante.getOpcionDos());

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " Restaurante Registrado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Restaurante obtenerRestaurante(int id) {
        Restaurante restaurante = null;
        String sql = "SELECT * FROM informacion_restaurante WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String direccion = resultSet.getString("direccion");
                String opcionUno = resultSet.getString("opcion_uno");
                String opcionDos = resultSet.getString("opcion_dos");

                restaurante = new Restaurante(id, nombre, direccion, opcionUno, opcionDos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurante;
    }

    public void actualizarRestaurante(Restaurante restaurante) {
        String sql = "UPDATE informacion_restaurante SET nombre = ?, direccion = ?, opcion_uno = ?, opcion_dos = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, restaurante.getNombre());
            statement.setString(2, restaurante.getDireccion());
            statement.setString(3, restaurante.getOpcionUno());
            statement.setString(4, restaurante.getOpcionDos());
            statement.setInt(5, restaurante.getId());

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " Restaurante Actualizado");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminarRestaurante(int id) {
        String sql = "DELETE FROM informacion_restaurante WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " Restaurante Eliminado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
