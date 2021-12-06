package me.vforchi.aoc.y2021.day06;

import me.vforchi.aoc.Day;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day06 extends Day {

    private Map<Integer, Long> fish;

    @Override
    public void setup(String path) {
        super.setup(path);
        fish = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .collect(groupingBy(identity(), counting()));
    }

    @Override
    public Object partOne() {
        return count(spawn(fish, 80));
    }

    @Override
    public Object partTwo() {
        return count(spawn(fish, 256));
    }

   private  Map<Integer, Long> spawn(Map<Integer, Long> fish, int days) {
        return Stream.iterate(fish, this::spawn)
                .skip(days)
                .findFirst()
                .orElseThrow();
    }

    private Map<Integer, Long> spawn(Map<Integer, Long> fish) {
        var newFish = fish.entrySet().stream()
                .filter(e -> e.getKey() != 0)
                .collect(Collectors.toMap(
                        e -> e.getKey() - 1,
                        Map.Entry::getValue));
        var zeroFish = fish.getOrDefault(0, 0L);
        newFish.put(6, newFish.getOrDefault(6, 0L) + zeroFish);
        newFish.put(8, zeroFish);
        return newFish;
    }

    private Long count(Map<Integer, Long> fish) {
        return fish.values().stream()
                .reduce(Long::sum)
                .orElseThrow();
    }

}
