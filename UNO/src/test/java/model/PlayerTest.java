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
    }

    @Test
    public void testDrawCard() {
        Card card = new Card(1, "Green", "7", "Normal");
        cardRegistry.registerCard(card);
        cardQueue.enqueueCard(1);
        player.drawCard(cardQueue.dequeueCard());
        assertTrue("El jugador debería tener la carta en la mano", player.getHand().contains(card));
    }
}
