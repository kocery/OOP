package blackjack;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class BlackjackTest {

    @Test
    void testSomeoneWins() {
        String input = "1\n0\n0\n";
        Scanner scanner = new Scanner(input);

        Blackjack game = new Blackjack();
        game.startGame(scanner);

        List<String> gameLog = game.getGameLog();
        assertTrue(gameLog.contains("Вы победили!") || gameLog.contains("Дилер победил!")
            || gameLog.contains("Кажется, это ничья!"));
    }

    @Test
    void testGameEndsAfterOneRound() {
        String input = "0\n0\n";
        Scanner scanner = new Scanner(input);

        Blackjack game = new Blackjack();
        game.startGame(scanner);

        List<String> gameLog = game.getGameLog();
        assertTrue(gameLog.contains("Спасибо за игру!"));
    }

    @Test
    void testInvalidDrawInputHandling() {
        String input = "x\n0\n0\n";
        Scanner scanner = new Scanner(input);

        Blackjack game = new Blackjack();
        game.startGame(scanner);

        List<String> gameLog = game.getGameLog();
        assertTrue(gameLog.contains("Неккоректный ввод, введите 1 или 0."));
    }

    @Test
    void testInvalidGameInputHandling() {
        String input = "0\nx\n0\n";
        Scanner scanner = new Scanner(input);

        Blackjack game = new Blackjack();
        game.startGame(scanner);

        List<String> gameLog = game.getGameLog();
        assertTrue(gameLog.contains("Неккоректный ввод, введите 1 или 0."));
    }
}
