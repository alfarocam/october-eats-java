package Conexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static final int PUERTO = 5002;
    
    public static void main(String[] args) {
        System.out.println("[SERVIDOR] Iniciando servidor en el puerto " + PUERTO + "...");
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("[SERVIDOR] Servidor iniciado, esperando conexiones...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("[SERVIDOR] Conexión aceptada desde: " + socket.getInetAddress());
                // Se crea un hilo para gestionar esta conexión mediante el dispatcher.
                new Thread(new ConexionDispatcher(socket)).start();
            }
        } catch (IOException e) {
            System.err.println("[SERVIDOR] Error en el servidor:");
            e.printStackTrace();
        }
    }
}
