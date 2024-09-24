package blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.LinkedList;
import org.junit.jupiter.api.Test;

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
        LinkedList<Card> initialOrder = new LinkedList<>(deck.getCards());
        deck.shuffle();
        assertNotEquals(initialOrder, deck.getCards());
    }
}
