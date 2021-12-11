package me.vforchi.aoc.y2021.day11;

import me.vforchi.aoc.Day;

import java.util.stream.IntStream;

public class Day11 extends Day {
    private final int partOneSteps = 100;
    private Cavern cavern;

    @Override
    public void setup(String path) {
        super.setup(path);
        cavern = Cavern.fromLines(input);
    }

    @Override
    public Object partOne() {
        return IntStream.range(0, partOneSteps)
                .map(i -> cavern.flash())
                .sum();
    }

    @Override
    public Object partTwo() {
        return IntStream.iterate(partOneSteps + 1, i -> i + 1)
                .filter(i -> cavern.flash() == cavern.size * cavern.size)
                .findFirst()
                .orElseThrow();
    }
}
