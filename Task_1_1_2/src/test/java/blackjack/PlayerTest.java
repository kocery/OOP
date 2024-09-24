package blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PlayerTest {

    @Test
    void testAddCardIncreasesHandSize() {
        Player player = new Player();
        Card card = new Card("Пики", "7");
        player.addCard(card);
        assertEquals(1, player.getHand().size());
    }

    @Test
    void testCalcHand() {
        Player player = new Player();
        player.addCard(new Card("Червы", "10"));
        player.addCard(new Card("Пики", "Туз"));
        assertEquals("10 Червы, Туз Пики", player.calcHand());
    }

    @Test
    void testGetScoreWithoutAceAdjustment() {
        Player player = new Player();
        player.addCard(new Card("Червы", "9"));
        player.addCard(new Card("Бубы", "5"));
        assertEquals(14, player.getScore());
    }

    @Test
    void testGetScoreWithAceAdjustment() {
        Player player = new Player();
        player.addCard(new Card("Трефы", "Туз"));
        player.addCard(new Card("Пики", "9"));
        player.addCard(new Card("Бубы", "5"));
        assertEquals(15, player.getScore());
    }

    @Test
    void testBustsOver21() {
        Player player = new Player();
        player.addCard(new Card("Трефы", "10"));
        player.addCard(new Card("Пики", "9"));
        player.addCard(new Card("Бубы", "5"));
        assertTrue(player.getScore() > 21);
    }
}
