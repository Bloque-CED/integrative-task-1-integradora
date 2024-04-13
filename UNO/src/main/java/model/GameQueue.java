package model;

import java.util.Iterator;
import java.util.LinkedList;

public class GameQueue<T> implements Queue<T>, Iterable<T> {
    private LinkedList<T> elements = new LinkedList<>();

    @Override
    public void enqueue(T item) {
        elements.addLast(item);
    }

    @Override
    public T dequeue() {
        return elements.pollFirst();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    public LinkedList<T> getElements() {
        return elements;
    }

    public void setElements(LinkedList<T> elements) {
        this.elements = elements;
    }
    public T peek() {
        if (!isEmpty()) {
            return elements.get(elements.size() - 1);
        }
        return null;
    }
}
