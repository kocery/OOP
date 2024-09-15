package blackjack;

import java.util.Scanner;

/**
 * The {@code Blackjack} class manages the flow of a Blackjack game.
 */
public class Blackjack {

    private int round = 1;

    /**
     * Starts and manages a single round of Blackjack.
     */
    public void playRound() {
        System.out.println("Раунд: " + round);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в Блекджек!");
        System.out.println("Дилер раздал карты");

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
            System.out.println("Вы берёте карту (0) или останавливаетесь? (1)");
            String action = scanner.nextLine().strip();

            if (action.equalsIgnoreCase("0")) {
                player.addCard(deck.dealCard());
                player.showHand();

                if (player.getScore() > 21) {
                    System.out.println("Вы проиграли! Победа дилера.");
                    playNext();
                }
            } else if (action.equalsIgnoreCase("1")) {
                break;
            } else {
                System.out.println("Неккоректный ввод, введите 0 или 1.");
            }
        }

        System.out.println("\nХод дилера:\n");
        dealer.showFirstCard();
        while (dealer.getScore() < 17) {
            dealer.addCard(deck.dealCard());
            System.out.println("Дилер берёт карту.");
            dealer.showAllCards();
        }

        if (dealer.getScore() > 21) {
            System.out.println("Дилер проиграл! На этот раз вы победили!");
        } else {
            dealer.showAllCards();
            if (dealer.getScore() > player.getScore()) {
                System.out.println("Дилер победил!");
            } else if (dealer.getScore() < player.getScore()) {
                System.out.println("Вы победили!");
            } else {
                System.out.println("Кажется, это ничья!");
            }
        }

        playNext();
    }


    /**
     * Asks the player whether they want to play another round.
     */
    private void playNext() {
        round += 1;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Хотите сыграть ещё? Введите: да или нет");
            String action = scanner.nextLine().strip();

            if (action.equalsIgnoreCase("да")) {
                playRound();
                break;
            } else if (action.equalsIgnoreCase("нет")) {
                break;
            } else {
                System.out.println("Неккоректный ввод, введите да или нет");
            }
        }
    }
}
