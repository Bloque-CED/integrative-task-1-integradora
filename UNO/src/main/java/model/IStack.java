package model;

import java.util.EmptyStackException;

public interface IStack<T> {

    void push(T element);


    T pop() throws EmptyStackException;


    T peek() throws EmptyStackException;

    // Devuelve true si la pila está vacía, false en caso contrario
    boolean isEmpty();

    // Devuelve el tamaño actual de la pila
    int size();

    // Elimina todos los elementos de la pila
    void clear();
}