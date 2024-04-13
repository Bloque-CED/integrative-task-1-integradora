package model;

// PlayerTest.java
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    private Player player;
    private CardQueue cardQueue;
    private CardRegistry cardRegistry;

    @Before
    public void setUp() {
        cardRegistry = new CardRegistry();
        player = new Player("Test Player", cardRegistry);
        cardQueue = new CardQueue(cardRegistry);
        Card card = new Card(1, "Green", "7", "Normal");
        cardRegistry.registerCard(card);
        cardQueue.enqueueCard(1);
    }

    @Test
    public void testDrawAndPlayCard() {
        player.drawCard(cardQueue);
        assertTrue("El jugador debe poder jugar una carta que tiene en la mano.", player.playCard(1, new DiscardPile(cardRegistry)));
    }
}
