package me.vforchi.aoc.y2021.day21;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public abstract class Game {
    protected int player1, player2;
    protected int points1, points2;
    protected int round;
    protected int goal;

    protected boolean isPlayerOneNext() {
        return round % 6 < 3;
    }

    protected boolean playerOneScores() {
        return round % 6 == 2;
    }

    protected boolean playerTwoScores() {
        return round % 6 == 5;
    }

    protected int move(int pos, int die) {
        var newPos = pos + die;
        while (newPos > 10) {
            newPos -= 10;
        }
        return newPos;
    }

    public boolean over() {
        return points1 >= goal || points2 >= goal;
    }

    public int winner() {
        if (points1 >= goal) {
            return 1;
        } else if (points2 >= goal) {
            return 2;
        } else {
            throw new RuntimeException();
        }
    }
}