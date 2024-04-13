package model;

import exception.NodeNotFoundException;

public class HashTable<K, V> implements IHashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 10;
    private LinkedListStructure<Entry<K, V>>[] table;
    private int size;

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int capacity) {
        table = new LinkedListStructure[capacity];
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        int hash = hash(key);
        if (table[hash] == null)
            table[hash] = new LinkedListStructure<>();

        // Buscamos la clave en la lista correspondiente
        try {
            LinkedListStructure<Entry<K, V>>.Node<Entry<K, V>> foundNode = table[hash].searchNode(entry -> entry.getKey().equals(key));
            foundNode.getData().setValue(value);
        } catch (NodeNotFoundException e) {
            // Si la clave no existe, la agregamos
            table[hash].addNode(new Entry<>(key, value));
            size++;
        }
    }




    @Override
    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        int hash = hash(key);
        if (table[hash] == null)
            return null;

        // Buscamos la clave en la lista correspondiente
        try {
            LinkedListStructure<Entry<K, V>>.Node<Entry<K, V>> foundNode = table[hash].searchNode(entry -> entry.getKey().equals(key));
            return foundNode.getData().getValue();
        } catch (NodeNotFoundException e) {
            return null; // Clave no encontrada
        }
    }

    @Override
    public void remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        int hash = hash(key);
        if (table[hash] == null)
            return;

        // Buscamos la clave en la lista correspondiente y la eliminamos si existe
        table[hash].removeNode(entry -> entry.key.equals(key));
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}