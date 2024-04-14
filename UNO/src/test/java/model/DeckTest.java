package model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DeckTest {
    private Deck deck;
    private CardRegistry cardRegistry;


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
        setUp();
        // Verify the deck size initially
        assertEquals("Deck should initially contain 108 cards", 108, deck.cards.size());

        // Draw all cards to verify they can be drawn and are distinct
        Set<Integer> drawnCardIds = new HashSet<>();
        for (int i = 0; i < 108; i++) {
            Card card = deck.drawCard();
            assertNotNull("Card should not be null when drawn", card);
            assertTrue("Card IDs should be unique", drawnCardIds.add(card.getId()));
        }
        assertTrue("All cards should have been drawn", deck.cards.isEmpty());
        assertNull("No more cards should be available to draw", deck.drawCard());
    }
    public void setUp2() {
        cardRegistry = new CardRegistry();
        deck = new Deck(cardRegistry);
    }

    @Test
    public void testDrawCardWhenDeckNotEmpty() {
        setUp2();
        // Add some cards to the deck
        Card card1 = new Card(1, "Red", "1", "Normal");
        Card card2 = new Card(2, "Blue", "2", "Special");
        cardRegistry.registerCard(card1);
        cardRegistry.registerCard(card2);
        deck.getCards().push(card1);
        deck.getCards().push(card2);

        // Draw a card
        Card drawnCard = deck.drawCard();

        // Verify the drawn card
        assertEquals(card2, drawnCard);
    }


}


