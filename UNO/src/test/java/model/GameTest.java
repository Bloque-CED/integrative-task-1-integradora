package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class GameTest {
    private Game game;
    private CardRegistry cardRegistry;

    @Before
    public void setUp() {
        cardRegistry = new CardRegistry();
        game = new Game(4, cardRegistry); // Supongamos que jugamos con 4 jugadores.
    }

    @Test
    public void gameInitializationTest() {
        assertNotNull("El mazo no debe ser nulo", game.getDeck());
        assertNotNull("La pila de descarte no debe ser nula", game.getDiscardPile());
        assertEquals("Debe haber 4 jugadores", 4, game.getPlayers().size());
    }

    @Test
    public void cardDistributionTest() {
        for (Player player : game.getPlayers()) {
            assertEquals("Cada jugador debe comenzar con 7 cartas", 7, player.getHand().size());
        }
    }

    @Test
    public void initialDiscardTest() {
        Card topCard = game.getDiscardPile().topCard();
        assertNotNull("Debe haber una carta inicial en la pila de descarte", topCard);
    }


}

