package me.vforchi.aoc.y2015.day20;

import me.vforchi.aoc.Day;

import java.util.stream.IntStream;

public class Day20 extends Day {

    private int target = 29000000;
    private int maxIterations = target / 10;

    @Override
    public Object partOne() {
        var houses = fillPresents1();
        return IntStream.rangeClosed(1, houses.length)
                .filter(i -> houses[i - 1] >= target)
                .findFirst()
                .orElseThrow();
    }

    private int[] fillPresents1() {
        int[] houses = new int[maxIterations];
        IntStream.rangeClosed(1, maxIterations)
                .forEach(elf -> IntStream.rangeClosed(1, maxIterations / elf)
                        .forEach(i -> houses[elf * i - 1] = houses[elf * i - 1] + elf * 10));
        return houses;
    }

    @Override
    public Object partTwo() {
        var houses = fillPresents2();
        return IntStream.rangeClosed(1, houses.length)
                .filter(i -> houses[i - 1] >= target)
                .findFirst()
                .orElseThrow();
    }

    private int[] fillPresents2() {
        int[] houses = new int[maxIterations];
        IntStream.rangeClosed(1, maxIterations)
                .forEach(elf -> IntStream.rangeClosed(1, 50)
                        .filter(i -> elf * i < maxIterations)
                        .forEach(i -> houses[elf * i - 1] = houses[elf * i - 1] + elf * 11));
        return houses;
    }

}
