package model;

// CardRegistry.java
public class CardRegistry {
    private HashTable<Integer, Card> cardMap = new HashTable<>();

    public void registerCard(Card card) {
        cardMap.put(card.getId(), card);
    }

    public Card getCard(int id) {
        return cardMap.get(id);
    }
}

