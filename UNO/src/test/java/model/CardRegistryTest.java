package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CardRegistryTest {
    private CardRegistry cardRegistry;

    @Before
    public void setUp() {
        cardRegistry = new CardRegistry();
        cardRegistry.registerCard(new Card(1, "Red", "5", "Normal"));
    }

    @Test
    public void testRegisterAndGetCard() {
        Card card = cardRegistry.getCard(1);
        assertNotNull("Debe recuperar una carta registrada.", card);
        assertEquals("La carta recuperada debe tener el ID correcto.", 1, card.getId());
        assertEquals("La carta recuperada debe tener el color correcto.", "Red", card.getColor());
        assertEquals("La carta recuperada debe tener el valor correcto.", "5", card.getValue());
        assertEquals("La carta recuperada debe tener el tipo correcto.", "Normal", card.getType());
    }
}

