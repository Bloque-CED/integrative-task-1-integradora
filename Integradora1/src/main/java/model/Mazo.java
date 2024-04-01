package model;

public class Mazo<T> {
    private Pila<T> mazo;
    public Pila<T> mazoDescarte;

    public Mazo(Pila<T> mazo, Pila<T> mazoDescarte) {
        this.mazo = mazo;
        this.mazoDescarte = mazoDescarte;
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
    }
}
