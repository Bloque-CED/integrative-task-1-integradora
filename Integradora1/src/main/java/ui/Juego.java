package ui;
import model.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Juego {
    private ArrayList<Cola<Carta>> mazosJugadores;
    private Mazo<Carta> mazo;
    private Mazo<Carta> mazoDescarte;
    private ArrayList<Jugador> jugadores;
    private Carta cartaInicial;
    private Jugador jugadorActual;

    public Juego(int numJugadores) {
        mazosJugadores = new ArrayList<>(); // Cambio en la inicialización
        mazoDescarte = new Mazo<>(new Pila<Carta>());
        jugadores = new ArrayList<>();

        // Mensaje de depuración para verificar la inicialización de mazo
        System.out.println("Inicializando mazo...");
        mazo = crearBaraja(); // Inicializar el mazo
        if (mazo == null) {
            System.out.println("¡Error! El mazo es null.");
        } else {
            System.out.println("¡El mazo se ha inicializado correctamente!");
        }

        for (int i = 0; i < numJugadores; i++) {
            Cola<Carta> mazoJugador = new Cola<>(); // Eliminar el tipo genérico adicional
            mazosJugadores.add(mazoJugador); // Agrega el mazo al arreglo de mazos de jugadores
            jugadores.add(new Jugador("Jugador " + (i + 1), mazoJugador)); // Asigna el mazo al jugador
        }
        cartaInicial = mazo.robarCarta(); // Colocar la carta inicial en el montón de descarte
        mazoDescarte.descartarCarta(cartaInicial);
        jugadorActual = jugadores.get(0); // Empezar con el primer jugador
    }


    private Pila<Carta> crearBaraja() {
        Pila<Carta> baraja = new Pila<>();
        // Agregar cartas a la baraja
        for (int i = 0; i < 4; i++) { // Colores
            for (int j = 0; j < 10; j++) { // Números
                baraja.push(new Carta("Rojo", j));
                baraja.push(new Carta("Verde", j));
                baraja.push(new Carta("Azul", j));
                baraja.push(new Carta("Amarillo", j));
            }
            // Agregar cartas especiales
            baraja.push(new Carta("Cambio de Color"));
            baraja.push(new Carta("Roba 2"));
            baraja.push(new Carta("Revertir"));
            baraja.push(new Carta("Salto"));
        }
        Collections.shuffle(baraja.getItems()); // Mezclar las cartas

        // Mensaje de depuración para verificar si baraja es null
        if (baraja == null) {
            System.out.println("¡Error! La baraja es null.");
        } else {
            System.out.println("¡La baraja se ha creado correctamente!");
        }

        return baraja;
    }

    public void iniciarJuego() {
        for (Jugador jugador : jugadores) {
            for (int i = 0; i < 7; i++) {
                jugador.robarCarta(mazo);
            }
        }
        // Iniciar el turno del primer jugador
        turnoJugador();
    }

    public void turnoJugador() {
        // Mostrar estado actual del juego
        System.out.println("Carta inicial: " + cartaInicial);
        System.out.println("Jugador actual: " + jugadorActual.getNombre());
        System.out.println("Cartas en la mano del jugador:");

        // Mostrar el mazo del jugador actual
        Cola<Carta> mazoJugadorActual = mazosJugadores.get(jugadores.indexOf(jugadorActual));
        for (Carta carta : mazoJugadorActual.getItems()) { // Utilizar el método getCartas()
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
                Carta cartaRobada = (Carta) jugadorActual.robarCarta(mazo);
                System.out.println("El jugador ha robado una carta: " + cartaRobada);
                if (cartaRobada.getColor().equals(cartaInicial.getColor()) ||
                        cartaRobada.getNumero() == cartaInicial.getNumero()) {
                    jugadorActual.jugarCarta(cartaRobada, mazoDescarte);
                    cartaInicial = cartaRobada;
                    cartaJugada = true;
                }
            }
        }

        // Actualizar jugadorActual al siguiente jugador
        int indiceSiguienteJugador = (jugadores.indexOf(jugadorActual) + 1) % jugadores.size();
        jugadorActual = jugadores.get(indiceSiguienteJugador);

        // Verificar si el jugador actual ha ganado
        if (verificarVictoria(jugadorActual)) {
            System.out.println("¡El jugador " + jugadorActual.getNombre() + " ha ganado!");
        } else {
            // Continuar con el siguiente turno
            turnoJugador();
        }
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
        System.out.println("¿Cuántas personas jugarán?");
        int numJugadores = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer del scanner

        if (numJugadores >= 2) { // Modificación aquí
            Juego juego = new Juego(numJugadores);

            for (int i = 0; i < numJugadores; i++) {
                System.out.println("Ingrese el nombre del jugador " + (i + 1) + ":");
                String nombre = scanner.nextLine();
                juego.getJugadores().get(i).setNombre(nombre);
            }

            juego.iniciarJuego();
        } else {
            System.out.println("No hay suficientes jugadores para iniciar el juego.");
        }
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
}