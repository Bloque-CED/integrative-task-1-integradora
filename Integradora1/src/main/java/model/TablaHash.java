package model;

public class TablaHash<K, V> implements ITablaHash<K, V> {
    private static class Entrada<K, V> {
        K clave;
        V valor;
        Entrada<K, V> siguiente;

        Entrada(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private static final int CAPACIDAD_INICIAL = 16;
    private Entrada<K, V>[] tabla;
    private int tamano;

    @SuppressWarnings("unchecked")
    public TablaHash() {
        tabla = (Entrada<K, V>[]) new Entrada[CAPACIDAD_INICIAL];
        tamano = 0;
    }

    private int hash(K clave) {
        return Math.abs(clave.hashCode()) % tabla.length;
    }

    @Override
    public void put(K clave, V valor) {
        int indice = hash(clave);
        Entrada<K, V> entrada = new Entrada<>(clave, valor);
        if (tabla[indice] == null) {
            tabla[indice] = entrada;
        } else {
            Entrada<K, V> actual = tabla[indice];
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = entrada;
        }
        tamano++;
    }

    @Override
    public V get(K clave) {
        int indice = hash(clave);
        Entrada<K, V> actual = tabla[indice];
        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    @Override
    public boolean containsKey(K clave) {
        return get(clave) != null;
    }

    @Override
    public boolean isEmpty() {
        return tamano == 0;
    }

    @Override
    public int size() {
        return tamano;
    }
}