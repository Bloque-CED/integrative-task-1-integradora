package model;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private List<Integer> mano;
    private Map<Integer, Carta> referenciaCartas;

    public Jugador(String nombre, Map<Integer, Carta> referenciaCartas) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.referenciaCartas = referenciaCartas;
    }

    public String getNombre() {
        return nombre;
    }

    public void recibirCarta(int cartaId) {
        mano.add(cartaId);
    }

    public int cantidadCartas() {
        return mano.size();
    }

    public Integer jugarCarta(Carta cartaSuperior) {
        if (cartaSuperior == null) {
            throw new IllegalArgumentException("La carta superior no puede ser null.");
        }
        for (int i = 0; i < mano.size(); i++) {
            Carta miCarta = referenciaCartas.get(mano.get(i));
            if (miCarta != null && (miCarta.getColor().equals(cartaSuperior.getColor()) ||
                    miCarta.getValor().equals(cartaSuperior.getValor()) ||
                    miCarta.getColor().equals("Negro"))) {
                return mano.remove(i);
            }
        }
        return null;
    }

    public void mostrarMano() {

        System.out.println(nombre + " tiene las siguientes cartas:");
        for (int cartaId : mano) {
            Carta carta = referenciaCartas.get(cartaId);
            System.out.println("Carta: " + carta);
        }
    }
}
