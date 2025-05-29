/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author CamilaAlfaro
 */
/**
 * este es el cliente que ven los restaurantes que se conecta al servidor para
 * recibir la orden esta clase es la que utiliza los restaurantes para ver y
 * procesar los pedidos
 */
public class ClienteRestaurante {

    private Set<String> ordenesProcessadas = new HashSet<>();

    private static final String HOST_SERVIDOR = "localhost";
    // Puerto del server de restaurante
    private static final int PUERTO_SERVIDOR = 5003;
    private int restauranteId;
    //socket para conexion con el servidor
    private Socket socket;
    //entrada para recibir a orden
    private ObjectInputStream entrada;
    //salida para enviar respuestas
    private ObjectOutputStream salida;
    private boolean conectado = false;

    public ClienteRestaurante(int restauranteId) {
        this.restauranteId = restauranteId;
    }

    /**
     * Conecta al cliente con el servidor
     */
    public void conectar() {
        if (conectado) {
            return;
        }

        try {
            socket = new Socket(HOST_SERVIDOR, PUERTO_SERVIDOR);
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada = new ObjectInputStream(socket.getInputStream());

            salida.writeObject(restauranteId);
            new Thread(this::escucharPedidos).start();

            conectado = true;
            System.out.println("Conectado al servidor como Restaurante #" + restauranteId);

        } catch (IOException e) {
            System.err.println("Error al conectar con el servidor: " + e.getMessage());

        }
    }

    /**
     * Escucha mensajes del servidor
     */
    private void escucharPedidos() {
        try {
            while (conectado) {
                // se lee  el mensaje del servidor
                Object mensaje = entrada.readObject();

                //si el mensaje es una orden se procesa
                if (mensaje instanceof Orden) {
                    Orden orden = (Orden) mensaje;
                    System.out.println("\nNueva orden recibida!");
                    System.out.println(orden);

                    //aquí se mueestra la orden en una interfaz 
                    // para que el restaurante pueda decidir si la acepta o rechaza
                    procesarOrden(orden);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en la conexión con el servidor: " + e.getMessage());
            conectado = false;
        }
    }

    /**
     * Procesa la orden recibida
     */
    private void procesarOrden(Orden orden) {
        // aqui se crea un identificador único para la orden
        String ordenId = orden.getNombreUsuario() + "_" + orden.getProducto() + "_"
                + orden.getDireccionEntrega() + "_" + orden.getPrecio();

        // se verifica si ya procesamos esta orden
        if (ordenesProcessadas.contains(ordenId)) {
            System.out.println("Esta orden ya fue procesada previamente. Ignorando duplicado.");
            return;
        }

        // se registra que estamos procesando esta orden
        ordenesProcessadas.add(ordenId);

        System.out.println("Nueva orden recibida:");
        System.out.println(orden);

        System.out.println("Desea aceptar esta orden?");
        System.out.println("Escriba si para aceptar o cualquier otra cosa para rechazar:");

        Scanner scanner = new Scanner(System.in);
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("si")) {
            orden.setEstado("confirmado");
        } else {
            orden.setEstado("rechazado");
        }

        try {
            // nos aseguramos que solo se envía una vez:
            synchronized (salida) {
                salida.reset();
                salida.writeObject(orden);
                salida.flush();
                System.out.println("Estado de orden enviado al servidor: " + orden.getEstado());
            }
        } catch (IOException e) {
            System.err.println("Error al enviar el estado de la orden: " + e.getMessage());
        }
    }

    /**
     * Cierra la conexion con el servidor
     */
    public void desconectar() {
        try {
            conectado = false;
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    /**
     * metodo main para ejecutar el cliente de restaurante
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID del restaurante: ");
        int id = scanner.nextInt();

        ClienteRestaurante cliente = new ClienteRestaurante(id);

        //conectamos con el server
        cliente.conectar();
        System.out.println("Esperando ordenes de clientes...");

    }
}
