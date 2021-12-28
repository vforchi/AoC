package me.vforchi.aoc.y2021.day15;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import me.vforchi.aoc.Day;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day15 extends Day {

    private int[][] cave;
    private int size;

    public int getRisk(int r, int c) {
        var orig = cave[r % size][c % size];
        var mod = orig + (r / size + c / size);
        if (mod > 9) {
            mod = mod - 9;
        }
        return mod;
    }

    @Override
    public void setup(String path) {
        super.setup(path);
        cave = input.stream()
                .map(l -> Arrays.stream(l.split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
        size = cave.length;
    }

    @Override
    public Object partOne() {
        return findLowestRisk(size);
    }

    @Override
    public Object partTwo() {
        return findLowestRisk(5 * size);
    }

    private int[][] initDistances(int size) {
        var distances = IntStream.range(0, size)
                .mapToObj(i -> new int[size])
                .peek(a -> Arrays.fill(a, Integer.MAX_VALUE))
                .toArray(int[][]::new);
        distances[0][0] = 0;
        return distances;
    }

    private int findLowestRisk(int size) {
        var distances = initDistances(size);
        var risk = Integer.MAX_VALUE;
        var newRisk = 0;
        while (newRisk != risk) {
            IntStream.range(0, 5).forEach(k ->
                    IntStream.range(0, size).forEach(i ->
                            IntStream.range(0, size).forEach(j ->
                                    findNeighbours(i, j, size)
                                            .forEach(p -> distances[p._1][p._2] = Math.min(distances[p._1][p._2], distances[i][j] + getRisk(p._1, p._2))))));
            risk = newRisk;
            newRisk = distances[distances.length - 1][distances[0].length - 1];
        }
        return newRisk;
    }

    private Stream<Tuple2<Integer, Integer>> findNeighbours(int row, int col, int size) {
        return Stream.of(
                        Tuple.of(row + 1, col),
                        Tuple.of(row, col + 1),
                        Tuple.of(row - 1, col),
                        Tuple.of(row, col - 1))
                .filter(p -> p._1 >= 0 && p._1 < size)
                .filter(p -> p._2 >=0 && p._2 < size)
                .distinct();
    }

}
