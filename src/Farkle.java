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

    private void prepararJuego() {
        iconosCaras[5] = new ImageIcon("src/DadoCara6.png");
        iconosCaras[4] = new ImageIcon("src/DadoCara5.png");
        iconosCaras[3] = new ImageIcon("src/DadoCara4.png");
        iconosCaras[2] = new ImageIcon("src/DadoCara3.png");
        iconosCaras[1] = new ImageIcon("src/DadoCara2.png");
        iconosCaras[0] = new ImageIcon("src/DadoCara1.png");

        for (int i = 0; i < 6; i++) {
            botonesDado[i].setIcon(iconosCaras[i]);
            estadosDado[i] = ESTADO_ACTIVO;
            botonesDado[i].setBackground(Color.WHITE);
            botonesDado[i].setEnabled(false);
        }

        btnLanzar.setEnabled(true);
        btnAnotar.setEnabled(false);
        btnDetener.setEnabled(false);

        marcoPrincipal.setContentPane(panelJuego);
        marcoPrincipal.revalidate();
        marcoPrincipal.repaint();
        actualizarInterfaz();
    }

    private void lanzarDados() {
        lblMensaje.setText("");
        IntStream.range(0, botonesDado.length)
                .filter(i -> estadosDado[i] == ESTADO_ACTIVO)
                .forEach(i -> {
                    int resultado = (int) (Math.random() * 6);
                    valoresDado[i] = resultado;
                    botonesDado[i].setIcon(iconosCaras[resultado]);
                    botonesDado[i].setEnabled(true);
                });

        btnLanzar.setEnabled(false);
        btnAnotar.setEnabled(true);
        btnDetener.setEnabled(false);
    }

    private void calcularPuntos() {
        int[] frecuencia = new int[7];
        int totalSeleccionados = 0;

        for (int i = 0; i < botonesDado.length; i++) {
            if (estadosDado[i] == ESTADO_SELECCIONADO) {
                frecuencia[valoresDado[i] + 1]++;
                totalSeleccionados++;
            }
        }

        if (totalSeleccionados == 0) {
            int opcion = JOptionPane.showConfirmDialog(marcoPrincipal,
                    "¿Cancelar puntos actuales?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                puntosRonda = 0;
                siguienteJugador();
            }
            return;
        }

        int scoringDiceCount = 0;
        for (int i = 1; i <= 6; i++) {
            if (i == 1 || i == 5) {
                scoringDiceCount += frecuencia[i];
            } else {
                if (frecuencia[i] >= 3) {
                    scoringDiceCount += frecuencia[i];
                }
            }
        }
        if (scoringDiceCount != totalSeleccionados) {
            JOptionPane.showMessageDialog(marcoPrincipal,
                    "Selección inválida. Debes seleccionar:\n" +
                            "- 1's o 5's individuales\n" +
                            "- Tríos\n" +
                            "- Triple par (3 pares)",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int pares = 0;
        boolean esTriplePar = true;
        for (int i = 1; i <= 6; i++) {
            if (frecuencia[i] == 2) {
                pares++;
            } else if (frecuencia[i] != 0 && frecuencia[i] != 3) {
                esTriplePar = false;
            }
        }
        if (esTriplePar && pares == 3) {
            puntosRonda += 750;
            lblMensaje.setText("¡Tres pares! +750 puntos");
            bloquearDados();
            return;
        }

        int puntosCalculados = 0;

        if (frecuencia[1] >= 3) puntosCalculados += (frecuencia[1] - 2) * 1000;
        if (frecuencia[2] >= 3) puntosCalculados += (frecuencia[2] - 2) * 200;
        if (frecuencia[3] >= 3) puntosCalculados += (frecuencia[3] - 2) * 300;
        if (frecuencia[4] >= 3) puntosCalculados += (frecuencia[4] - 2) * 400;
        if (frecuencia[5] >= 3) puntosCalculados += (frecuencia[5] - 2) * 500;
        if (frecuencia[6] >= 3) puntosCalculados += (frecuencia[6] - 2) * 600;

        if (frecuencia[1] < 3) puntosCalculados += frecuencia[1] * 100;
        if (frecuencia[5] < 3) puntosCalculados += frecuencia[5] * 50;

        if (puntosCalculados == 0) {
            JOptionPane.showMessageDialog(marcoPrincipal,
                    "La selección no contiene combinaciones válidas",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        puntosRonda += puntosCalculados;
        lblMensaje.setText("+" + puntosCalculados + " puntos");
        bloquearDados();
    }

    private void bloquearDados() {
        for (int j = 0; j < botonesDado.length; j++) {
            if (estadosDado[j] == ESTADO_SELECCIONADO) {
                estadosDado[j] = ESTADO_BLOQUEADO;
                botonesDado[j].setBackground(new Color(255, 200, 200));
            }
            botonesDado[j].setEnabled(false);
        }

        long bloqueoTotal = Arrays.stream(estadosDado)
                .filter(e -> e == ESTADO_BLOQUEADO)
                .count();

        if (bloqueoTotal == 6) {
            for (int j = 0; j < botonesDado.length; j++) {
                estadosDado[j] = ESTADO_ACTIVO;
                botonesDado[j].setBackground(Color.WHITE);
            }
            lblMensaje.setText(lblMensaje.getText() + " ¡Hot Dice!");
        }

        lblPuntosActuales.setText("Puntos en juego: " + puntosRonda);
        btnLanzar.setEnabled(true);
        btnAnotar.setEnabled(false);
        btnDetener.setEnabled(true);
    }

    private void finalizarTurno() {
        puntajesJugadores[turnoJugador] += puntosRonda;
        if (puntajesJugadores[turnoJugador] >= puntosVictoria && !rondaExtra) {
            rondaExtra = true;
            idJugadorMeta = turnoJugador;
            restantesRondaExtra = totalJugadores - 1;
            JOptionPane.showMessageDialog(marcoPrincipal, "¡Jugador " +
                    (turnoJugador + 1) + " alcanzó la meta!\nOtros jugadores tendrán un turno adicional.");
        }
        siguienteJugador();
    }

    private void alternarEstado(int indice) {
        if (estadosDado[indice] == ESTADO_ACTIVO) {
            botonesDado[indice].setBackground(new Color(200, 255, 200));
            estadosDado[indice] = ESTADO_SELECCIONADO;
        } else {
            botonesDado[indice].setBackground(Color.WHITE);
            estadosDado[indice] = ESTADO_ACTIVO;
        }
    }

    private void siguienteJugador() {
        puntosRonda = 0;
        if (rondaExtra && turnoJugador != idJugadorMeta) {
            restantesRondaExtra--;
            if (restantesRondaExtra == 0) {
                mostrarResultadoFinal();
                return;
            }
        }
        turnoJugador = (turnoJugador + 1) % totalJugadores;
        ronda++;
        actualizarInterfaz();
        reiniciarDados();
    }

    private void mostrarResultadoFinal() {
        int maximo = -1;
        int ganador = -1;
        StringBuilder resumen = new StringBuilder("FIN DEL JUEGO\n");

        for (int i = 0; i < totalJugadores; i++) {
            resumen.append("Jugador ").append(i + 1).append(": ").append(puntajesJugadores[i]).append(" puntos\n");
            if (puntajesJugadores[i] > maximo) {
                maximo = puntajesJugadores[i];
                ganador = i;
            }
        }
        resumen.append("\nGanó el jugador ").append(ganador + 1).append("!");

        // Mostrar resultados
        JOptionPane.showMessageDialog(marcoPrincipal, resumen.toString());

        // Preguntar si desean jugar nuevamente
        int opcion = JOptionPane.showConfirmDialog(
                marcoPrincipal,
                "¿Quieres jugar otra partida?",
                "Fin del juego",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            reiniciarJuego();
        } else {
            marcoPrincipal.dispose();
            new MenuPrincipal().mostrar(); // Volver al menú principal
        }
    }

    private void reiniciarJuego() {
        // Reiniciar variables del juego
        puntosRonda = 0;
        ronda = 1;
        turnoJugador = 0;
        rondaExtra = false;
        idJugadorMeta = -1;
        restantesRondaExtra = 0;

        Arrays.fill(puntajesJugadores, 0);

        for (int i = 0; i < 6; i++) {
            estadosDado[i] = ESTADO_ACTIVO;
            botonesDado[i].setBackground(Color.WHITE);
            botonesDado[i].setEnabled(false);
        }

        btnLanzar.setEnabled(true);
        btnAnotar.setEnabled(false);
        btnDetener.setEnabled(false);

        actualizarInterfaz();
        lblMensaje.setText(" ");
    }
