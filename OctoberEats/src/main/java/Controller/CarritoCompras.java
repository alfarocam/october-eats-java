package Controller;

import Model.Producto;
import java.util.ArrayList;
import java.util.List;

public class CarritoCompras {
    // Ahora el carrito almacena objetos Producto en lugar de Orden
    private List<Producto> productos;

    public CarritoCompras() {
        this.productos = new ArrayList<>();
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto p) {
        System.out.println("[CarritoCompras] Agregando producto: " + p);
        productos.add(p);
    }

    public void eliminarProducto(Producto p) {
        System.out.println("[CarritoCompras] Eliminando producto: " + p);
        productos.remove(p);
    }

    public List<Producto> verCarrito() {
        return productos;
    }

    public void vaciarCarrito() {
        System.out.println("[CarritoCompras] Vaciando carrito");
        productos.clear();
    }
}
