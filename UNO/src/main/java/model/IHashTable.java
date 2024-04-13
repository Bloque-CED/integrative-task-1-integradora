package model;

public interface IHashTable<K, V> {
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    int size();
}