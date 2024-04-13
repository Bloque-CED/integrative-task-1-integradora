package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class JuegoTest {
    private Juego juego;
    private Jugador jugador1;
    private Jugador jugador2;

    @Before
    public void setUp() {
        jugador1 = new Jugador("Jugador 1", new CardHashMap<>());
        jugador2 = new Jugador("Jugador 2", new CardHashMap<>());
        juego = new Juego(List.of(jugador1, jugador2));
    }

    @Test
    public void testDistribucionDeCartas() {
        assertEquals(7, jugador1.cantidadCartas());
        assertEquals(7, jugador2.cantidadCartas());
    }

    @Test
    public void testInicioDelJuego() {
        assertNotNull(juego.getDescarte().peek());
    }

    @Test
    public void testJugarCartaValida() {
        // Setup del test para asegurar condiciones iniciales adecuadas
        if (juego.getDescarte().isEmpty()) {
            if (!juego.getMazo().isEmpty()) {
                juego.getDescarte().push(juego.getMazo().pop());
            } else {
                fail("No hay cartas en el mazo para transferir al descarte.");
            }
        }

        Carta cartaSuperior = juego.getMapaCartas().get(juego.getDescarte().peek());
        if (cartaSuperior == null) {
            fail("No se encontró una carta superior válida en el descarte.");
        }

        Integer cartaJugadaId = jugador1.jugarCarta(cartaSuperior);
        assertNotNull("El jugador debería haber podido jugar una carta, pero no lo hizo.", cartaJugadaId);
    }



    @Test
    public void testJugarCartaInvalida() {
        while (jugador1.cantidadCartas() > 0) {
            jugador1.recibirCarta(juego.getMazo().pop());
        }
        assertTrue(jugador1.cantidadCartas() > 0);
    }

    @Test
    public void testFinDelJuego() {
        while (jugador1.cantidadCartas() > 0) {
            jugador1.recibirCarta(juego.getMazo().pop());
        }
        assertFalse(juego.isJuegoEnCurso());
        assertEquals("Jugador 1", juego.getJugadores().peek().getNombre());
    }

    @Test
    public void testCartaRoba2() {
        Carta cartaRoba2 = juego.getMapaCartas().get(juego.getDescarte().peek());
        jugador1.recibirCarta(cartaRoba2.getId()); // Agregar carta de roba 2 a la mano del jugador
        int cantidadCartasJugador2 = jugador2.cantidadCartas();
        juego.aplicarEfectoCarta(cartaRoba2.getId(), jugador1);
        assertEquals(cantidadCartasJugador2 + 2, jugador2.cantidadCartas());
        assertNotEquals(jugador2, juego.getJugadores().peek());
    }

    @Test
    public void testCartaRevertir() {
        Carta cartaRevertir = juego.getMapaCartas().get(juego.getDescarte().peek());
        jugador1.recibirCarta(cartaRevertir.getId());
        Jugador jugadorInicial = juego.getJugadores().peek();
        juego.aplicarEfectoCarta(cartaRevertir.getId(), jugador1);
        assertNotEquals(jugadorInicial, juego.getJugadores().peek());
    }

    @Test
    public void testCartaSalto() {
        Carta cartaSalto = juego.getMapaCartas().get(juego.getDescarte().peek());
        jugador1.recibirCarta(cartaSalto.getId());
        Jugador jugadorSiguiente = juego.getJugadores().getElements().get(1);
        juego.aplicarEfectoCarta(cartaSalto.getId(), jugador1);
        assertNotEquals(jugadorSiguiente, juego.getJugadores().peek());
    }

}


