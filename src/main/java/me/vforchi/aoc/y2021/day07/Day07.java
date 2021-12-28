package me.vforchi.aoc.y2021.day07;

import me.vforchi.aoc.Day;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toCollection;

public class Day07 extends Day {

    private Deque<Long> crabs;

    @Override
    public void setup(String path) {
        super.setup(path);
        crabs = Arrays.stream(this.input.get(0).split(","))
                .map(Long::parseLong)
                .sorted()
                .collect(toCollection(ArrayDeque::new));
    }

    private long totalFuel(LongUnaryOperator fuelCalculator) {
        return LongStream.rangeClosed(crabs.getFirst(), crabs.getLast())
                .map(point -> crabs.stream()
                        .mapToLong(c -> abs(c - point))
                        .map(fuelCalculator)
                        .sum())
                .min()
                .orElseThrow();
    }

    @Override
    public Object partOne() {
        return totalFuel(f -> f);
    }
    @Override
    public Object partTwo() {
        return totalFuel(f -> f * (f + 1) / 2);
    }

}
