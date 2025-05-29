package Controller;

import Model.Usuario;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5002); ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream()); Scanner scanner = new Scanner(System.in)) {

            //random para que genere un ID aleatorio por el momento esta de 5 digitos
            Random random = new Random();
            int id = 10000 + random.nextInt(90000);

            //datos del usuario
            System.out.print("Ingrese su nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese su dirección: ");
            String direccion = scanner.nextLine();

            //crea objeto usuario
            /*
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setDireccion(direccion);
*/
            
            System.out.print("Ingrese el producto: ");
            String producto = scanner.nextLine();

            //datos que se le piden al usuario
            //por el momento lo dejo así ya que aún no está conectado con restaurante
            System.out.print("Ingrese el estado (pendiente, confirmado, cancelado): ");
            String estado = scanner.nextLine();
            System.out.print("Ingrese el metodo de pago (efectivo, tarjeta): ");
            String metodoPago = scanner.nextLine();

            
            /*
                        //Lista<String> productos (Lista)
            List<String> productos = new ArrayList<>();
            System.out.println("Ingrese los productos (escriba 'S' para terminar):");
            
            String producto;
            while (true) {
                System.out.print("Producto: ");
                producto = scanner.nextLine();
                if (producto.equalsIgnoreCase("S")) {
                    break;
                }
                productos.add(producto);
            }


*/
            
            
            
            //esto de igual forma se encuentra así temporalmente
            System.out.print("El precio total es: ");
            int precio = scanner.nextInt();

            //crea objeto orden con los datos ingresados
            Orden orden = new Orden(estado, metodoPago, nombre, direccion, producto, precio);
            System.out.println("ID de orden: " + id);
            
            


            //se envia la orden al server
            salida.writeObject(orden);
            System.out.println("Orden enviada al servidor!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


