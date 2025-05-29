package Controller;

public class Usuario {
    private String nombre;
    private String correo;
    private String contrasena;
    private String direccion;

    public Usuario(String nombre, String correo, String contrasena, String direccion) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "Usuario [Nombre=" + nombre + ", Correo=" + correo + ", Contraseña=" + contrasena + ", Dirección=" + direccion + "]";
    }
}
