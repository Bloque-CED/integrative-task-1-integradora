package model;

import java.util.Collections;
import java.util.List;

public class Deck {
    public ShufflableStack<Card> cards = new ShufflableStack<>();
    PriorityQueue<Card> specialCards = new PriorityQueue<>();
    private CardRegistry cardRegistry;

    public Deck(CardRegistry cardRegistry) {
        this.cardRegistry = cardRegistry;
        this.cards = new ShufflableStack<>();
        this.specialCards = new PriorityQueue<>();
        initializeDeck();
    }


    private void initializeDeck() {
        for (int id = 1; id <= 108; id++) {
            Card card = new Card(id, determineColor(id), determineValue(id), determineType(id));
            cards.push(card);
            cardRegistry.registerCard(card);
            if (card.getType().equals("Special")) {
                specialCards.enqueue(card);
            }
        }
        cards.shuffle();
    }


    private int registerCard(int id, String color, String value, String type) {
        Card card = new Card(id, color, value, type);
        cardRegistry.registerCard(card);
        return id;
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.pop();
        }
        return null;
    }

    public void shuffle() {
        cards.shuffle();
    }

    public int getSize() {
        return cards.size();
    }


    private String determineColor(int id) {
        if (id >= 1 && id <= 27) return "Red";
        if (id >= 28 && id <= 54) return "Green";
        if (id >= 55 && id <= 81) return "Blue";
        if (id >= 82 && id <= 108) return "Yellow";
        return "None";
    }


    private String determineValue(int id) {
        int baseId = (id - 1) % 27;
        if (baseId == 0) return "0";
        int num = (baseId - 1) % 13 + 1;
        if (num > 9) return "Special";
        return String.valueOf(num);
    }


    private String determineType(int id) {
        int baseId = (id - 1) % 27;
        if (baseId >= 19) return "Special";
        return "Normal";
    }

    public PriorityQueue<Card> getSpecialCards() {
        return specialCards;
    }
}

