package model;

import java.util.Collections;
import java.util.List;

public class Mazo<T extends Carta> {
    private Pila<T> mazo;
    private Pila<T> mazoDescarte;

    public Mazo(Pila<T> mazoPrincipal) {
        this.mazo = mazoPrincipal;
        this.mazoDescarte = new Pila<>();
    }

    public T robarCarta() {
        if (mazo.isEmpty()) {
            rellenarMazo();
        }
        return mazo.pop();
    }

    public void añadirCarta(T carta) {
        mazo.push(carta);
    }


    public void descartarCarta(T carta) {
        mazoDescarte.push(carta);
    }

    public void rellenarMazo() {
        while (!mazoDescarte.isEmpty()) {
            mazo.push(mazoDescarte.pop());
        }
        Collections.shuffle(mazo.getItems());
    }

    public List<T> getItems() {
        return mazo.getItems();
    }

    public int size() {
        return mazo.size();
    }
}
