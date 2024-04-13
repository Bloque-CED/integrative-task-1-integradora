package model;

import java.util.Collections;
import java.util.List;

public class Deck {
    ShufflableStack<Integer> cards = new ShufflableStack<>();
    private CardRegistry cardRegistry;

    public Deck(CardRegistry cardRegistry) {
        this.cardRegistry = cardRegistry;
        this.cards = new ShufflableStack<>();
        initializeDeck();
    }

    private void initializeDeck() {
        // Suppose card IDs range from 1 to 108
        for (int id = 1; id <= 108; id++) {
            cards.push(id);
            cardRegistry.registerCard(new Card(id, determineColor(id), determineValue(id), determineType(id)));
        }
        cards.shuffle();
    }

    private int registerCard(int id, String color, String value, String type) {
        Card card = new Card(id, color, value, type);
        cardRegistry.registerCard(card);
        return id;
    }

    public CardQueue drawCard() {
        if (!cards.isEmpty()) {
            int cardId = cards.pop();
            CardQueue cardQueue = new CardQueue(cardRegistry);
            cardQueue.enqueueCard(cardId);
            return cardQueue;
        }
        return null;
    }



    public void shuffle() {
        Collections.shuffle((List<?>) cards);//Shuffle no sirve para Stack, castea y vuelve un arraylist.
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
}

