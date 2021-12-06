package me.vforchi.aoc.y2015.day02;

import me.vforchi.aoc.Day;

import java.util.List;

import static java.lang.Integer.parseInt;

public class Day02 extends Day {

    private List<List<Integer>> gifts;

    @Override
    public void setup(String path) {
        super.setup(path);
        gifts = input.stream()
                .map(s -> s.split("x"))
                .map(s -> List.of(parseInt(s[0]), parseInt(s[1]), parseInt(s[2])))
                .toList();
    }

    @Override
    public Object partOne() {
        return gifts.stream()
                .mapToInt(this::area)
                .sum();
    }

    private int area(List<Integer> dim) {
        var sides = List.of(dim.get(0) * dim.get(1), dim.get(0) * dim.get(2), dim.get(1) * dim.get(2));
        return 2 * sides.stream().reduce(Integer::sum).orElseThrow() +
                   sides.stream().min(Integer::compare).orElseThrow();
    }

    @Override
    public Object partTwo() {
        return gifts.stream()
                .mapToInt(this::ribbon)
                .sum();
    }

    private int ribbon(List<Integer> dim) {
        return 2 * dim.stream().sorted().limit(2).reduce(Integer::sum).orElseThrow() +
                   dim.stream().reduce((a, b) -> a * b).orElseThrow();
    }

}
