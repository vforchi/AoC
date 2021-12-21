package me.vforchi.aoc.y2021.day21;

public class Game1 extends Game {

    private int die = 0;

    public static Game1 start(int player1, int player2) {
        return new Game1(player1, player2, 0, 0, 0);
    }

    public Game1(int player1, int player2, int points1, int points2, int round) {
        super(player1, player2, points1, points2, round, 1000);
    }

    public void play() {
        die += 1;
        roll(die);
        round += 1;
    }

    private void roll(int die) {
        if (isPlayerOneNext()) {
            player1 = move(player1, die);
            points1 = playerOneScores() ? points1 + player1 : points1;
        } else {
            player2 = move(player2, die);
            points2 = playerTwoScores() ? points2 + player2 : points2;
        }
    }

    public int points() {
        if (winner() == 1) {
            return points2 * round;
        } else {
            return points1 * round;
        }
    }
}
