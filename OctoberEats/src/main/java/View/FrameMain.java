package View;

import Controller.Orden;
import Controller.CarritoCompras;
// Cambiamos la importación para usar el usuario del paquete Model.
import Model.Usuario;
import Model.AutenticadorUsuarios;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class FrameMain extends JFrame {

    // Panel principal y CardLayout para la navegación.
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Instancias globales.
    private CarritoCompras carrito;
    // Ahora se utiliza la clase Usuario del paquete Model.
    private Usuario usuarioActivo;
    private AutenticadorUsuarios autenticador;

    // Variables para la conexión con el servidor.
    private Socket clientSocket;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5002;

    public FrameMain() {
        super("October Eats");
        initializeFrame();
        initializeGlobals();
        initializeConnection();
        initializePanels();
        setVisible(true);

        // Envía el token "CLIENTE" una vez que la ventana se haya abierto.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                sendToken();
            }
        });
    }

    /**
     * Configura las propiedades básicas de la ventana.
     */
    private void initializeFrame() {
        setSize(1600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Inicializa objetos globales de la aplicación.
     */
    private void initializeGlobals() {
        carrito = new CarritoCompras();
        // Inicialmente el usuario se define como "Guest".
        usuarioActivo = new Usuario("Guest", "guest@example.com", "Sin dirección", "Sin teléfono");
        autenticador = new AutenticadorUsuarios();
    }

    /**
     * Establece la conexión con el servidor y arranca el listener.
     */
    private void initializeConnection() {
        new Thread(() -> {
            try {
                System.out.println("[FrameMain] Intentando conectar al servidor...");
                clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
                outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
                inFromServer = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println("[FrameMain] Conexión establecida: " + clientSocket.getInetAddress());

                // Inicia el listener para mensajes del servidor.
                startServerListener();
            } catch (IOException ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(FrameMain.this,
                            "No se pudo conectar al servidor en " + SERVER_HOST + ":" + SERVER_PORT
                            + ". La aplicación se iniciará en modo offline.",
                            "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                });
            }
        }).start();
    }

    private void startServerListener() {
        new Thread(() -> {
            try {
                while (true) {
                    Object mensaje = inFromServer.readObject();
                    System.out.println("[FrameMain] Mensaje recibido del servidor: " + mensaje);

                    if (mensaje instanceof String) {
                        String msg = (String) mensaje;
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(FrameMain.this, msg, "Estado de Orden", JOptionPane.INFORMATION_MESSAGE);
                        });
                    } else if (mensaje instanceof Orden) {
                        Orden ordenRecibida = (Orden) mensaje;
                        String estado = ordenRecibida.getEstado().toLowerCase();
                        final String mensajeAlerta;
                        if (estado.equals("confirmado")) {
                            mensajeAlerta = "Tu orden ha sido aceptada.";
                        } else if (estado.equals("rechazado")) {
                            mensajeAlerta = "Tu orden ha sido rechazada.";
                        } else if (estado.equals("enviada")) {
                            mensajeAlerta = "Tu orden fue enviada.";
                        } else {
                            mensajeAlerta = "";
                        }
                        if (!mensajeAlerta.isEmpty()) {
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(FrameMain.this, mensajeAlerta, "Estado de Orden", JOptionPane.INFORMATION_MESSAGE);
                            });
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("[FrameMain] Error al recibir mensaje del servidor: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Configura y agrega las interfaces al CardLayout.
     */
    private void initializePanels() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new InterfazInicial(this), "Inicial");
        mainPanel.add(new InterfazMenu(this), "Menu");
        mainPanel.add(new InterfazCarrito(this), "Carrito");
        mainPanel.add(new InterfazInicioDeSesion(this), "InicioDeSesion");
        mainPanel.add(new InterfazRegistro(this), "Registro");

        add(mainPanel);
        cardLayout.show(mainPanel, "Inicial");
    }

    /**
     * Envía el token "CLIENTE" al servidor.
     */
    private void sendToken() {
        if (clientSocket != null && clientSocket.isConnected() && outToServer != null) {
            try {
                outToServer.writeObject("CLIENTE");
                outToServer.flush();
                System.out.println("[FrameMain] Token 'CLIENTE' enviado. Conexión confirmada: " + clientSocket.getInetAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error al enviar el token al servidor.",
                        "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.err.println("[FrameMain] La conexión no está establecida; no se pudo enviar el token.");
        }
    }

    /**
     * Envía al servidor un objeto Orden utilizando la conexión establecida.
     */
    public void enviarOrden(Orden orden) {
        try {
            if (outToServer != null) {
                outToServer.writeObject(orden);
                outToServer.flush();
                System.out.println("[FrameMain] Orden enviada: " + orden);
            } else {
                System.err.println("[FrameMain] La conexión al servidor no está establecida.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al enviar la orden.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Permite cambiar la interfaz mostrada en el CardLayout.
     */
    public void mostrarPanel(String panelName) {
        System.out.println("[FrameMain] Mostrando panel: " + panelName);
        cardLayout.show(mainPanel, panelName);
    }

    // Getters para los objetos globales.
    public CarritoCompras getCarrito() {
        return carrito;
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public AutenticadorUsuarios getAutenticador() {
        return autenticador;
    }

    // Setter para actualizar el usuario global.
    public void setUsuarioActivo(Usuario usuario) {
        this.usuarioActivo = usuario;
    }

    /**
     * Cierra la conexión con el servidor.
     */
    public void closeConnection() {
        try {
            if (outToServer != null) {
                outToServer.close();
            }
            if (inFromServer != null) {
                inFromServer.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
            System.out.println("[FrameMain] Conexión cerrada.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
