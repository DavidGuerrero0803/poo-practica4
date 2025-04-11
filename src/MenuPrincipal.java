import javax.swing.*;
import java.awt.*;

public class MenuPrincipal {
    private JFrame frame;

    public MenuPrincipal() {
        frame = new JFrame("Farkle | Menú Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(512, 512);
        frame.setLayout(new BorderLayout());

        // Panel de fondo con imagen
        JLabel backgroundLabel = new JLabel(new ImageIcon("/src/PortadaFarkle.png"));
        backgroundLabel.setLayout(new BorderLayout());

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(220, 0, 0, 0));

        // Botón Jugar
        JButton btnJugar = new JButton("Jugar");
        btnJugar.setPreferredSize(new Dimension(220, 80));
        btnJugar.setFont(new Font("Arial", Font.BOLD, 20));
        btnJugar.setMargin(new Insets(10, 10, 10, 10));
        btnJugar.setFocusable(false);
        btnJugar.addActionListener(e -> iniciarJuego());
        btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(220, 80));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 20));
        btnSalir.setMargin(new Insets(10, 10, 10, 10));
        btnSalir.setFocusable(false);
        btnSalir.addActionListener(e -> salir());
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Añadir componentes
        panelBotones.add(btnJugar);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 22)));
        panelBotones.add(btnSalir);

        backgroundLabel.add(panelBotones, BorderLayout.CENTER);
        frame.add(backgroundLabel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    private void iniciarJuego() {
        String inputJugadores = JOptionPane.showInputDialog(
                frame,
                "¿Cuántos jugadores van a jugar?:",
                "Número de Jugadores",
                JOptionPane.QUESTION_MESSAGE
        );

        if (inputJugadores == null) {
            return;
        }

        try {
            int jugadores = Integer.parseInt(inputJugadores);
            if (jugadores < 2) {
                throw new IllegalArgumentException("Debe haber al menos 2 jugadores.");
            }

            String inputPuntos = JOptionPane.showInputDialog(
                    frame,
                    "Puntos requeridos para ganar:",
                    "Configuración del Juego",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (inputPuntos == null) {
                return;
            }

            int puntosVictoria = Integer.parseInt(inputPuntos);


            new Farkle(jugadores, puntosVictoria);
            frame.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Ingrese un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    frame,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void salir() {
        int confirmacion = JOptionPane.showConfirmDialog(
                frame,
                "¿Quieres salir del juego?",
                "Salir del juego",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}