package Controller;

import java.io.Serializable;

/**
 * Representa una orden en el sistema.
 * Esta versión ha sido modificada para incluir los métodos que utiliza GestorDePedidos:
 *  - esValida()
 *  - getNombreCliente()
 *  - getProductos()
 *  - getClienteId()
 *  - getId()
 */
public class Orden implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Atributos existentes
    private String estado;
    private String metodoPago;
    private String nombreUsuario;
    private String direccionEntrega;
    private String producto;
    private double precio;
    
    // Constructor
    public Orden(String estado, String metodoPago, String nombreUsuario,
                 String direccionEntrega, String producto, double precio) {
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.nombreUsuario = nombreUsuario;
        this.direccionEntrega = direccionEntrega;
        this.producto = producto;
        this.precio = precio;
        System.out.println("[Orden] Creada: " + this);
    }
    
    @Override
    public String toString() {
        return "Orden {\n"
                + "  Estado: " + estado + ",\n"
                + "  Método de Pago: " + metodoPago + ",\n"
                + "  Nombre de Usuario: " + nombreUsuario + ",\n"
                + "  Dirección de Entrega: " + direccionEntrega + ",\n"
                + "  Producto: " + producto + ",\n"
                + "  Precio: " + precio + "\n"
                + '}';
    }
    
    // Getters y setters existentes

    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        System.out.println("[Orden] Set estado: " + estado);
        this.estado = estado;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        System.out.println("[Orden] Set método de pago: " + metodoPago);
        this.metodoPago = metodoPago;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        System.out.println("[Orden] Set nombre de usuario: " + nombreUsuario);
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    
    public void setDireccionEntrega(String direccionEntrega) {
        System.out.println("[Orden] Set dirección de entrega: " + direccionEntrega);
        this.direccionEntrega = direccionEntrega;
    }
    
    public String getProducto() {
        return producto;
    }
    
    public void setProducto(String producto) {
        System.out.println("[Orden] Set producto: " + producto);
        this.producto = producto;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public void setPrecio(double precio) {
        System.out.println("[Orden] Set precio: " + precio);
        this.precio = precio;
    }
    
    // Métodos implementados para satisfacer el contrato de GestorDePedidos
    
    /**
     * Determina si la orden es válida comprobando que los atributos mínimos no sean nulos o vacíos.
     * En este ejemplo, se considera válida si el nombre de usuario y el producto no son vacíos.
     */
    public boolean esValida() {
        return (nombreUsuario != null && !nombreUsuario.trim().isEmpty())
            && (producto != null && !producto.trim().isEmpty());
    }
    
    /**
     * Retorna el nombre del cliente. Se usa el atributo nombreUsuario.
     */
    public String getNombreCliente() {
        return nombreUsuario;
    }
    
    /**
     * Devuelve la información del producto. En esta versión se usa el atributo producto.
     */
    public String getProductos() {
        return producto;
    }
    
    /**
     * Devuelve un identificador ficticio para el cliente.
     * Ajusta este valor según la lógica de tu aplicación.
     */
    public String getClienteId() {
        return "0";  // Valor dummy o por defecto
    }
    
    /**
     * Devuelve un identificador ficticio para la orden.
     * Ajusta este valor según tu diseño de modelado.
     */
    public String getId() {
        return "1";  // Valor dummy o por defecto
    }
    
    // Opcional: Un método para obtener una descripción resumida de la orden.
    public String getDescripcion() {
        return "Producto: " + producto + ", Usuario: " + nombreUsuario
                + ", Dirección: " + direccionEntrega;
    }
}
