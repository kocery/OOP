package blackjack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckHas52Cards() {
        Deck deck = new Deck();
        assertEquals(52, deck.getCards().size());
    }

    @Test
    void testDealCardRemovesCardFromDeck() {
        Deck deck = new Deck();
        Card dealtCard = deck.dealCard();
        assertNotNull(dealtCard);
        assertEquals(51, deck.getCards().size());
    }

    @Test
    void testShuffleChangesOrder() {
        Deck deck = new Deck();
        String initialOrder = deck.getCards().toString();
        deck.shuffle();
        String shuffledOrder = deck.getCards().toString();
        assertNotEquals(initialOrder, shuffledOrder);
    }
}
