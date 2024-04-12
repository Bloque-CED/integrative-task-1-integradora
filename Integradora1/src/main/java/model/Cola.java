package model;

import java.util.ArrayList;

public class Cola<T> implements ICola<T> {
    private Nodo<T> frente;
    private Nodo<T> finalCola;
    private int tamaño;

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    public Cola() {
        this.frente = null;
        this.finalCola = null;
        this.tamaño = 0;
    }

    @Override
    public void enqueue(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        if (isEmpty()) {
            frente = nuevoNodo;
        } else {
            finalCola.siguiente = nuevoNodo;
        }
        finalCola = nuevoNodo;
        tamaño++;
    }

    public void remove(T elemento) {
        if (isEmpty()) {
            return;
        }

        Nodo<T> previo = null;
        Nodo<T> actual = frente;

        while (actual != null && !actual.dato.equals(elemento)) {
            previo = actual;
            actual = actual.siguiente;
        }

        if (actual != null) {
            if (previo != null) {
                previo.siguiente = actual.siguiente;
            } else {
                frente = actual.siguiente;
            }

            if (actual == finalCola) {
                finalCola = previo;
            }

            tamaño--;
        }
    }


    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T datoEliminado = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            finalCola = null;
        }
        tamaño--;
        return datoEliminado;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return frente.dato;
    }

    @Override
    public boolean isEmpty() {
        return frente == null;
    }

    @Override
    public int size() {
        return tamaño;
    }

    public ArrayList<T> getItems() {
        ArrayList<T> items = new ArrayList<>();
        Nodo<T> actual = frente;
        while (actual != null) {
            items.add(actual.dato);
            actual = actual.siguiente;
        }
        return items;
    }
}

