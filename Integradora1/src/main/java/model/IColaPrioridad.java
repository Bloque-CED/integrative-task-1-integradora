package model;

public interface IColaPrioridad <T>{
    void insert(T elemento, int prioridad);
    T remove();
    T peek();
    boolean isEmpty();
    int size();
}
