package blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CardTest {

    @Test
    void testGetValueForNumericCard() {
        Card card = new Card("Червы", "8");
        assertEquals(8, card.getValue());
    }

    @Test
    void testGetValueForFaceCard() {
        Card card = new Card("Пики", "Валет");
        assertEquals(10, card.getValue());
    }

    @Test
    void testGetValueForAceCard() {
        Card card = new Card("Бубы", "Туз");
        assertEquals(11, card.getValue());
    }

    @Test
    void testToString() {
        Card card = new Card("Трефы", "Король");
        assertEquals("Король Трефы", card.toString());
    }

    @Test
    void testWrongInput1() {
        Card card = new Card("Трефыы", "Король");
        assertEquals("Король Трефыы", card.toString());
    }

    @Test
    void testWrongInput2() {
        Card card = new Card("Трефы", "Корольь");
        assertEquals("Корольь Трефы", card.toString());
    }
}
