package blackjack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

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
