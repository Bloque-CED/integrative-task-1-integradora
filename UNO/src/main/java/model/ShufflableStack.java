package model;

import java.util.Random;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;


public class ShufflableStack<T> extends Stack<T> {
    private Random rand = new Random();

    public void shuffle() {
        // Temporary List to hold elements for shuffling
        List<T> tempBuffer = new ArrayList<>();
        while (!isEmpty()) {
            tempBuffer.add(pop()); // Remove elements from stack and add to list
        }

        // Shuffle the list using Collections.shuffle for simplicity and effectiveness
        Collections.shuffle(tempBuffer, rand);

        // Push all elements back into the stack
        for (T item : tempBuffer) {
            push(item);
        }
    }
}


