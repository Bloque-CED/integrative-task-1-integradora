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
        mazosJugadores = new ArrayList<>(); // Inicializar la lista de mazos de jugadores

        System.out.println("Inicializando mazo...");
        crearBaraja(mazo);
        if (mazo == null) {
            System.out.println("¡Error! El mazo es null.");
        } else {
            System.out.println("¡El mazo se ha inicializado correctamente!");
        }

        for (int i = 0; i < numJugadores; i++) {
            Cola<Carta> mazoJugador = new Cola<>();
            Jugador<Carta> jugador = new Jugador<>("Jugador " + (i + 1), mazoJugador);
            jugadores.add(jugador);
            mazosJugadores.add(mazoJugador); // Agregar el mazo del jugador a la lista de mazos de jugadores
        }
        cartaInicial = mazo.robarCarta();
        mazoDescarte.descartarCarta(cartaInicial);
        jugadorActual = jugadores.get(0);
    }




    private void crearBaraja(Mazo<Carta> baraja) { // Modificación aquí
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                baraja.añadirCarta(new Carta("Rojo", j));
                baraja.añadirCarta(new Carta("Verde", j));
                baraja.añadirCarta(new Carta("Azul", j));
                baraja.añadirCarta(new Carta("Amarillo", j));
            }
            baraja.añadirCarta(new Carta("Cambio de Color"));
            baraja.añadirCarta(new Carta("Roba 2"));
            baraja.añadirCarta(new Carta("Revertir"));
            baraja.añadirCarta(new Carta("Salto"));
        }
        baraja.rellenarMazo(); // Usamos el método rellenarMazo definido en la clase Mazo
    }


    public void iniciarJuego() {
        int numJugadores = jugadores.size();
        // Se inicializa el mazo solo una vez
        System.out.println("Inicializando mazo...");
        crearBaraja(mazo);
        if (mazo == null) {
            System.out.println("¡Error! El mazo es null.");
        } else {
            System.out.println("¡El mazo se ha inicializado correctamente!");
        }

        // Se crea el mazo para cada jugador y se le asigna un nombre
        for (int i = 0; i < numJugadores; i++) {
            Cola<Carta> mazoJugador = new Cola<>();
            Jugador<Carta> jugador = new Jugador<>("Jugador " + (i + 1), mazoJugador);
            jugadores.add(jugador);
        }

        // Seleccionar una carta aleatoria como carta inicial
        Random rand = new Random();
        int cartaInicialIndex = rand.nextInt(mazo.size());
        cartaInicial = mazo.getItems().get(cartaInicialIndex);
        mazoDescarte.descartarCarta(cartaInicial);
        jugadorActual = jugadores.get(0);

        // Mostrar el mazo del jugador actual
        System.out.println("Cartas en la mano del jugador actual (" + jugadorActual.getNombre() + "):");
        Cola<Carta> mazoJugadorActual = jugadorActual.getMano();
        for (Carta carta : mazoJugadorActual.getItems()) {
            System.out.println(carta);
        }

        // Mostrar los nombres y mazos de los jugadores
        System.out.println("Jugadores y sus mazos:");
        for (Jugador jugador : jugadores) {
            System.out.println(jugador.getNombre() + ":");
            Cola<Carta> mazoJugador = jugador.getMano();
            for (Carta carta : mazoJugador.getItems()) {
                System.out.println(carta);
            }
        }

        turnoJugador();
    }





    public void turnoJugador() {
        // Mostrar estado actual del juego
        System.out.println("Carta inicial: " + cartaInicial);
        System.out.println("Jugador actual: " + jugadorActual.getNombre());
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
                Carta cartaRobada = mazo.getItems().get(new Random().nextInt(mazo.size()));

                System.out.println("El jugador ha robado una carta: " + cartaRobada);
                if (cartaRobada != null && cartaRobada.getColor() != null && (cartaRobada.getColor().equals(cartaInicial.getColor()) ||
                        cartaRobada.getNumero() == cartaInicial.getNumero())) {
                    jugadorActual.jugarCarta(cartaRobada, mazoDescarte);
                    cartaInicial = cartaRobada;
                    cartaJugada = true;
                } else {
                    // Preguntar al usuario si desea tirar la carta o mantenerla
                    System.out.println("¿Deseas tirar la carta? (s/n)");
                    Scanner scanner = new Scanner(System.in);
                    String decision = scanner.nextLine();
                    if (decision.equalsIgnoreCase("s")) {
                        System.out.println("El jugador ha decidido tirar la carta.");
                        cartaJugada = true;
                    } else {
                        // Agregar la carta al mazo del jugador y pasar al siguiente jugador
                        jugadorActual.getMano().enqueue(cartaRobada);
                        jugadorActual = obtenerSiguienteJugador();
                        cartaJugada = true;
                    }
                }
            }
        }

        // Actualizar jugadorActual al siguiente jugador
        int indiceSiguienteJugador = (jugadores.indexOf(jugadorActual) + 1) % jugadores.size();
        jugadorActual = jugadores.get(indiceSiguienteJugador);

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
            cartaInicial = new Carta(nuevoColor, cartaInicial.getNumero()); // Cambiar el color de la carta inicial
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