package Model;

import java.io.Serializable;

/**
 * Representa un producto en el sistema.
 * Contiene información sobre el nombre, el precio unitario,
 * la cantidad, y permite calcular el precio total.
 */
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        System.out.println("[Producto] Creado: " + this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        System.out.println("[Producto] Set nombre: " + nombre);
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        System.out.println("[Producto] Set precio: " + precio);
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        System.out.println("[Producto] Set cantidad: " + cantidad);
        this.cantidad = cantidad;
    }

    // Calcula el precio total del producto (precio unitario * cantidad)
    public double getPrecioTotal() {
        return precio * cantidad;
    }

    @Override
    public String toString() {
        return nombre + " - $" + String.format("%.2f", precio) 
                + " x " + cantidad
                + " (Total: $" + String.format("%.2f", getPrecioTotal()) + ")";
    }
}
