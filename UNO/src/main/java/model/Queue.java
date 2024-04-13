package model;


import java.util.NoSuchElementException;

public class Queue<T extends Comparable<T>> implements IQueue<T> {
    private DoublyLinkedList<T> list;

    public Queue() {
        this.list = new DoublyLinkedList<>();
    }

    public void enqueue(T data) {
        list.addNode(data);
    }

    public T dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue from an empty queue");
        }
        T data = list.head.getData();
        list.head = list.head.getNextNode();
        if (list.head != null) {
            list.head.setPrevNode(null);
        } else {
            list.tail = null;
        }
        list.size--;
        return data;
    }

    public T peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek into an empty queue");
        }
        return list.head.getData();
    }

    public boolean isEmpty() {
        return list.size == 0;
    }

    public int size() {
        return list.size;
    }
}
