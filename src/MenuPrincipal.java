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
        JLabel backgroundLabel = new JLabel(new ImageIcon("C:\\Users\\david\\IdeaProjects\\PruebaCodeFinal\\src\\PortadaFarkle.png"));
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
        btnJugar.addActionListener(e -> iniciarElFarkle());
        btnJugar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(220, 80));
        btnSalir.setFont(new Font("Arial", Font.BOLD, 20));
        btnSalir.setMargin(new Insets(10, 10, 10, 10));
        btnSalir.setFocusable(false);
        btnSalir.addActionListener(e -> salir());
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
