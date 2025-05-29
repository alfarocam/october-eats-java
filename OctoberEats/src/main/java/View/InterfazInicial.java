package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazInicial extends JPanel {

    private FrameMain frameMain; // Referencia para navegación y objetos globales

    // Constructor que recibe el FrameMain.
    public InterfazInicial(FrameMain frameMain) {
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
        centralPanel.setBackground(Color.WHITE);
        centralPanel.setPreferredSize(new Dimension(1400, 750));

        JPanel CCPanel = new JPanel(new BorderLayout());
        CCPanel.setPreferredSize(new Dimension(1400, 750));
        CCPanel.setBackground(Color.WHITE);
        centralPanel.add(CCPanel, BorderLayout.CENTER);

        // Panel de categorías
        JPanel NCCPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 17));
        NCCPanel.setPreferredSize(new Dimension(0, 60));
        NCCPanel.setBackground(Color.WHITE);
        String[] categories = {"Todos", "October Eats", "Super", "Express", "Alcohol", "Farmacia", "Gourmet", "Retail", "Mascota", "Flores", "Bebe", "Electronica"};
        for (String cat : categories) {
            JButton btn = new JButton(cat);
            btn.setFont(new Font("Arial", Font.ITALIC, 15));
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            NCCPanel.add(btn);
        }
        CCPanel.add(NCCPanel, BorderLayout.NORTH);

        // Panel de restaurantes
        JPanel CCCPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        CCCPanel.setPreferredSize(new Dimension(1400, 690));
        CCCPanel.setBackground(Color.WHITE);

        // Crear 8 botones simulando restaurantes. Al pulsarlos se navega a "Menu".
        for (int i = 0; i < 8; i++) {
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(344, 300));
            btn.setBackground(Color.WHITE);
            btn.setLayout(new FlowLayout());

            JPanel imagePanel = new JPanel();
            imagePanel.setBackground(new Color(211, 211, 211));
            imagePanel.setPreferredSize(new Dimension(342, 205));
            JLabel imgLabel = new JLabel();
            ImageIcon restIcon = new ImageIcon("images/restaurante.png");
            ImageIcon restIconScaled = new ImageIcon(restIcon.getImage().getScaledInstance(340, 203, Image.SCALE_FAST));
            imgLabel.setIcon(restIconScaled);
            imagePanel.add(imgLabel);
            btn.add(imagePanel);

            JPanel namePanel = new JPanel();
            namePanel.setBackground(Color.GRAY);
            namePanel.setPreferredSize(new Dimension(342, 63));
            btn.add(namePanel);

            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.addActionListener(e -> frameMain.mostrarPanel("Menu"));
            CCCPanel.add(btn);
        }
        CCPanel.add(CCCPanel, BorderLayout.CENTER);

        add(centralPanel, BorderLayout.CENTER);
    }
}
