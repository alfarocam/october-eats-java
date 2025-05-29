package Model;

import Model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/prueba_bd2";
    private static final String USER = "root";
    private static final String PASSWORD = "prueba1";

    public void crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, direccion, correo, clave) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getDireccion());
            statement.setString(3, usuario.getCorreo());
            statement.setString(4, usuario.getClave());

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " Usuario Registrado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario obtenerUsuario(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setDireccion(resultSet.getString("direccion"));
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setClave(resultSet.getString("clave"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    // Nuevo método para obtener el usuario por correo.
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        String sql = "SELECT * FROM usuario WHERE correo = ?";
        Usuario usuario = null;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, correo);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setDireccion(resultSet.getString("direccion"));
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setClave(resultSet.getString("clave"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre = ?, direccion = ?, correo = ?, clave = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getDireccion());
            statement.setString(3, usuario.getCorreo());
            statement.setString(4, usuario.getClave());
            statement.setInt(5, usuario.getId());

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " Información Actualizada");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuario con ID " + id + " ha sido eliminado.");
            } else {
                System.out.println("No se encontró el usuario con ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void obtenerTodosUsuarios() {
        String sql = "SELECT * FROM usuario";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Nombre: " + resultSet.getString("nombre"));
                System.out.println("Dirección: " + resultSet.getString("direccion"));
                System.out.println("Correo: " + resultSet.getString("correo"));
                System.out.println("Clave: " + resultSet.getString("clave"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
