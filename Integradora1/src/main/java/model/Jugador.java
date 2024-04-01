package model;
import java.util.Scanner;

public class Jugador<T> {
    public String nombre;
    public Cola<T> mano;

    public Jugador(String nombre, Cola<T> mano) {
        this.nombre = nombre;
        this.mano = mano;
    }

    public T robarCarta(Mazo<T> mazo) {
        T carta = mazo.robarCarta();
        mano.enqueue(carta);
        return carta;
    }

    public void jugarCarta(T carta, Mazo<T> mazo) {
        if (carta instanceof Carta) {
            Carta cartaJugada = (Carta) carta;
            if (cartaJugada.getColor().equals(((Carta) mazo.robarCarta()).getColor()) ||
                    cartaJugada.getNumero() == ((Carta) mazo.robarCarta()).getNumero()) {
                mano.dequeue();
                mazo.descartarCarta(carta);
            }
        }
    }

    public boolean tieneCartas() {
        return !mano.isEmpty();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cola<T> getMano() {
        return mano;
    }

    public void setMano(Cola<T> mano) {
        this.mano = mano;
    }

    public Carta decisionJugarCarta(Carta cartaInicial) {
        Scanner scanner = new Scanner(System.in);
        Carta cartaSeleccionada = null;

        System.out.println("Ingrese el índice de la carta que desea jugar (o -1 para robar una carta):");
        int indiceCarta = scanner.nextInt();

        if (indiceCarta == -1) {
            return null; // El jugador decide robar una carta
        }

        if (indiceCarta >= 0 && indiceCarta < mano.size()) {
            cartaSeleccionada = (Carta) mano.getItems().get(indiceCarta);
        } else {
            System.out.println("Índice de carta no válido. Por favor, ingrese un índice válido.");
            return decisionJugarCarta(cartaInicial); // Pedir al usuario que ingrese un índice válido
        }

        // Verificar si la carta seleccionada puede ser jugada
        if (cartaSeleccionada.esEspecial()) {
            // Si la carta seleccionada es especial, verificar si coincide con la carta inicial
            if (cartaSeleccionada.getTipoEspecial().equals("Cambio de Color")) {
                // Permitir jugar la carta especial sin restricciones
                return cartaSeleccionada;
            } else if (cartaSeleccionada.getTipoEspecial().equals("Roba 2")) {
                // Permitir jugar la carta especial si coincide con el color de la carta inicial
                if (cartaSeleccionada.getColor().equals(cartaInicial.getColor())) {
                    return cartaSeleccionada;
                } else {
                    System.out.println("No puedes jugar esta carta en este momento.");
                    return null;
                }
            } else if (cartaSeleccionada.getTipoEspecial().equals("Revertir")) {
                // Permitir jugar la carta especial si coincide con el color de la carta inicial
                if (cartaSeleccionada.getColor().equals(cartaInicial.getColor())) {
                    return cartaSeleccionada;
                } else {
                    System.out.println("No puedes jugar esta carta en este momento.");
                    return null;
                }
            } else if (cartaSeleccionada.getTipoEspecial().equals("Salto")) {
                // Permitir jugar la carta especial si coincide con el color de la carta inicial
                if (cartaSeleccionada.getColor().equals(cartaInicial.getColor())) {
                    return cartaSeleccionada;
                } else {
                    System.out.println("No puedes jugar esta carta en este momento.");
                    return null;
                }
            }
        } else {
            // Si la carta seleccionada es normal, verificar si coincide con la carta inicial
            if (cartaSeleccionada.getColor().equals(cartaInicial.getColor()) ||
                    cartaSeleccionada.getNumero() == cartaInicial.getNumero()) {
                return cartaSeleccionada;
            } else {
                System.out.println("No puedes jugar esta carta en este momento.");
                return null;
            }
        }

        return cartaSeleccionada;
    }
}
