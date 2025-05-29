package Conexion;

import java.util.*;
import java.io.*;
import java.net.Socket;
import Controller.Orden;

public class ConexionDispatcher implements Runnable {

    private Set<String> ordenesEnviadas = new HashSet<>();

    private Socket socket;

    public ConexionDispatcher(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("[Servidor] Creando ObjectOutputStream...");
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            System.out.println("[Servidor] ObjectOutputStream creado y flusheado.");

            System.out.println("[Servidor] Creando ObjectInputStream...");
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("[Servidor] ObjectInputStream creado.");

            // Se lee el token enviado por el cliente/restaurante.
            Object tokenObj = in.readObject();
            if (tokenObj instanceof String) {
                String token = (String) tokenObj;
                System.out.println("[Servidor] Token recibido: " + token);
                if (token.equalsIgnoreCase("CLIENTE")) {
                    System.out.println("[Servidor] Conexión de cliente establecida.");
                } else if (token.equalsIgnoreCase("RESTAURANTE")) {
                    System.out.println("[Servidor] Conexión de restaurante establecida.");
                    // Guarda el ObjectOutputStream del restaurante para reenviar órdenes más adelante.
                    ServerManager.setRestaurantOut(out);
                    // Envía una confirmación al restaurante
                    out.writeObject("CONEXION RESTAURANTE CONFIRMADA");
                    out.flush();
                    // Aquí se puede implementar lógica adicional si se desea que el restaurante reciba otros tipos de mensajes.
                } else {
                    System.out.println("[Servidor] Token no reconocido: " + token + ". Cerrando conexión.");
                    socket.close();
                    return;
                }
            } else {
                System.out.println("[Servidor] Se esperaba un String como token, pero se recibió: "
                        + tokenObj.getClass().getName());
                socket.close();
                return;
            }

            // Bucle que lee objetos enviados desde la parte de cliente o restaurante.
            while (true) {
                Object obj = null;
                try {
                    obj = in.readObject();
                } catch (EOFException eof) {
                    System.out.println("[Servidor] Fin de stream.");
                    break;
                }
                if (obj == null) {
                    System.out.println("[Servidor] Objeto nulo recibido, finalizando conexión.");
                    break;
                }
                System.out.println("[Servidor] Objeto recibido: " + obj.getClass().getName());
                // Si se trata de una Orden, la procesamos y la reenviamos al restaurante.
                if (obj instanceof Orden) {
                    Orden ordenRecibida = (Orden) obj;
                    System.out.println("[Servidor] Orden recibida: " + ordenRecibida);

                    // se crea un identificador único para la orden
                    String ordenId = ordenRecibida.getNombreUsuario() + "_"
                            + ordenRecibida.getProducto() + "_"
                            + ordenRecibida.getDireccionEntrega() + "_"
                            + ordenRecibida.getPrecio();

                    // verificamos si esta orden ya fue enviada
                    if (!ordenesEnviadas.contains(ordenId)) {
                        ordenesEnviadas.add(ordenId);

                        // se reencia la orden al restaurante, si está conectado.
                        if (ServerManager.isRestaurantConnected()) {
                            try {
                                ObjectOutputStream restOut = ServerManager.getRestaurantOut();
                                restOut.reset();
                                restOut.writeObject(ordenRecibida);
                                restOut.flush();
                                System.out.println("[Servidor] Orden reenviada al restaurante.");
                            } catch (IOException ex) {
                                System.err.println("[Servidor] Error al reenviar la orden: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        } else {
                            System.err.println("[Servidor] No hay conexión con restaurante para reenviar la orden.");
                        }
                    } else {
                        System.out.println("[Servidor] Orden duplicada detectada, no reenviando: " + ordenId);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Servidor] Error en ConexionDispatcher:");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("[Servidor] Conexión cerrada.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}