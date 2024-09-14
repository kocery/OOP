package blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DealerTest {

   @Test
    void testAddCardIncreasesHandSize() {
        Dealer dealer = new Dealer();
        Card card = new Card("Пики", "7");
        dealer.addCard(card);
        assertEquals(1, dealer.getHand().size());
    }

    @Test
    void testCalcHand() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Червы", "10"));
        dealer.addCard(new Card("Пики", "Туз"));
        assertEquals("10 Червы, Туз Пики", dealer.calcHand());
    }

    @Test
    void testGetScoreWithoutAceAdjustment() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Червы", "9"));
        dealer.addCard(new Card("Бубы", "5"));
        assertEquals(14, dealer.getScore());
    }

    @Test
    void testBustsOver21() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Трефы", "10"));
        dealer.addCard(new Card("Пики", "9"));
        dealer.addCard(new Card("Бубы", "5"));
        assertTrue(dealer.getScore() > 21);
    }

    @Test
    void testShowFirstCard() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Червы", "7"));
        dealer.addCard(new Card("Пики", "Туз"));
        assertEquals("7 Червы", dealer.hand.getFirst().toString());
    }

    @Test
    void testShowAllCards() {
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("Червы", "7"));
        dealer.addCard(new Card("Пики", "Туз"));
        assertEquals("7 Червы, Туз Пики", dealer.calcHand());
    }
}
