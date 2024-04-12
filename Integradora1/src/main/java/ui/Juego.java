package ui;
import model.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Juego {
    private ArrayList<Cola<Carta>> mazosJugadores;
    private Mazo<Carta> mazo; // Cambio de tipo aquí
    private Mazo<Carta> mazoDescarte;
    private ArrayList<Jugador> jugadores;
    private Carta cartaInicial;
    private Jugador jugadorActual;

    public Juego(int numJugadores) {
        mazo = new Mazo<>(new Pila<>());
        mazoDescarte = new Mazo<>(new Pila<>());
        jugadores = new ArrayList<>();

        System.out.println("");
        crearMazo(mazo);
        if (mazo == null) {
            System.out.println("¡Error! El mazo es null.");
        } else {
            System.out.println("¡El mazo se ha inicializado correctamente!");
        }

        for (int i = 0; i < numJugadores; i++) {
            Cola<Carta> mazoJugador = new Cola<>();
            Jugador<Carta> jugador = new Jugador<>("Jugador " + (i + 1), mazoJugador);
            jugadores.add(jugador);
            // Llenar el mazo del jugador con 7 cartas al inicio del juego
            for (int j = 0; j < 7; j++) {
                mazoJugador.enqueue(mazo.robarCarta());
            }
        }
        cartaInicial = mazo.robarCarta();
        mazoDescarte.descartarCarta(cartaInicial);
        jugadorActual = jugadores.get(0);
    }




    private void crearMazo(Mazo<Carta> mazo) {
        // Crear todas las cartas y añadirlas al mazo
        ArrayList<Carta> cartas = new ArrayList<>();
        String[] colores = {"Rojo", "Verde", "Azul", "Amarillo"};

        // Crear cartas normales
        for (String color : colores) {
            for (int j = 0; j < 10; j++) {
                cartas.add(new Carta(color, j));
            }
        }

        // Crear cartas especiales con color asociado
        for (String color : colores) {
            // Excluir "Cambio de Color" de esta sección
            if (!color.equals("Cambio de Color")) {
                cartas.add(new Carta("Roba 2", color));
                cartas.add(new Carta("Revertir", color));
                cartas.add(new Carta("Salto", color));
            }
        }

        // Crear "Cambio de Color" sin color asociado
        cartas.add(new Carta("Cambio de Color"));

        // Mezclar las cartas
        Collections.shuffle(cartas);

        // Añadir las cartas mezcladas al mazo
        for (Carta carta : cartas) {
            mazo.añadirCarta(carta);
        }
    }

    public void iniciarJuego() {
        // Seleccionar una carta aleatoria como carta inicial
        Random rand = new Random();
        mazoDescarte.descartarCarta(cartaInicial);
        jugadorActual = jugadores.get(0);

        do {
            int cartaInicialIndex = rand.nextInt(mazo.size());
            cartaInicial = mazo.getItems().get(cartaInicialIndex);
        } while (cartaInicial.esEspecial());

        // Mostrar el mazo de cada jugador
        System.out.println("Mazos de los jugadores:");
        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + ":");
            Cola<Carta> mazoJugador = jugador.getMano();
            for (Carta carta : mazoJugador.getItems()) {
                System.out.println(carta);
            }
            System.out.println(); // Separador entre mazos de jugadores
        }

        // Mostrar estado actual del juego
        System.out.println("Carta inicial: " + cartaInicial);
        System.out.println("Jugador actual: " + jugadorActual.getNombre());

        // Implementar lógica del juego
        while (!verificarVictoria(jugadorActual)) {
            // Turno del jugador actual
            System.out.println("Turno del jugador: " + jugadorActual.getNombre());
            turnoJugador();
            if (verificarVictoria(jugadorActual)) {
                System.out.println("¡El jugador " + jugadorActual.getNombre() + " ha ganado!");
                break;
            }
            // Pasar al siguiente jugador
            jugadorActual = obtenerSiguienteJugador();
        }
        System.out.println("Fin del juego.");
    }






    public void turnoJugador() {
        // Mostrar estado actual del juego
        System.out.println("Cartas en la mano del jugador:");

        // Mostrar el mazo del jugador actual
        Cola<Carta> mazoJugadorActual = jugadorActual.getMano();
        for (Carta carta : mazoJugadorActual.getItems()) {
            System.out.println(carta);
        }

        // Implementar lógica del turno del jugador actual
        boolean cartaJugada = false;
        while (!cartaJugada) {
            Carta cartaAJugar = jugadorActual.decisionJugarCarta(cartaInicial);
            if (cartaAJugar != null) {
                if (cartaAJugar.esEspecial()) {
                    // Si la carta es especial, ejecutar su efecto
                    ejecutarEfectoCartaEspecial(cartaAJugar);
                    cartaJugada = true; // La carta especial siempre se juega y termina el turno
                } else {
                    // Si la carta es normal, verificar si puede ser jugada
                    if (cartaAJugar.getColor().equals(cartaInicial.getColor()) ||
                            cartaAJugar.getNumero() == cartaInicial.getNumero()) {
                        jugadorActual.jugarCarta(cartaAJugar, mazoDescarte);
                        cartaInicial = cartaAJugar;
                        cartaJugada = true;
                    } else {
                        System.out.println("La carta no es válida. Debes jugar una carta del mismo color o número.");
                    }
                }
            } else {
                // El jugador decide robar una carta
                Carta cartaRobada = mazo.robarCarta();
                System.out.println("El jugador ha robado una carta: " + cartaRobada);
                jugadorActual.getMano().enqueue(cartaRobada);
                cartaJugada = true;
            }
        }

        // Pasar al siguiente jugador
        jugadorActual = obtenerSiguienteJugador();

        // Mostrar el mazo del siguiente jugador
        System.out.println("Cartas en la mano del siguiente jugador (" + jugadorActual.getNombre() + "):");
        Cola<Carta> manoSiguienteJugador = jugadorActual.getMano();
        for (Carta carta : manoSiguienteJugador.getItems()) {
            System.out.println(carta);
        }
    }





    // Método para que el jugador decida si tirar o guardar una carta robada
    private boolean decidirTirarCarta() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Quieres tirar la carta robada? (s/n)");
        String decision = scanner.nextLine().trim().toLowerCase();
        return decision.equals("s");
    }






    public boolean verificarVictoria(Jugador jugador) {
        return jugador.getMano().isEmpty();
    }

    public void ejecutarEfectoCartaEspecial(Carta cartaEspecial) {
        if (cartaEspecial.getTipoEspecial().equals("Cambio de Color")) {
            // Seleccionar un nuevo color para el juego (podría ser aleatorio o elegido por el jugador)
            // Aquí se asume que se elige aleatoriamente
            String[] colores = {"Rojo", "Azul", "Verde", "Amarillo"};
            String nuevoColor = colores[new Random().nextInt(colores.length)];
            System.out.println("Se ha cambiado el color a: " + nuevoColor);
            cartaInicial = new Carta(nuevoColor); // Cambiar el color de la carta inicial
        } else if (cartaEspecial.getTipoEspecial().equals("Roba 2")) {
            // El siguiente jugador debe robar 2 cartas y perder su turno
            Jugador siguienteJugador = obtenerSiguienteJugador();
            for (int i = 0; i < 2; i++) {
                Carta cartaRobada = (Carta) siguienteJugador.robarCarta(mazo);
                System.out.println("El jugador " + siguienteJugador.getNombre() + " ha robado una carta: " + cartaRobada);
            }
            // Pasar al jugador después del siguiente jugador
            jugadorActual = obtenerSiguienteJugador();
            System.out.println("El jugador " + jugadorActual.getNombre() + " pierde su turno.");
        } else if (cartaEspecial.getTipoEspecial().equals("Revertir")) {
            // Invertir el orden de juego
            Collections.reverse(jugadores);
            System.out.println("Se ha revertido el orden de juego.");
        } else if (cartaEspecial.getTipoEspecial().equals("Salto")) {
            // El siguiente jugador pierde su turno
            jugadorActual = obtenerSiguienteJugador();
            System.out.println("El jugador " + jugadorActual.getNombre() + " pierde su turno.");
        }
    }

    private Jugador obtenerSiguienteJugador() {
        int indiceSiguienteJugador = (jugadores.indexOf(jugadorActual) + 1) % jugadores.size();
        return jugadores.get(indiceSiguienteJugador);
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Cuántas personas jugarán? (2-10)");
        int numJugadores = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner

        if (numJugadores >= 2 && numJugadores <= 10) {
            Juego juego = new Juego(numJugadores);

            for (int i = 0; i < numJugadores; i++) {
                System.out.println("Ingrese el nombre del jugador " + (i + 1) + ":");
                String nombre = scanner.nextLine();
                juego.getJugadores().get(i).setNombre(nombre);
            }

            juego.iniciarJuego();
        } else {
            System.out.println("Número de jugadores no válido. Debe ser un número entre 2 y 10.");
        }
    }


    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
}