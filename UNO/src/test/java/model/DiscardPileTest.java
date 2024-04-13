package model;

// DiscardPileTest.java
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DiscardPileTest {
    private DiscardPile discardPile;
    private CardRegistry cardRegistry;

    @Before
    public void setUp() {
        cardRegistry = new CardRegistry();
        discardPile = new DiscardPile(cardRegistry);
        cardRegistry.registerCard(new Card(1, "Blue", "Skip", "Special"));
        discardPile.addCard(1);
    }

    @Test
    public void testTopCard() {
        Card card = discardPile.topCard();
        assertNotNull("Debe haber una carta en el montón de descarte.", card);
        assertEquals("La carta en la cima debe ser la última añadida.", 1, card.getId());
    }
}

