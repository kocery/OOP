package blackjack;

import java.util.Collections;
import java.util.LinkedList;


/**
 * The {@code Deck} class represents a standard deck of 52 playing cards used in a game of
 * Blackjack.
 */
public class Deck {

    private static final String[] SUITS = {"Червы", "Трефы", "Бубы", "Пики"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Дама",
        "Валет", "Король", "Туз"};

    private final LinkedList<Card> cards = new LinkedList<>();

    /**
     * Constructs a standard 52-card deck.
     * <p>
     * This constructor initializes the standard Blackjack deck.
     * </p>
     */
    public Deck() {

        for (String suit : SUITS) {
            for (String rank : RANKS) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    /**
     * Shuffles the deck of cards.
     * <p>
     * The {@link Collections#shuffle(java.util.List)} method is used to randomize the order of the
     * cards in the deck.
     * </p>
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Return all cards from deck.
     *
     * @return {@code cards} from deck.
     */
    protected LinkedList<Card> getCards() {
        return cards;
    }

    /**
     * Deals the top card from the deck.
     * <p>
     * This method removes and returns the first card from the deck.
     * </p>
     *
     * @return new card {@link Card} from deck.
     */
    public Card dealCard() {
        return cards.removeFirst();
    }
}
