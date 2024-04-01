package model;

import java.util.ArrayList;

public class ColaPrioridad<T> implements IColaPrioridad<T> {
    private ArrayList<T> elementos;
    private ArrayList<Integer> prioridades;

    public ColaPrioridad() {
        elementos = new ArrayList<>();
        prioridades = new ArrayList<>();
    }

    @Override
    public void insert(T elemento, int prioridad) {
        elementos.add(elemento);
        prioridades.add(prioridad);
        reordenar();
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            return null;
        }
        T elemento = elementos.remove(0);
        prioridades.remove(0);
        return elemento;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return elementos.get(0);
    }

    @Override
    public boolean isEmpty() {
        return elementos.isEmpty();
    }

    @Override
    public int size() {
        return elementos.size();
    }

    private void reordenar() {
        ArrayList<T> nuevosElementos = new ArrayList<>();
        ArrayList<Integer> nuevasPrioridades = new ArrayList<>();

        while (!elementos.isEmpty()) {
            int indiceMinPrioridad = obtenerIndiceMinPrioridad();
            nuevosElementos.add(elementos.remove(indiceMinPrioridad));
            nuevasPrioridades.add(prioridades.remove(indiceMinPrioridad));
        }

        elementos = nuevosElementos;
        prioridades = nuevasPrioridades;
    }

    private int obtenerIndiceMinPrioridad() {
        int minPrioridad = Integer.MAX_VALUE;
        int indiceMinPrioridad = -1;

        for (int i = 0; i < prioridades.size(); i++) {
            if (prioridades.get(i) < minPrioridad) {
                minPrioridad = prioridades.get(i);
                indiceMinPrioridad = i;
            }
        }

        return indiceMinPrioridad;
    }
}
