package model;
// CardQueueTest.java
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CardQueueTest {
    private CardQueue cardQueue;
    private CardRegistry cardRegistry;

    @Before
    public void setUp() {
        cardRegistry = new CardRegistry();
        cardQueue = new CardQueue(cardRegistry);
        cardRegistry.registerCard(new Card(1, "Red", "5", "Normal"));
        cardQueue.enqueueCard(1);
    }

    @Test
    public void testDequeueCard() {
        Card card = cardQueue.dequeueCard();
        assertNotNull("Debe desencolar una carta.", card);
        assertEquals("La carta debe ser la que se encoló.", 1, card.getId());
    }
}

