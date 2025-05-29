package View;

import Controller.Orden;
import Controller.CarritoCompras;
import Model.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class InterfazCarrito extends JPanel {

    private JPanel detailsPanel;
    private JPanel totalPanel;
    private double total = 0.0;
    private FrameMain frameMain;

    public InterfazCarrito(FrameMain frameMain) {
        System.out.println("[InterfazCarrito] Iniciando interfaz del carrito...");
        this.frameMain = frameMain;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1400, 800));

        JPanel northPanel = createNorthPanel();
        add(northPanel, BorderLayout.NORTH);

        JPanel centralPanel = createCentralPanel();
        add(centralPanel, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("[InterfazCarrito] Panel mostrado, actualizando carrito y total...");
                actualizarCarrito();
                actualizarTotal();
            }
        });
    }

   private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        northPanel.setPreferredSize(new Dimension(0, 70));
        northPanel.setBackground(Color.WHITE);

        // Botón similar al de la interfaz de inicio de sesión
        JButton returnButton = new JButton("<html>October <b>Eats</b></html>");
        returnButton.setPreferredSize(new Dimension(200, 40));
        returnButton.setFont(new Font("Arial", Font.ITALIC, 25));
        returnButton.setForeground(Color.BLACK);
        returnButton.setContentAreaFilled(false);
        returnButton.setBorderPainted(false);
        returnButton.setFocusPainted(false);
        returnButton.addActionListener(e -> frameMain.mostrarPanel("Inicial"));

        northPanel.add(returnButton);
        return northPanel;
    }

    private JPanel createCentralPanel() {
        JPanel centralPanel = new JPanel();
        centralPanel.setPreferredSize(new Dimension(1400, 750));
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setPreferredSize(new Dimension(1400, 750));
        containerPanel.setBackground(Color.WHITE);

        // Panel izquierdo: detalles del carrito y pago
        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(970, 0));
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setPreferredSize(new Dimension(960, 400));
        detailsPanel.setBackground(new Color(211, 211, 211));

        JLabel cartTitleLabel = new JLabel("Tu Carrito de Compras");
        cartTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cartTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartTitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        detailsPanel.add(cartTitleLabel);
        westPanel.add(detailsPanel);

        // Panel para el pago
        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paymentPanel.setPreferredSize(new Dimension(960, 200));
        paymentPanel.setBackground(new Color(211, 211, 211));

        JButton payButton = new JButton("Pagar");
        payButton.setFont(new Font("Arial", Font.BOLD, 18));
        payButton.setPreferredSize(new Dimension(200, 50));
        payButton.setBackground(Color.WHITE);
        payButton.setForeground(Color.BLACK);
        payButton.setFocusPainted(false);
        payButton.addActionListener(e -> {
            System.out.println("[InterfazCarrito] Botón Pagar presionado.");
            List<Producto> productosEnCarrito = frameMain.getCarrito().verCarrito();
            if (productosEnCarrito.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "El carrito está vacío. Agrega productos antes de pagar.",
                        "Carrito Vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Orden ordenFinal = createOrderFromCart();
            System.out.println("[InterfazCarrito] Orden final creada: " + ordenFinal);
            frameMain.enviarOrden(ordenFinal);
            JOptionPane.showMessageDialog(null,
                    "Pedido enviado correctamente.",
                    "Pedido Enviado", JOptionPane.INFORMATION_MESSAGE);
            frameMain.getCarrito().vaciarCarrito();
            actualizarCarrito();
            actualizarTotal();
        });
        paymentPanel.add(payButton);
        westPanel.add(paymentPanel);

        // Panel derecho: resumen del pedido
        JPanel eastPanel = new JPanel(new FlowLayout());
        eastPanel.setPreferredSize(new Dimension(415, 0));
        totalPanel = new JPanel();
        totalPanel.setPreferredSize(new Dimension(400, 180));
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
        totalPanel.setBackground(Color.WHITE);

        JLabel totalTitleLabel = new JLabel("Resumen del Pedido");
        totalTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalTitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        totalPanel.add(totalTitleLabel);
        eastPanel.add(new JPanel());
        eastPanel.add(new JPanel());
        eastPanel.add(new JPanel());
        eastPanel.add(totalPanel);

        containerPanel.add(westPanel, BorderLayout.WEST);
        containerPanel.add(eastPanel, BorderLayout.EAST);
        centralPanel.add(containerPanel);

        return centralPanel;
    }

    // Actualiza el panel de detalles del carrito mostrando cada producto
    private void actualizarCarrito() {
        System.out.println("[InterfazCarrito] Actualizando detalles del carrito...");
        detailsPanel.removeAll();
        JLabel cartTitleLabel = new JLabel("Tu Carrito de Compras");
        cartTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        cartTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartTitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        detailsPanel.add(cartTitleLabel);

        List<Producto> productos = frameMain.getCarrito().verCarrito();
        if (productos.isEmpty()) {
            JLabel emptyCartLabel = new JLabel("No hay productos en el carrito");
            emptyCartLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyCartLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            detailsPanel.add(emptyCartLabel);
        } else {
            for (Producto producto : productos) {
                System.out.println("[InterfazCarrito] Agregando producto al detalle: " + producto);
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));
                itemPanel.setMaximumSize(new Dimension(900, 60));
                itemPanel.setBackground(new Color(230, 230, 230));
                itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

                JLabel itemLabel = new JLabel(producto.getNombre());
                itemLabel.setFont(new Font("Arial", Font.PLAIN, 16));

                JButton removeButton = new JButton("Eliminar");
                removeButton.setFocusPainted(false);
                removeButton.addActionListener(e -> {
                    System.out.println("[InterfazCarrito] Eliminando producto: " + producto);
                    frameMain.getCarrito().eliminarProducto(producto);
                    actualizarCarrito();
                    actualizarTotal();
                });

                itemPanel.add(itemLabel);
                itemPanel.add(Box.createHorizontalGlue());
                itemPanel.add(removeButton);
                detailsPanel.add(itemPanel);
                detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    // Actualiza el resumen del pedido (subtotal, IVA y total) a partir de los productos en el carrito
    private void actualizarTotal() {
        System.out.println("[InterfazCarrito] Actualizando resumen del pedido...");
        totalPanel.removeAll();
        JLabel totalTitleLabel = new JLabel("Resumen del Pedido");
        totalTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalTitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        totalPanel.add(totalTitleLabel);

        List<Producto> productos = frameMain.getCarrito().verCarrito();
        double suma = 0.0;
        for (Producto producto : productos) {
            suma += producto.getPrecioTotal();
        }
        total = suma;

        JLabel subtotalLabel = new JLabel("Subtotal: $" + String.format("%.2f", total));
        JLabel ivaLabel = new JLabel("IVA (16%): $" + String.format("%.2f", total * 0.16));
        JLabel totalLabel = new JLabel("Total: $" + String.format("%.2f", total * 1.16));

        subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ivaLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));

        subtotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ivaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtotalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        ivaLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        totalPanel.add(subtotalLabel);
        totalPanel.add(ivaLabel);
        totalPanel.add(totalLabel);

        totalPanel.revalidate();
        totalPanel.repaint();
    }

    /**
     * Combina el contenido actual del carrito (productos) en una única Orden final.
     */
    private Orden createOrderFromCart() {
        List<Producto> productos = frameMain.getCarrito().verCarrito();
        StringBuilder prodStr = new StringBuilder();
        double suma = 0.0;
        // Se obtienen los datos del usuario activo
        String usuario = frameMain.getUsuarioActivo().getNombre();
        String direccion = frameMain.getUsuarioActivo().getDireccion();

        for (Producto p : productos) {
            prodStr.append(p.getNombre())
                   .append(" x ").append(p.getCantidad()).append(", ");
            suma += p.getPrecioTotal();
        }
        if (prodStr.length() > 0) {
            prodStr.setLength(prodStr.length() - 2);
        }
        System.out.println("[InterfazCarrito] Creando orden final con usuario: " + usuario +
                ", dirección: " + direccion + ", productos: " + prodStr.toString() + ", suma: " + suma);
        return new Orden("Pendiente", "Efectivo", usuario, direccion, prodStr.toString(), suma);
    }
}
