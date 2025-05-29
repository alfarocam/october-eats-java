package Conexion;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class AnuncianteServidor implements Runnable {

    private static final int BROADCAST_PORT = 5003; // Puerto UDP para difusión (distinto al TCP)
    private static final int INTERVALO_MS = 3000;   // Intervalo de 3 segundos

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setBroadcast(true);
            while (true) {
                // Obtener la IP local del servidor
                String ipServidor = InetAddress.getLocalHost().getHostAddress();
                // Suponemos que el puerto TCP de conexión es 5002
                String mensaje = "SERVIDOR:IP=" + ipServidor + ";PUERTO=5002";
                byte[] datos = mensaje.getBytes();

                // Enviar el broadcast a la dirección de difusión general
                DatagramPacket packet = new DatagramPacket(
                        datos, 
                        datos.length, 
                        InetAddress.getByName("255.255.255.255"), 
                        BROADCAST_PORT);
                socket.send(packet);
                System.out.println("Anuncio enviado: " + mensaje);

                Thread.sleep(INTERVALO_MS);
            }
        } catch (Exception e) {
            System.err.println("Error en la emisión de broadcast:");
            e.printStackTrace();
        }
    }
    
    // Método main para pruebas independientes
    public static void main(String[] args) {
        Thread thread = new Thread(new AnuncianteServidor());
        thread.start();
    }
}
