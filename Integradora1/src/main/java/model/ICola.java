package model;

public interface ICola<T> {
    void enqueue(T elemento);
    T dequeue();
    T peek();
    boolean isEmpty();
    int size();
}
