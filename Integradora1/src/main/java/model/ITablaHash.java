package model;

public interface ITablaHash<K, V>{
    void put(K clave, V valor);
    V get(K clave);
    boolean containsKey(K clave);
    boolean isEmpty();
    int size();
}
