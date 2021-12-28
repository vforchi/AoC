package me.vforchi.aoc.y2021.day08;

import me.vforchi.aoc.Day;

import java.util.Arrays;
import java.util.List;

public class Day08 extends Day {

    @Override
    public Object partOne() {
        return input.stream()
                .map(l -> l.split(" \\| ")[1])
                .mapToLong(this::count)
                .sum();
    }

    private long count(String line) {
        return Arrays.stream(line.split(" "))
                .filter(d -> List.of(2, 3, 4, 7).contains(d.length()))
                .count();
    }

    @Override
    public Object partTwo() {
        return input.stream()
                .map(DisplayLog::fromText)
                .mapToLong(DisplayLog::output)
                .sum();
    }

}
