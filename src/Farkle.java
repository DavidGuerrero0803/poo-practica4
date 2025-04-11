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