package model;

public class DiscardPile {
    private Stack<Integer> discardStack = new Stack<>();
    private CardRegistry cardRegistry;

    public DiscardPile(CardRegistry cardRegistry) {
        this.cardRegistry = cardRegistry;
    }

    public void addCard(int cardId) {
        discardStack.push(cardId);
    }

    public Card topCard() {
        return cardRegistry.getCard(discardStack.peek());
    }

    public Stack<Integer> getDiscardStack() {
        return discardStack;
    }

    public void setDiscardStack(Stack<Integer> discardStack) {
        this.discardStack = discardStack;
    }

    public CardRegistry getCardRegistry() {
        return cardRegistry;
    }

    public void setCardRegistry(CardRegistry cardRegistry) {
        this.cardRegistry = cardRegistry;
    }
}
