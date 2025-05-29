package Conexion;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RestauranteDispatcher implements Runnable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    // Constructor modificado para aceptar tanto Socket como ObjectInputStream.
    public RestauranteDispatcher(Socket socket, ObjectInputStream in) {
        this.socket = socket;
        this.in = in;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error inicializando ObjectOutputStream en RestauranteDispatcher:");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Conexión de RESTAURANTE establecida desde: " + socket.getInetAddress());
        try {
            Object mensaje;
            while ((mensaje = in.readObject()) != null) {
                System.out.println("Mensaje recibido del restaurante: " + mensaje);
                // Procesa el mensaje según el protocolo
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en RestauranteDispatcher:");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
