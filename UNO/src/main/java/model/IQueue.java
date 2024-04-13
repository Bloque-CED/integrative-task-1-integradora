package model;

import java.util.NoSuchElementException;

public interface IQueue<T> {

    void enqueue(T data);


    T dequeue() throws NoSuchElementException;


    T peek() throws NoSuchElementException;

    boolean isEmpty();

    int size();
}

