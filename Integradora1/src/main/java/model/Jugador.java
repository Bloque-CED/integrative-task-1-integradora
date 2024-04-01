package model;

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
}
