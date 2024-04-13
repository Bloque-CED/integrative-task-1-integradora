package model;

import java.util.*;

public class Juego {
    private GameStack<Integer> mazo;
    private GameStack<Integer> descarte;
    private GameQueue<Jugador> jugadores;
    private Map<Integer, Carta> mapaCartas;  // Tabla hash que almacena cartas por ID
    private Scanner scanner;
    private boolean juegoEnCurso;

    public Juego(List<Jugador> listaJugadores) {
        mazo = new GameStack<>();
        descarte = new GameStack<>();
        jugadores = new GameQueue<>();
        mapaCartas = new CardHashMap<>();
        scanner = new Scanner(System.in);
        listaJugadores.forEach(jugadores::enqueue);
        inicializarCartas();
        repartirCartas();
        prepararDescarte();
        juegoEnCurso = true;
    }

    private void inicializarCartas() {
        String[] colores = {"Rojo", "Verde", "Azul", "Amarillo"};
        String[] valores = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Salto", "Reversa", "Roba2"};
        int id = 0;
        for (String color : colores) {
            for (String valor : valores) {
                Carta nuevaCarta = new Carta(color, valor, id);
                mazo.push(id);
                mapaCartas.put(id++, nuevaCarta);
                if (!valor.equals("0")) {
                    nuevaCarta = new Carta(color, valor, id);
                    mazo.push(id);
                    mapaCartas.put(id++, nuevaCarta);
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            Carta nuevaCarta = new Carta("Negro", "CambioColor", id);
            mazo.push(id);
            mapaCartas.put(id++, nuevaCarta);
            nuevaCarta = new Carta("Negro", "Roba4", id);
            mazo.push(id);
            mapaCartas.put(id++, nuevaCarta);
        }
        Collections.shuffle(mazo.getElements());  // Mezclar el mazo
    }

    private void repartirCartas() {
        for (int i = 0; i < 7; i++) {
            for (Jugador jugador : jugadores) {
                if (!mazo.isEmpty()) {
                    int cartaId = mazo.pop();
                    if (mapaCartas.containsKey(cartaId)) {  // Verifica que el ID está en el mapa
                        jugador.recibirCarta(cartaId);
                    } else {
                        System.out.println("ID de carta inválido: " + cartaId);
                    }
                }
            }
        }
    }
    private void prepararDescarte() {
        if (!mazo.isEmpty()) {
            descarte.push(mazo.pop());  // Asegurarse de que el descarte no esté vacío al iniciar
        }
    }

    void jugarTurno(Jugador jugador) {
        Integer cartaSuperiorId = descarte.peek();
        Carta cartaSuperior = mapaCartas.get(cartaSuperiorId);
        Integer cartaJugadaId = jugador.jugarCarta(cartaSuperior);
        Carta cartaJugada = mapaCartas.get(cartaJugadaId);

        if (cartaJugada != null) {
            descarte.push(cartaJugadaId);
            System.out.println(jugador.getNombre() + " juega " + cartaJugada);
            aplicarEfectoCarta(cartaJugada.getId(), jugador);
        } else {
            System.out.println(jugador.getNombre() + " no tiene jugadas válidas y roba una carta.");
            Integer robadaId = mazo.pop();
            Carta robada = mapaCartas.get(robadaId);
            jugador.recibirCarta(robadaId);
            if (robada.coincide(cartaSuperior)) {
                descarte.push(robadaId);
                System.out.println(jugador.getNombre() + " juega la carta robada " + robada);
            }
        }

        if (jugador.cantidadCartas() == 0) {
            juegoEnCurso = false;
            System.out.println(jugador.getNombre() + " ha ganado el juego!");
        }
    }

    void aplicarEfectoCarta(Integer cartaId, Jugador jugadorActual) {
        Carta carta = mapaCartas.get(cartaId);
        switch (carta.getValor()) {
            case "Salto":
                jugadores.dequeue(); // El siguiente jugador pierde su turno
                break;
            case "Reversa":
                reversePlayerOrder();
                break;
            case "Roba2":
                Jugador siguiente = jugadores.dequeue();
                siguiente.recibirCarta(mazo.pop());
                siguiente.recibirCarta(mazo.pop());
                jugadores.enqueue(siguiente); // El jugador continúa en juego
                break;
            case "CambioColor":
                String nuevoColor = pedirColor(); // Esta función debería gestionar la interacción del usuario para elegir un color
                descarte.push(mazo.pop()); // Asume que el jugador debe poner una carta después de cambiar el color
                break;
            case "Roba4":
                String colorElegido = pedirColor();
                Jugador proximo = jugadores.dequeue();
                for (int i = 0; i < 4; i++) {
                    proximo.recibirCarta(mazo.pop());
                }
                jugadores.enqueue(proximo); // El jugador continúa en juego
                descarte.push(mazo.pop()); // Asume que el jugador debe poner una carta después de cambiar el color
                break;
            default:
                // No special action needed
                break;
        }
    }

    private String pedirColor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Elija un color (Rojo, Verde, Azul, Amarillo): ");
        String color = scanner.nextLine().trim();
        while (!color.equalsIgnoreCase("Rojo") && !color.equalsIgnoreCase("Verde") &&
                !color.equalsIgnoreCase("Azul") && !color.equalsIgnoreCase("Amarillo")) {
            System.out.println("Color inválido. Por favor elija entre Rojo, Verde, Azul o Amarillo.");
            color = scanner.nextLine().trim();
        }
        return color;
    }

    private void reversePlayerOrder() {
        List<Jugador> reversedList = new ArrayList<>();
        while (!jugadores.isEmpty()) {
            reversedList.add(jugadores.dequeue());
        }
        Collections.reverse(reversedList);
        for (Jugador jugador : reversedList) {
            jugadores.enqueue(jugador);
        }
    }

    public GameStack<Integer> getMazo() {
        return mazo;
    }

    public void setMazo(GameStack<Integer> mazo) {
        this.mazo = mazo;
    }

    public GameStack<Integer> getDescarte() {
        return descarte;
    }

    public void setDescarte(GameStack<Integer> descarte) {
        this.descarte = descarte;
    }

    public GameQueue<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(GameQueue<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Map<Integer, Carta> getMapaCartas() {
        return mapaCartas;
    }

    public void setMapaCartas(Map<Integer, Carta> mapaCartas) {
        this.mapaCartas = mapaCartas;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean isJuegoEnCurso() {
        return juegoEnCurso;
    }

    public void setJuegoEnCurso(boolean juegoEnCurso) {
        this.juegoEnCurso = juegoEnCurso;
    }
}
