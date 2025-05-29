package View;

import Controller.CarritoCompras;
import Model.Producto;
import javax.swing.*;
import java.awt.*;

public class InterfazMenu extends JPanel {

    // Referencia para navegación y para obtener el carrito que se creó en FrameMain
    private FrameMain frameMain;

    public InterfazMenu(FrameMain frameMain) {
        this.frameMain = frameMain;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1400, 800));

        // PANEL SUPERIOR
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        northPanel.setPreferredSize(new Dimension(0, 70));
        northPanel.setBackground(Color.WHITE);

        JButton threeLinesButton = new JButton("≡");
        threeLinesButton.setPreferredSize(new Dimension(60, 40));
        threeLinesButton.setFont(new Font("Arial", Font.BOLD, 30));
        northPanel.add(threeLinesButton);

        JButton octoberEatsButton = new JButton("<html>October <b>Eats</b></html>");
        octoberEatsButton.setPreferredSize(new Dimension(200, 40));
        octoberEatsButton.setFont(new Font("Arial", Font.ITALIC, 20));
        octoberEatsButton.addActionListener(e -> frameMain.mostrarPanel("Inicial"));
        northPanel.add(octoberEatsButton);

        JPanel northSpace = new JPanel();
        northSpace.setPreferredSize(new Dimension(400, 40));
        northSpace.setBackground(new Color(0, 0, 0, 0));
        northPanel.add(northSpace);

        JTextArea searchArea = new JTextArea();
        searchArea.setPreferredSize(new Dimension(400, 40));
        searchArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        northPanel.add(searchArea);

        JButton cartButton = new JButton();
        cartButton.setPreferredSize(new Dimension(60, 40));
        cartButton.setFont(new Font("Arial", Font.BOLD, 20));
        ImageIcon cartIcon = new ImageIcon("images/cart.png");
        ImageIcon cartIconScaled = new ImageIcon(cartIcon.getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
        cartButton.setIcon(cartIconScaled);
        cartButton.addActionListener(e -> frameMain.mostrarPanel("Carrito"));
        northPanel.add(cartButton);

        JButton signInButton = new JButton("Iniciar Sesion");
        signInButton.setPreferredSize(new Dimension(200, 40));
        signInButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        signInButton.addActionListener(e -> frameMain.mostrarPanel("InicioDeSesion"));
        northPanel.add(signInButton);

        JButton signUpButton = new JButton("Registrarse");
        signUpButton.setPreferredSize(new Dimension(150, 40));
        signUpButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        signUpButton.addActionListener(e -> frameMain.mostrarPanel("Registro"));
        northPanel.add(signUpButton);

        JButton[] northButtons = {threeLinesButton, octoberEatsButton, cartButton, signInButton, signUpButton};
        for (JButton b : northButtons) {
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
        }
        add(northPanel, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBackground(Color.PINK);
        centralPanel.setPreferredSize(new Dimension(1400, 750));

        JPanel CCPanel = new JPanel(new BorderLayout());
        CCPanel.setPreferredSize(new Dimension(1400, 750));
        CCPanel.setBackground(Color.WHITE);
        centralPanel.add(CCPanel, BorderLayout.CENTER);

        // Panel de categorías en la parte superior central (opcional)
        JPanel NCCPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 17));
        NCCPanel.setPreferredSize(new Dimension(0, 200));
        NCCPanel.setBackground(new Color(0, 0, 0, 0));
        String[] tabs = {"Novedades October Eats", "Family", "Combos", "Menu", "Collection", "Especialidades", "Postres"};
        for (String tab : tabs) {
            JButton btn = new JButton(tab);
            btn.setFont(new Font("Arial", Font.ITALIC, 15));
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            NCCPanel.add(btn);
        }
        CCPanel.add(NCCPanel, BorderLayout.NORTH);

        // Panel de productos
        JPanel CCCPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        CCCPanel.setPreferredSize(new Dimension(1400, 550));
        CCCPanel.setBackground(Color.WHITE);

        // Arreglo de productos ficticios
        String[] productNames = {"Hamburguesa", "Pizza", "Tacos", "Pasta", "Ensalada", "Postre"};
        double[] productPrices = {8.99, 12.99, 7.50, 9.99, 6.50, 5.99};

        // Para cada producto, se crea un panel y un botón para agregar al carrito
        for (int i = 0; i < productNames.length; i++) {
            JPanel productPanel = createProductPanel(productNames[i], productPrices[i]);
            CCCPanel.add(productPanel);
        }
        CCPanel.add(CCCPanel, BorderLayout.CENTER);
        add(centralPanel, BorderLayout.CENTER);
    }

    // Crea un panel para un producto
    private JPanel createProductPanel(String productName, double price) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(695, 200));
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout());

        JPanel namePanel = new JPanel();
        namePanel.setBackground(new Color(211, 211, 211));
        namePanel.setPreferredSize(new Dimension(510, 189));
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(productName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
        namePanel.add(nameLabel);

        JLabel priceLabel = new JLabel("Precio: $" + price);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));
        namePanel.add(priceLabel);

        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        quantitySpinner.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantitySpinner.setMaximumSize(new Dimension(80, 30));
        namePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        namePanel.add(quantitySpinner);

        panel.add(namePanel);

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.GRAY);
        imagePanel.setPreferredSize(new Dimension(170, 189));
        imagePanel.setLayout(new BorderLayout());

        // Botón "Agregar" refactorizado: crea un objeto Producto en lugar de Orden
        JButton addToCartButton = new JButton("Agregar");
        addToCartButton.setBackground(new Color(70, 70, 70));
        addToCartButton.setForeground(new Color(40, 40, 40));
        addToCartButton.setFocusPainted(false);
        addToCartButton.addActionListener(e -> {
            int cantidad = (int) quantitySpinner.getValue();
            // Se crea un objeto Producto usando el nombre, precio y cantidad
            Producto nuevoProducto = new Producto(productName, price, cantidad);
            // Se agrega al carrito. Se asume que FrameMain tiene un método getCarrito() que retorna un CarritoCompras refactorizado para Productos.
            frameMain.getCarrito().agregarProducto(nuevoProducto);
            JOptionPane.showMessageDialog(null, "Agregado al carrito: " + cantidad + " x " + productName);
        });
        imagePanel.add(addToCartButton, BorderLayout.CENTER);
        panel.add(imagePanel);

        return panel;
    }
}
