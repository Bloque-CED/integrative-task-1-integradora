package ui;
import model.*;
import java.util.Scanner;
import java.util.List;

public class GameUI {
    private Game game;
    private Scanner scanner;

    public GameUI() {
        scanner = new Scanner(System.in);
        CardRegistry cardRegistry = new CardRegistry(); // Asume que el registro de cartas se inicializa aquí
        game = new Game(4, cardRegistry); // Suponemos 4 jugadores para el ejemplo
    }

    public void start() {
        boolean gameEnded = false; // Agregamos una variable para controlar el estado del juego
        while (!gameEnded) { // Cambiamos la condición a gameEnded
            Player currentPlayer = game.getPlayers().get(game.getCurrentPlayerIndex());
            Card topCard = game.getDiscardPile().topCard();

            System.out.println("\nTurno de: " + currentPlayer.getName());
            System.out.println("Carta en la cima de la pila de descarte: " + topCard);
            displayPlayerHand(currentPlayer);

            System.out.println("Elige una carta para jugar (1-" + currentPlayer.getHand().size() + ") o escribe 'robar' para sacar una carta del mazo:");
            String input = scanner.nextLine().trim();
            gameEnded = processPlayerInput(input, currentPlayer, topCard); // Actualizamos gameEnded con el resultado de processPlayerInput
        }
        scanner.close();
    }

    private void displayPlayerHand(Player currentPlayer) {
        List<Card> hand = currentPlayer.getHand();
        System.out.println("Tus cartas:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).toString());
        }
    }


    private boolean processPlayerInput(String input, Player currentPlayer, Card topCard) {
        if (input.equalsIgnoreCase("robar")) {
            handleDrawCard(currentPlayer, topCard);
        } else {
            try {
                int choice = Integer.parseInt(input) - 1;
                if (choice >= 0 && choice < currentPlayer.getHand().size()) {
                    handlePlayCard(choice, currentPlayer, topCard);
                } else {
                    System.out.println("Número de carta inválido, intenta de nuevo.");
                    game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() - 1); // Mantiene el turno en el jugador actual.
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida, intenta de nuevo.");
                game.setCurrentPlayerIndex(game.getCurrentPlayerIndex() - 1); // Mantiene el turno en el jugador actual.
            }
        }

        return game.isGameOver(); // Devolvemos el estado del juego después de procesar la entrada del jugador
    }


    private void handleDrawCard(Player currentPlayer, Card topCard) {
        Card drawnCard = game.getDeck().drawCard();
        currentPlayer.drawCard(drawnCard);
        System.out.println("Has robado: " + drawnCard);
        if (drawnCard.matches(topCard)) {
            currentPlayer.playCard(drawnCard.getId(), game.getDiscardPile());
            System.out.println("Jugaste la carta robada automáticamente: " + drawnCard);
        } else {
            System.out.println("No puedes jugar la carta robada.");
        }
    }

    private void handlePlayCard(int choice, Player currentPlayer, Card topCard) {
        Card chosenCard = currentPlayer.getHand().get(choice);
        if (chosenCard.matches(topCard)) {
            currentPlayer.playCard(chosenCard.getId(), game.getDiscardPile());
            System.out.println("Has jugado: " + chosenCard);
            if (currentPlayer.getHand().isEmpty()) {
                System.out.println(currentPlayer.getName() + " ha ganado el juego!");
                game.endGame(); // Asume que se ha implementado este método para terminar el juego.
            }
        } else {
            // No juega la carta, dejamos que el jugador decida qué hacer en processPlayerInput()
        }
    }



    public static void main(String[] args) {
        GameUI ui = new GameUI();
        ui.start();
    }
}


