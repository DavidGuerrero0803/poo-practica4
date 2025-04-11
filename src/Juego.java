public class Juego {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().mostrar();
        });
    }
}