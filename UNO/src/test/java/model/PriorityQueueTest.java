package model;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueTest {
    PriorityQueue<Integer> priorityQueue;


    void setUp() {
        priorityQueue = new PriorityQueue<>();
    }

    @Test
    void enqueue() {
        setUp();
        assertTrue(priorityQueue.isEmpty());
        priorityQueue.enqueue(3);
        priorityQueue.enqueue(1);
        priorityQueue.enqueue(2);
        assertFalse(priorityQueue.isEmpty());
        assertEquals(3, priorityQueue.size());
        assertEquals(1, priorityQueue.peek());
    }

    @Test
    void dequeue() {
        setUp();
        priorityQueue.enqueue(3);
        priorityQueue.enqueue(1);
        priorityQueue.enqueue(2);

        assertEquals(1, priorityQueue.dequeue());
        assertEquals(2, priorityQueue.dequeue());
        assertEquals(3, priorityQueue.dequeue());

        assertTrue(priorityQueue.isEmpty());
        assertThrows(NoSuchElementException.class, () -> priorityQueue.dequeue());
    }

    @Test
    void peek() {
        setUp();
        assertTrue(priorityQueue.isEmpty());
        priorityQueue.enqueue(5);
        priorityQueue.enqueue(2);
        assertEquals(2, priorityQueue.peek());
        priorityQueue.dequeue();
        assertEquals(5, priorityQueue.peek());
    }

    @Test
    void isEmpty() {
        setUp();
        assertTrue(priorityQueue.isEmpty());
        priorityQueue.enqueue(4);
        assertFalse(priorityQueue.isEmpty());
        priorityQueue.dequeue();
        assertTrue(priorityQueue.isEmpty());
    }

    @Test
    void size() {
        setUp();
        assertEquals(0, priorityQueue.size());
        priorityQueue.enqueue(6);
        assertEquals(1, priorityQueue.size());
        priorityQueue.enqueue(7);
        assertEquals(2, priorityQueue.size());
        priorityQueue.dequeue();
        assertEquals(1, priorityQueue.size());
    }
}

