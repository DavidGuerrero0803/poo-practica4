import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Farkle {
    private JFrame marcoPrincipal;
    private JPanel panelJuego;
    private JPanel areaDados;
    private JPanel controlesPrincipales;
    private JPanel panelInformacion;
    private JButton[] botonesDado = new JButton[6];
    private ImageIcon[] iconosCaras = new ImageIcon[6];
    private int[] estadosDado = new int[6];
    private int[] valoresDado = new int[6];

    final int ESTADO_ACTIVO = 0;
    final int ESTADO_SELECCIONADO = 1;
    final int ESTADO_BLOQUEADO = 2;

    private JButton btnLanzar = new JButton("Lanzar Dados");
    private JButton btnAnotar = new JButton("Anotar");
    private JButton btnDetener = new JButton("Acumular");
    private JButton btnInstrucciones = new JButton("Combinaciones");

    private JLabel lblPuntosActuales = new JLabel("Puntos en juego: 0", JLabel.CENTER);
    private JLabel lblPuntosTotales = new JLabel("Puntos acumulados: 0", JLabel.CENTER);
    private JLabel lblRonda = new JLabel("Ronda: 1", JLabel.CENTER);
    private JLabel lblTurno = new JLabel("Jugador actual: 1", JLabel.CENTER);
    private JLabel lblMensaje = new JLabel(" ", JLabel.CENTER);

    private int puntosRonda = 0;
    private int ronda = 1;
    private int turnoJugador = 0;
    private int totalJugadores;
    private int puntosVictoria;
    private int[] puntajesJugadores;
    private boolean rondaExtra = false;
    private int idJugadorMeta = -1;
    private int restantesRondaExtra = 0;

    public Farkle(int jugadores, int puntosParaGanar) {
        this.totalJugadores = jugadores;
        this.puntosVictoria = puntosParaGanar;
        this.puntajesJugadores = new int[totalJugadores];

        marcoPrincipal = new JFrame("Farkle");
        marcoPrincipal.setSize(800, 600);
        marcoPrincipal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarInterfaz();
        prepararJuego();
        marcoPrincipal.setVisible(true);
    }

    private void inicializarInterfaz() {
        panelJuego = new JPanel(new BorderLayout(10, 10));
        panelJuego.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelJuego.setBackground(new Color(240, 240, 240));

        areaDados = new JPanel(new GridLayout(2, 3, 10, 10));
        areaDados.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        areaDados.setBackground(new Color(240, 240, 240));

        for (int i = 0; i < 6; i++) {
            botonesDado[i] = new JButton();
            botonesDado[i].setPreferredSize(new Dimension(80, 80));
            botonesDado[i].setBackground(Color.WHITE);
            botonesDado[i].setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            botonesDado[i].setEnabled(false);

            final int indice = i;
            botonesDado[i].addActionListener(e -> alternarEstado(indice));
            areaDados.add(botonesDado[i]);
        }

        controlesPrincipales = new JPanel(new GridLayout(1, 4, 10, 10));
        controlesPrincipales.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnLanzar.addActionListener(e -> lanzarDados());
        btnAnotar.addActionListener(e -> calcularPuntos());
        btnDetener.addActionListener(e -> finalizarTurno());
        btnInstrucciones.addActionListener(e -> mostrarInstrucciones());

        controlesPrincipales.add(btnLanzar);
        controlesPrincipales.add(btnAnotar);
        controlesPrincipales.add(btnDetener);
        controlesPrincipales.add(btnInstrucciones);

        panelInformacion = new JPanel(new GridLayout(5, 1, 5, 5));
        panelInformacion.setBorder(BorderFactory.createTitledBorder("InformaciÃ³n del Juego"));
        panelInformacion.add(lblTurno);
        panelInformacion.add(lblRonda);
        panelInformacion.add(lblPuntosActuales);
        panelInformacion.add(lblPuntosTotales);
        panelInformacion.add(lblMensaje);

        panelJuego.add(controlesPrincipales, BorderLayout.NORTH);
        panelJuego.add(areaDados, BorderLayout.CENTER);
        panelJuego.add(panelInformacion, BorderLayout.SOUTH);
    }