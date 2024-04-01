package model;

public class Pila<T> implements IPila<T> {
    private Nodo<T> cima;
    private int tamano;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    public Pila() {
        this.cima = null;
        this.tamano = 0;
    }

    @Override
    public void push(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        if (cima == null) {
            cima = nuevoNodo;
        } else {
            nuevoNodo.siguiente = cima;
            cima = nuevoNodo;
        }
        tamano++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T datoEliminado = cima.dato;
        cima = cima.siguiente;
        tamano--;
        return datoEliminado;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return cima.dato;
    }

    @Override
    public boolean isEmpty() {
        return cima == null;
    }

    @Override
    public int size() {
        return tamano;
    }
}
