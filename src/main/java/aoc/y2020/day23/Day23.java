package aoc.y2020.day23;

import aoc.Day;

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
        return one.next.value.longValue() * one.next.next.value.longValue();
    }

    @Override
    public void setup(String path) throws Exception {
    }

    private void getStartingCups(int size) {
        var firstValues = Arrays.stream(first10.split(""))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        cups = new CupsGame(firstValues, size);
    }

}