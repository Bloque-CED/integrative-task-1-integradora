package model;
import java.util.ArrayList;
import java.util.List;

// Player.java
public class Player {
    private List<Integer> hand = new ArrayList<>();
    private String name;
    private CardRegistry cardRegistry;

    public Player(String name, CardRegistry cardRegistry) {
        this.name = name;
        this.cardRegistry = cardRegistry;
    }

    public void drawCard(Card card) {
        if (card != null) {
            hand.add(card.getId());
        }
    }

    public boolean playCard(int cardId, DiscardPile discardPile) {
        if (hand.contains(cardId)) {
            hand.remove(Integer.valueOf(cardId));
            discardPile.addCard(cardId);
            return true;
        }
        return false;
    }

    public List<Card> getHand() {
        List<Card> cards = new ArrayList<>();
        for (Integer id : hand) {
            cards.add(cardRegistry.getCard(id));
        }
        return cards;
    }

    public String getName() {
        return name;
    }

    public void setHand(List<Integer> hand) {
        this.hand = hand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardRegistry getCardRegistry() {
        return cardRegistry;
    }

    public void setCardRegistry(CardRegistry cardRegistry) {
        this.cardRegistry = cardRegistry;
    }
}

