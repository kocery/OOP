package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code Blackjack} class manages the flow of a Blackjack game.
 */
public class Blackjack {

    private static final String DEALERWIN = "Дилер победил!";
    private static final String PLAYERWIN = "Вы победили!";
    private static final String DRAW = "Кажется, это ничья!";
    private static final String INCORRECTINPUT = "Неккоректный ввод, введите 1 или 0.";
    private static final String ENDGAME = "Спасибо за игру!";

    private int round = 1;
    private final List<String> gameLog = new ArrayList<>();

    /**
     * Starts the game and manages multiple rounds of Blackjack.
     */
    public void startGame(Scanner scanner) {
        System.out.println("Добро пожаловать в Блекджек!");
        boolean playAgain = true;

        while (playAgain) {
            playRound(scanner);
            round++;

            System.out.println("Хотите сыграть ещё? Введите: да (1) или нет (0)");

            while (true) {
                String action = scanner.nextLine().strip();

                if (action.equals("0")) {
                    playAgain = false;
                    gameLog.add(ENDGAME);
                    System.out.println(ENDGAME);
                    break;
                } else if (action.equals("1")) {
                    break;
                } else {
                    gameLog.add(INCORRECTINPUT);
                    System.out.println(INCORRECTINPUT);
                }
            }
        }
    }

    /**
     * Plays a single round of Blackjack.
     */
    public void playRound(Scanner scanner) {
        System.out.println("Раунд: " + round);

        Deck deck = new Deck();

        Player player = new Player();
        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());

        Dealer dealer = new Dealer();
        dealer.addCard(deck.dealCard());
        dealer.addCard(deck.dealCard());

        player.showHand();
        dealer.showFirstCard();

        while (true) {
            System.out.println("Вы берёте карту (1) или останавливаетесь? (0)");
            String action = scanner.nextLine().strip();

            if (action.equals("1")) {
                player.addCard(deck.dealCard());
                player.showHand();

                if (player.getScore() > 21) {
                    gameLog.add(DEALERWIN);
                    System.out.println(DEALERWIN);
                    return;
                }
            } else if (action.equals("0")) {
                break;
            } else {
                gameLog.add(INCORRECTINPUT);
                System.out.println(INCORRECTINPUT);
            }
        }

        System.out.println("\nХод дилера:\n");
        dealer.showFirstCard();
        while (dealer.getScore() < 17) {
            dealer.addCard(deck.dealCard());
            System.out.println("Дилер берёт карту.");
        }

        dealer.showAllCards();
        if (dealer.getScore() > 21) {
            gameLog.add(PLAYERWIN);
            System.out.println(PLAYERWIN);
        } else {
            if (dealer.getScore() > player.getScore()) {
                gameLog.add(DEALERWIN);
                System.out.println(DEALERWIN);
            } else if (dealer.getScore() < player.getScore()) {
                gameLog.add(PLAYERWIN);
                System.out.println(PLAYERWIN);
            } else {
                gameLog.add(DRAW);
                System.out.println(DRAW);
            }
        }
    }

    /**
     * Returns the game log.
     *
     * @return List of game events.
     */
    public List<String> getGameLog() {
        return gameLog;
    }
}
