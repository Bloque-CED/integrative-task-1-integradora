package model;

public class ListaEnlazada<T> implements IPila<T>, ICola<T> {
    private Nodo<T> cabeza;
    private int tamano;

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamano = 0;
    }

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
            this.siguiente = null;
        }
    }

    @Override
    public void push(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        nuevoNodo.siguiente = cabeza;
        cabeza = nuevoNodo;
        tamano++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T datoEliminado = cabeza.dato;
        cabeza = cabeza.siguiente;
        tamano--;
        return datoEliminado;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return cabeza.dato;
    }

    @Override
    public boolean isEmpty() {
        return cabeza == null;
    }

    @Override
    public int size() {
        return tamano;
    }

    @Override
    public void enqueue(T elemento) {
        push(elemento);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;
        while (actual.siguiente != null) {
            anterior = actual;
            actual = actual.siguiente;
        }
        if (anterior != null) {
            anterior.siguiente = null;
        } else {
            cabeza = null;
        }
        tamano--;
        return actual.dato;
    }
}
