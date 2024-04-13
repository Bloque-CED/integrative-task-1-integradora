package model;

public class CardQueue {
    private Queue<Integer> cardQueue = new Queue<>();
    private CardRegistry cardRegistry;

    public CardQueue(CardRegistry cardRegistry) {
        this.cardRegistry = cardRegistry;
    }

    public void enqueueCard(int cardId) {
        cardQueue.enqueue(cardId);
    }

    public Card dequeueCard() {
        int cardId = cardQueue.dequeue();
        return cardRegistry.getCard(cardId);
    }

    public boolean isEmpty() {
        return cardQueue.isEmpty();
    }

    public void refillFromPile(Stack<Integer> pile) {
        while (!pile.isEmpty()) {
            enqueueCard(pile.pop());
        }
    }
}
