package model;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class QueueTest {
    Queue<Integer> queue;


    void setUp() {
        queue = new Queue<>();
    }

    @Test
    void enqueue() {
        setUp();
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());
        assertEquals(1, queue.peek());
    }

    @Test
    void dequeue() {
        setUp();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.size());
        assertEquals(2, queue.dequeue());
        assertEquals(3, queue.dequeue());

        assertTrue(queue.isEmpty());
        assertThrows(NoSuchElementException.class, () -> queue.dequeue());
    }

    @Test
    void peek() {
        setUp();
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(1, queue.peek());
        queue.dequeue();
        assertEquals(2, queue.peek());
    }

    @Test
    void isEmpty() {
        setUp();
        assertTrue(queue.isEmpty());
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    void size() {
        setUp();
        assertEquals(0, queue.size());
        queue.enqueue(1);
        assertEquals(1, queue.size());
        queue.enqueue(2);
        assertEquals(2, queue.size());
        queue.dequeue();
        assertEquals(1, queue.size());
    }
}