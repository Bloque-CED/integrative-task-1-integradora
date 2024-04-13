package model;

import java.util.PriorityQueue;
import java.util.Comparator;

public class TurnPriorityQueue<T> implements Queue<T> {
    private PriorityQueue<T> queue;

    public TurnPriorityQueue(Comparator<T> comparator) {
        queue = new PriorityQueue<>(comparator);
    }

    @Override
    public void enqueue(T item) {
        queue.add(item);
    }

    @Override
    public T dequeue() {
        return queue.poll();
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

}
