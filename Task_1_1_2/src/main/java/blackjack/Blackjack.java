package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code Blackjack} class manages the flow of a Blackjack game.
 */
public class Blackjack {

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
                    gameLog.add("Спасибо за игру!");
                    System.out.println("Спасибо за игру!");
                    break;
                } else if (action.equals("1")) {
                    break;
                } else {
                    gameLog.add("Неккоректный ввод, введите 1 или 0.");
                    System.out.println("Неккоректный ввод, введите 1 или 0.");
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
                    gameLog.add("Дилер победил!");
                    System.out.println("Дилер победил!");
                    return;
                }
            } else if (action.equals("0")) {
                break;
            } else {
                gameLog.add("Неккоректный ввод, введите 1 или 0.");
                System.out.println("Неккоректный ввод, введите 1 или 0.");
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
            gameLog.add("Дилер проиграл! На этот раз вы победили!");
            System.out.println("Дилер проиграл! На этот раз вы победили!");
        } else {
            if (dealer.getScore() > player.getScore()) {
                gameLog.add("Дилер победил!");
                System.out.println("Дилер победил!");
            } else if (dealer.getScore() < player.getScore()) {
                gameLog.add("Вы победили!");
                System.out.println("Вы победили!");
            } else {
                gameLog.add("Кажется, это ничья!");
                System.out.println("Кажется, это ничья!");
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
