package me.vforchi.aoc.y2019.day01;

import me.vforchi.aoc.Day;

public class Day01 extends Day {

    @Override
    public Object partOne() {
        return input.stream()
                .mapToInt(Integer::parseInt)
                .map(Day01::fuel)
                .sum();
    }

    @Override
    public Object partTwo() {
        return input.stream()
                .mapToInt(Integer::parseInt)
                .map(Day01::fuel2)
                .sum();
    }

    private static int fuel(int mass) {
        return Math.max(0, (mass / 3) - 2);
    }

    private static int fuel2(int mass) {
        int fuel = fuel(mass);
        return fuel + ((fuel > 0) ? fuel2(fuel) : 0);
    }

}
