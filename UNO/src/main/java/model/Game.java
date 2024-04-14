package model;

import java.util.List;
import java.util.ArrayList;
import model.CardQueue;

public class Game {
    private Deck deck;
    private DiscardPile discardPile;
    private List<Player> players;
    private int currentPlayerIndex;
    private boolean gameEnded;

    public Game(int numPlayers, CardRegistry cardRegistry) {
        this.deck = new Deck(cardRegistry);
        this.discardPile = new DiscardPile(cardRegistry);
        this.players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player("Player " + (i + 1), cardRegistry);
            for (int j = 0; j < 7; j++) {
                Card drawnCard = deck.drawCard();
                if (drawnCard != null) {
                    player.drawCard(drawnCard);
                }
            }
            players.add(player);
        }

        // Colocar la carta inicial en el mazo de descarte
        Card initialCard = deck.drawCard();
        if (initialCard != null) {
            discardPile.addCard(initialCard.getId());
        }

        currentPlayerIndex = 0;
    }



    public void play() {
        boolean gameEnded = false;
        while (!gameEnded) {
            Player currentPlayer = players.get(currentPlayerIndex);
            Card topCard = discardPile.topCard();
            boolean hasPlayed = false;

            // Iterar sobre las cartas en la mano del jugador
            for (Card card : currentPlayer.getHand()) {
                if (matches(topCard, card)) {
                    currentPlayer.playCard(card.getId(), discardPile);
                    System.out.println(currentPlayer.getName() + " plays " + card);
                    hasPlayed = true;
                    break;
                }
            }

            if (!hasPlayed) {
                Card drawnCard = deck.drawCard();
                if (drawnCard != null) {
                    currentPlayer.drawCard(drawnCard);
                    System.out.println(currentPlayer.getName() + " draws a card");
                    if (matches(topCard, drawnCard)) {
                        currentPlayer.playCard(drawnCard.getId(), discardPile);
                        System.out.println(currentPlayer.getName() + " plays " + drawnCard);
                    } else {
                        System.out.println(currentPlayer.getName() + " passes");
                    }
                } else {
                    System.out.println("The deck is empty, cannot draw a card.");
                }
            }

            if (currentPlayer.getHand().isEmpty()) {
                System.out.println(currentPlayer.getName() + " wins!");
                gameEnded = true;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    private boolean matches(Card card1, Card card2) {
        return card1.getColor().equals(card2.getColor()) || card1.getValue().equals(card2.getValue()) || card1.getType().equals(card2.getType());
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public void setDiscardPile(DiscardPile discardPile) {
        this.discardPile = discardPile;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                System.out.println(player.getName() + " has won the game!");
                return true;
            }
        }
        return false;
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void endGame() {
        this.gameEnded = true;
        System.out.println("The game has ended.");
    }
}
