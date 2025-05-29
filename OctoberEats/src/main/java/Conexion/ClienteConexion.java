package Conexion;

import Controller.Orden; // Asegúrate de que la clase Orden esté correctamente definida
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteConexion implements Runnable {
    private Socket socket;
    private ObjectInputStream in;

    public ClienteConexion(Socket socket, ObjectInputStream in) {
        this.socket = socket;
        this.in = in;
    }

    @Override
    public void run() {
        try {
            // Se espera que el siguiente objeto enviado sea la Orden
            Object obj = in.readObject();
            if (!(obj instanceof Orden)) {
                System.err.println("Se esperaba un objeto de tipo Orden, pero se recibió: " + obj);
                socket.close();
                return;
            }
            Orden orden = (Orden) obj;
            System.out.println("Orden recibida del cliente: " + orden);

            // Preparar el envío al restaurante mediante ServidorRestaurante
            ServidorRestaurante sr = new ServidorRestaurante();
            // Por ejemplo, enviamos al restaurante con ID 1 (puedes ajustar según necesites)
            sr.enviarOrdenARestaurante(orden, 1);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error en ClienteConexion:");
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
