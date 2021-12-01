package me.vforchi.aoc.y2021.day01;

import me.vforchi.aoc.Day;

import java.util.function.BiFunction;

public class Day01 extends Day {

    private int[] numbers;

    @Override
    public void setup(String path) {
        super.setup(path);
        numbers = input.stream()
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    @Override
    public Object partOne() {
        return increases(numbers, (numbers, i) -> numbers[i], 1);
    }

    @Override
    public Object partTwo() {
        return increases(numbers, (numbers, i) -> numbers[i] + numbers[i + 1] + numbers[i + 2], 3);
    }

    private int increases(int[] numbers, BiFunction<int[], Integer, Integer> reducer, int size) {
        int increases = 0;
        for (int i = 0; i < (numbers.length - size); i++) {
            if (reducer.apply(numbers, i + 1) > reducer.apply(numbers, i)) {
                increases++;
            }
        }
        return increases;
    }

}
