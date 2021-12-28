package me.vforchi.aoc.y2021.day21;

import lombok.EqualsAndHashCode;

import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
public class Game2 extends Game {

    public Game2(int player1, int player2, int points1, int points2, int round) {
        super(player1, player2, points1, points2, round, 21);
    }

    public static Game2 start(int player1, int player2) {
        return new Game2(player1, player2, 0, 0, 0);
    }

    public Stream<Game2> play() {
        return Stream.of(
                copy().roll(1),
                copy().roll(2),
                copy().roll(3)
        );
    }

    public Game2 copy() {
        return new Game2(player1, player2, points1, points2, round);
    }

    private Game2 roll(int die) {
        if (isPlayerOneNext()) {
            int newPlayer1 = move(player1, die);
            int newPoints1 = playerOneScores() ? points1 + newPlayer1 : points1;
            return new Game2(newPlayer1, player2, newPoints1, points2, round + 1);
        } else {
            int newPlayer2 = move(player2, die);
            int newPoints2 = playerTwoScores() ? points2 + newPlayer2 : points2;
            return new Game2(player1, newPlayer2, points1, newPoints2, round + 1);
        }
    }

}