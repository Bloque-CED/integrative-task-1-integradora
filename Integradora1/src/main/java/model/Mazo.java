package model;

import java.util.Collections;

public class Mazo<T> {
    private Pila<T> mazo;
    public Pila<T> mazoDescarte;

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

    public void descartarCarta(T carta) {
        mazoDescarte.push(carta);
    }

    private void rellenarMazo() {
        while (!mazoDescarte.isEmpty()) {
            mazo.push(mazoDescarte.pop());
        }
        Collections.shuffle(mazo.getItems());
    }
}
