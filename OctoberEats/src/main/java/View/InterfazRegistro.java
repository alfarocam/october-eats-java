package View;

import Controller.Usuario;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InterfazRegistro extends JPanel {

    private FrameMain frameMain; // Referencia para navegación
    
    public InterfazRegistro(FrameMain frameMain) {
        this.frameMain = frameMain;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1400, 800));

        // PANEL ENCABEZADO
        JPanel encabezado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        encabezado.setPreferredSize(new Dimension(0, 70));
        encabezado.setBackground(Color.BLACK);

        JButton octoberEatsButton = new JButton("<html>October <b>Eats</b></html>");
        octoberEatsButton.setPreferredSize(new Dimension(200, 40));
        octoberEatsButton.setFont(new Font("Arial", Font.ITALIC, 25));
        octoberEatsButton.setForeground(Color.WHITE);
        octoberEatsButton.setContentAreaFilled(false);
        octoberEatsButton.setBorderPainted(false);
        octoberEatsButton.setFocusPainted(false);
        octoberEatsButton.addActionListener(e -> frameMain.mostrarPanel("Inicial"));
        encabezado.add(octoberEatsButton);

        add(encabezado, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel centralPanel = new JPanel();
        centralPanel.setBackground(Color.WHITE);
        centralPanel.setPreferredSize(new Dimension(1000, 1000));

        JPanel CCPanel = new JPanel(new GridLayout(16, 1));
        CCPanel.setPreferredSize(new Dimension(350, 750));
        CCPanel.setBackground(Color.WHITE);
        centralPanel.add(CCPanel);

        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(290, 80));
        space.setBackground(new Color(0, 0, 0, 0));
        CCPanel.add(space);

        JLabel etiquetaRegistroNombre = new JLabel("<html>Cual es tu nombre?</html>");
        JTextArea textoRegistroNombre = new JTextArea();

        JLabel etiquetaRegistroCorreo = new JLabel("<html>Cual es tu numero de telefono o<br>correo?</html>");
        JTextArea textoRegistroCorreo = new JTextArea();

        JLabel etiquetaRegistroClave = new JLabel("<html>Cual es tu contraseña?</html>");
        JTextArea textoRegistroClave = new JTextArea();

        JLabel etiquetaRegistroUbicacion = new JLabel("<html>Indica cual es tu ubicacion</html>");
        JTextArea textoRegistroUbicacion = new JTextArea();

        JPanel panelEspaciador = new JPanel();
        panelEspaciador.setBackground(Color.WHITE);

        JButton botonDeConfirmacion = new JButton("Confirmar Información");
        botonDeConfirmacion.setPreferredSize(new Dimension(340, 40));
        botonDeConfirmacion.setFont(new Font("Segoe UI", Font.BOLD, 15));
        botonDeConfirmacion.setBackground(new Color(245, 245, 245));
        botonDeConfirmacion.setForeground(Color.BLACK);
        botonDeConfirmacion.setFocusPainted(false);
        botonDeConfirmacion.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));

        JLabel[] etiquetas = {etiquetaRegistroNombre, etiquetaRegistroCorreo, etiquetaRegistroClave, etiquetaRegistroUbicacion};
        for (JLabel lbl : etiquetas) {
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lbl.setPreferredSize(new Dimension(340, 28));
            lbl.setForeground(new Color(50, 50, 50));
        }

        JTextArea[] areasDeTexto = {textoRegistroNombre, textoRegistroCorreo, textoRegistroClave, textoRegistroUbicacion};
        for (JTextArea ta : areasDeTexto) {
            ta.setPreferredSize(new Dimension(340, 30));
            ta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            ta.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
            ));
            ta.setBackground(new Color(250, 250, 250));
            ta.setLineWrap(true);
            ta.setWrapStyleWord(true);
        }

        CCPanel.add(etiquetaRegistroNombre);
        CCPanel.add(textoRegistroNombre);
        CCPanel.add(etiquetaRegistroCorreo);
        CCPanel.add(textoRegistroCorreo);
        CCPanel.add(etiquetaRegistroClave);
        CCPanel.add(textoRegistroClave);
        CCPanel.add(etiquetaRegistroUbicacion);
        CCPanel.add(textoRegistroUbicacion);
        CCPanel.add(panelEspaciador);
        CCPanel.add(botonDeConfirmacion);

        botonDeConfirmacion.addActionListener(e -> {
            String nombre = textoRegistroNombre.getText();
            String correo = textoRegistroCorreo.getText();
            String clave = textoRegistroClave.getText();
            String direccion = textoRegistroUbicacion.getText();

            Usuario usuario = new Usuario(nombre, correo, clave, direccion);
            System.out.println(usuario);
            // Opcional: luego se podría navegar a otro panel, por ejemplo:
            // frameMain.mostrarPanel("InicioDeSesion");
        });

        add(centralPanel, BorderLayout.CENTER);
    }
}
