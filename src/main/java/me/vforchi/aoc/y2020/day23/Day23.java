package me.vforchi.aoc.y2020.day23;

import me.vforchi.aoc.Day;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day23 extends Day {

    String first10 = "538914762";

    CupsGame cups;
    Cup currentCup;

    @Override
    public Object partOne() {
        getStartingCups(9);
        IntStream.range(0, 100).forEach(i -> cups.nextRound());
        return cups.toString();
    }

    @Override
    public Object partTwo() {
        getStartingCups(1000000);
        IntStream.range(0, 10000000).forEach(i -> cups.nextRound());
        var one = cups.getCup(1);
        return one.next.getLabel() * one.next.next.getLabel();
    }

    private void getStartingCups(int size) {
        var firstValues = Arrays.stream(first10.split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        cups = new CupsGame(firstValues, size);
    }

}