package blackjack;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The {@code Player} class represents a participant in a Blackjack game.
 */
public class Player {

    protected final List<Card> hand = new ArrayList<>();

    /**
     * Adds a card to the player's hand.
     *
     * @param card the {@code Card} to be added to the player's hand
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Returns a string representation of the player's hand.
     *
     * @return a formatted string of the player's hand
     */
    public String calcHand() {
        return hand.stream()
            .map(card -> card.rank() + " " + card.suit())
            .collect(Collectors.joining(", "));
    }

    /**
     * Displays all cards in the player's hand along with the total score.
     */
    public void showHand() {
        System.out.println("Ваша рука: [" + calcHand() + "]" + " => " + getScore());
    }

    /**
     * Calculates the player's total score based on the cards in their hand.
     *
     * @return total score of a hand.
     */
    public int getScore() {
        int score = hand.stream().mapToInt(Card::getValue).sum();
        long aceNum = hand.stream().filter(card -> card.rank().equals("Туз")).count();

        while (score > 21 && aceNum > 0) {
            score -= 10;
            aceNum--;
        }

        return score;
    }

    /**
     * Return player's hand.
     *
     * @return player's {@code hand}.
     */
    protected List<Card> getHand() {
        return hand;
    }
}
