package View;

import Model.AutenticadorUsuarios;
import Model.Usuario;
import Model.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfazInicioDeSesion extends JPanel {

    private int intentos = 0;
    private FrameMain frameMain;  // Referencia para navegación y uso de objetos globales

    public InterfazInicioDeSesion(FrameMain frameMain) {
        this.frameMain = frameMain;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1400, 800));

        // Panel superior
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        northPanel.setPreferredSize(new Dimension(0, 70));
        northPanel.setBackground(Color.BLACK);

        JButton octoberEatsButton = new JButton("<html>October <b>Eats</b></html>");
        octoberEatsButton.setPreferredSize(new Dimension(200, 40));
        octoberEatsButton.setFont(new Font("Arial", Font.ITALIC, 25));
        octoberEatsButton.setForeground(Color.WHITE);
        octoberEatsButton.setContentAreaFilled(false);
        octoberEatsButton.setBorderPainted(false);
        octoberEatsButton.setFocusPainted(false);
        octoberEatsButton.addActionListener(e -> frameMain.mostrarPanel("Inicial"));
        northPanel.add(octoberEatsButton);
        add(northPanel, BorderLayout.NORTH);

        // Panel central de email
        JPanel centralPanel = new JPanel(new FlowLayout());
        centralPanel.setBackground(Color.WHITE);
        centralPanel.setPreferredSize(new Dimension(1000, 1000));

        JPanel CCPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        CCPanel.setPreferredSize(new Dimension(350, 750));
        CCPanel.setBackground(new Color(0, 0, 0, 0));
        centralPanel.add(CCPanel);

        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(290, 150));
        space.setBackground(new Color(0, 0, 0, 0));
        CCPanel.add(space);

        JLabel titleOne = new JLabel("<html>What's your phone number or<br>email?</html>");
        titleOne.setFont(new Font("Arial", Font.ROMAN_BASELINE, 21));
        titleOne.setPreferredSize(new Dimension(290, 40));
        CCPanel.add(titleOne);

        JTextArea inputEmailArea = new JTextArea();
        inputEmailArea.setPreferredSize(new Dimension(290, 40));
        inputEmailArea.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        CCPanel.add(inputEmailArea);

        JButton continueButton = new JButton("Continue");
        continueButton.setPreferredSize(new Dimension(290, 40));
        continueButton.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
        continueButton.setForeground(Color.WHITE);
        continueButton.setBackground(Color.BLACK);
        continueButton.setBorderPainted(false);
        continueButton.setFocusPainted(false);
        continueButton.addActionListener(e -> {
            String email = inputEmailArea.getText().trim();
            AutenticadorUsuarios autenticador = frameMain.getAutenticador();
            boolean existe = autenticador.verificarUsuario(email, null);
            if (existe) {
                mostrarDialogoContraseña(email, autenticador);
            } else {
                JOptionPane.showMessageDialog(InterfazInicioDeSesion.this,
                        "El correo no existe. Por favor, verifica.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        CCPanel.add(continueButton);

        add(centralPanel, BorderLayout.CENTER);
    }

    // Muestra el diálogo para ingresar contraseña y actualiza el usuario global en FrameMain tras un login exitoso.
    private void mostrarDialogoContraseña(String email, AutenticadorUsuarios autenticador) {
        JDialog dialog = new JDialog((Frame) null, "Ingresar Contraseña", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Ingrese su contraseña:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        panel.add(label);
        panel.add(passwordField);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            boolean acceso = autenticador.verificarUsuario(email, password);
            if (acceso) {
                // Recupera los datos reales del usuario desde la BD mediante UsuarioDAO.
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario usuarioObtenido = usuarioDAO.obtenerUsuarioPorCorreo(email);
                if (usuarioObtenido != null) {
                    // Actualiza la variable global en FrameMain.
                    frameMain.setUsuarioActivo(usuarioObtenido);
                }
                JOptionPane.showMessageDialog(dialog, "¡Inicio de sesión exitoso!",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                frameMain.mostrarPanel("Menu");
            } else {
                intentos++;
                if (intentos >= 4) {
                    JOptionPane.showMessageDialog(dialog,
                            "Has alcanzado el límite de intentos. Acceso bloqueado.",
                            "Bloqueo", JOptionPane.ERROR_MESSAGE);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Contraseña incorrecta. Intentos restantes: " + (4 - intentos),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }
}
