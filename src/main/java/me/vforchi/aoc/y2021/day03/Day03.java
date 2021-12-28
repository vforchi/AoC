package me.vforchi.aoc.y2021.day03;

import me.vforchi.aoc.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day03 extends Day {

    private int size;
    private List<int[]> logs;

    @Override
    public void setup(String path) {
        super.setup(path);
        logs = input.stream()
                .map(l -> l.chars()
                        .map(c -> (c == 48) ? 0 : 1)
                        .toArray())
                .toList();
        size = logs.get(0).length;
    }

    @Override
    public Object partOne() {
       return logs.stream()
               .reduce(Day03::sum)
               .map(sum -> getGammaRate(sum, logs.size()))
               .map(gamma -> gamma * (Math.pow(2, size) - 1 - gamma))
               .map(Double::intValue)
               .orElseThrow();
    }

    private Long getGammaRate(int[] sum, int size) {
        var gammaRate = Arrays.stream(sum)
                .mapToObj(i -> (i > size / 2) ? "1" : "0")
                .collect(Collectors.joining());
        return Long.parseLong(gammaRate, 2);
    }

    private static int[] sum(int[] log1, int[] log2) {
        return IntStream.range(0, log1.length)
                .map(i -> log1[i] + log2[i])
                .toArray();
    }

    @Override
    public Object partTwo() {
        return getRating(this::oxygenCriteria) * getRating(this::co2Criteria);
    }

    private int oxygenCriteria(int sum, int numLogs) {
       return (sum >= (0.5 * numLogs)) ? 1 : 0;
    }

    private int co2Criteria(int sum, int numLogs) {
        return (sum >= (0.5 * numLogs)) ? 0 : 1;
    }

    private Long getRating(BiFunction<Integer, Integer, Integer> criteria) {
        List<int[]> copy = new ArrayList<>(logs);
        int bit = 0;
        while (copy.size() > 1) {
            copy = filterLogs(copy, bit++, criteria);
        }
        return logToDecimal(copy.get(0));
    }

    private List<int[]> filterLogs(List<int[]> copy, int bit, BiFunction<Integer, Integer, Integer> criteria) {
        var common = common(copy, bit, criteria);
        return copy.stream()
                .filter(l -> l[bit] == common)
                .toList();
    }

    private Long logToDecimal(int[] sum) {
        var gammaRate = Arrays.stream(sum)
                .mapToObj(Objects::toString)
                .collect(Collectors.joining());
        return Long.parseLong(gammaRate, 2);
    }

    private int common(List<int[]> logs, int bit, BiFunction<Integer, Integer, Integer> criteria) {
        var sum = logs.stream()
                .mapToInt(l -> l[bit])
                .sum();
        return criteria.apply(sum, logs.size());
    }

}
