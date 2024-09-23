package blackjack;

/**
 * The {@code Dealer} class represents the dealer in a Blackjack game. This class extends the
 * {@link Player} class.
 */
public class Dealer extends Player {

    /**
     * Displays the dealer's first card.
     */
    public void showFirstCard() {
        System.out.println("Карты дилера: [" + hand.getFirst() + ", <закрытая карта>" + "]");
    }

    /**
     * Displays all cards in the dealer's hand along with the total score.
     */
    public void showAllCards() {
        System.out.println("Рука диллера: [" + calcHand() + "]" + " => " + getScore());
    }
}
