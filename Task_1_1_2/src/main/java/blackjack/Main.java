package blackjack;

import java.util.Scanner;

/**
 * The {@code Main} class that starts Blackjack Game.
 */
public class Main {

    /**
     * The {@code main} method that starts Blackjack Game.
     */
    public static void main(String[] args) {
        Blackjack game = new Blackjack();
        Scanner scanner = new Scanner(System.in);
        game.startGame(scanner);
    }
}
