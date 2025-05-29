package Model;

public class Usuario {
    private int id;
    private String nombre;
    private String direccion;
    private String correo;
    private String clave;

    public Usuario(int id, String nombre, String direccion, String correo, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.clave = clave;
    }

    public Usuario() {
    }

    public Usuario(String nombre, String direccion, String correo, String clave) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
