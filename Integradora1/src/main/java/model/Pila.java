package model;

import java.util.ArrayList;

public class Pila<T> {
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

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T datoEliminado = cima.dato;
        cima = cima.siguiente;
        tamano--;
        return datoEliminado;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return cima.dato;
    }

    public boolean isEmpty() {
        return cima == null;
    }

    public int size() {
        return tamano;
    }

    public ArrayList<T> getItems() {
        ArrayList<T> items = new ArrayList<>();
        Nodo<T> actual = cima;
        while (actual != null) {
            items.add(actual.dato);
            actual = actual.siguiente;
        }
        return items;
    }
}

