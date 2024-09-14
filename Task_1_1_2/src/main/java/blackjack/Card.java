package blackjack;

/**
 * The {@code Card} class represents a single playing card in a game of Blackjack.
 */
public class Card() {

    private String suit;
    private String rank;
    
    public Card(String suit, String rank) {
        this.suit = suit;
	this.rank = rank;
    }
    /**
     * Returns the numeric value of the card according to Blackjack rules.
     *
     * @return integer value of the card.
     */
    public int getValue() {
        return switch (rank) {
            case "Туз" -> 11;
            case "Валет", "Король", "Дама" -> 10;
            default -> Integer.parseInt(rank);
        };
    }

    /**
     * Returns a string representation of the card.
     *
     * @return string representation of the card.
     */
    @Override
    public String toString() {
        return rank + " " + suit;
    }
}
