package model;

import java.util.NoSuchElementException;

public class PriorityQueue<T extends Comparable<T>> implements IPriorityQueue<T> {
    private DoublyLinkedList<T> list;

    public PriorityQueue() {
        this.list = new DoublyLinkedList<>();
    }

    public void enqueue(T data) {
        list.addNodeInOrder(data);
    }


    public T dequeue() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue from an empty priority queue");
        }

        T removedItem = peek();

        if (list.head == list.tail) {
            list.head = null;
            list.tail = null;
        } else {
            list.head = list.head.getNextNode();
            list.head.setPrevNode(null);
        }

        list.size--;
        return removedItem;
    }



    public T peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek into an empty priority queue");
        }
        return list.head.getData();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size;
    }
}
