package model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DeckTest {
    private Deck deck;
    private CardRegistry cardRegistry;

    @Before
    public void setUp() {
        cardRegistry = new CardRegistry();
        // Mock setup for card registry
        for (int id = 1; id <= 108; id++) {
            cardRegistry.registerCard(new Card(id, "Color" + id, "Value" + id, "Type" + id));
        }
        deck = new Deck(cardRegistry);
    }

    @Test
    public void testDeckInitializationAndDrawing() {
        // Verify the deck size initially
        assertEquals("Deck should initially contain 108 cards", 108, deck.cards.size());

        // Draw all cards to verify they can be drawn and are distinct
        Set<Integer> drawnCardIds = new HashSet<>();
        for (int i = 0; i < 108; i++) {
            Card card = deck.drawCard().dequeueCard();
            assertNotNull("Card should not be null when drawn", card);
            assertTrue("Card IDs should be unique", drawnCardIds.add(card.getId()));
        }
        assertTrue("All cards should have been drawn", deck.cards.isEmpty());
        assertNull("No more cards should be available to draw", deck.drawCard());
    }
    @Test
    public void testDrawCard() {
        Card drawnCard = deck.drawCard().dequeueCard();
        assertNotNull("Drawn card should not be null", drawnCard);
        assertEquals("Drawn card should have the correct ID", 108, drawnCard.getId());  // Assuming the last card is drawn first
    }

}


