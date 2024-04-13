package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShufflableStackTest {
    private ShufflableStack<Integer> stack;

    @Before
    public void setUp() {
        stack = new ShufflableStack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
    }

    @Test
    public void testShuffle() {
        // Copying elements manually to another stack for original order preservation
        ShufflableStack<Integer> originalStack = new ShufflableStack<>();
        ShufflableStack<Integer> tempStack = new ShufflableStack<>();

        // Copy stack to a temp stack to reverse it
        while (!stack.isEmpty()) {
            tempStack.push(stack.pop());
        }

        // Now copy it to the original stack and refill the main stack
        while (!tempStack.isEmpty()) {
            Integer item = tempStack.pop();
            originalStack.push(item);
            stack.push(item);
        }

        // Shuffle the main stack
        stack.shuffle();

        // Check if the order has changed
        boolean orderChanged = false;
        while (!stack.isEmpty()) {
            Integer shuffledItem = stack.pop();
            Integer originalItem = originalStack.pop();
            if (!shuffledItem.equals(originalItem)) {
                orderChanged = true;
                break;
            }
        }

        assertTrue("The order of elements should change after shuffling", orderChanged);
    }
}
