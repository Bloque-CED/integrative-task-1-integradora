package model;


import java.util.LinkedList;

public class GameStack<T> {
    private LinkedList<T> elements;

    public GameStack() {
        elements = new LinkedList<>();
    }

    // Adds an element to the top of the stack
    public void push(T item) {
        elements.addFirst(item);
    }

    // Removes the top element of the stack and returns it
    public T pop() {
        return elements.isEmpty() ? null : elements.removeFirst();
    }

    // Returns the top element of the stack without removing it

    public boolean isEmpty() {
        return elements.isEmpty();
    }
    public T peek() {
        if (!isEmpty()) {
            return elements.get(elements.size() - 1);
        }
        return null;
    }

    public LinkedList<T> getElements() {
        return elements;
    }

    public void setElements(LinkedList<T> elements) {
        this.elements = elements;
    }
}
