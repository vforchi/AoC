package me.vforchi.aoc.y2015.day18;

import io.vavr.Tuple;
import me.vforchi.aoc.Day;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day18 extends Day {

    private int[][] lights;
    private int size;

    @Override
    public void setup(String path) {
        super.setup(path);
        lights = input.stream()
                .map(l -> Arrays.stream(l.split(""))
                        .mapToInt(c -> c.equals("#") ? 1 : 0)
                        .toArray())
                .toArray(int[][]::new);
        size = lights.length;
    }

    @Override
    public Object partOne() {
        return Stream.iterate(lights, this::step)
                .skip(100)
                .findFirst()
                .map(Day18::countLightsOn)
                .orElseThrow();
    }

    @Override
    public Object partTwo() {
        return Stream.iterate(lights, this::step2)
                .skip(100)
                .findFirst()
                .map(Day18::countLightsOn)
                .orElseThrow();
    }

    private int[][] step(int[][] lights) {
        int[][] newLights = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                long onNeighbours = findOnNeighbours(lights, i, j);
                if (lights[i][j] == 1 && (onNeighbours < 2 || onNeighbours > 3)) {
                    newLights[i][j] = 0;
                } else if (lights[i][j] == 0 && onNeighbours == 3) {
                    newLights[i][j] = 1;
                } else {
                    newLights[i][j] = lights[i][j];
                }
            }
        }
        return newLights;
    }

    private int[][] step2(int[][] lights) {
        var newLights = step(lights);
        newLights[0][0] = 1;
        newLights[size - 1][0] = 1;
        newLights[0][size - 1] = 1;
        newLights[size - 1][size - 1] = 1;
        return newLights;
    }

    private long findOnNeighbours(int[][] lights, int row, int col) {
        return Stream.of(
                        Tuple.of(row + 1, col),
                        Tuple.of(row, col + 1),
                        Tuple.of(row - 1, col),
                        Tuple.of(row, col - 1),
                        Tuple.of(row + 1, col + 1),
                        Tuple.of(row + 1, col - 1),
                        Tuple.of(row - 1, col - 1),
                        Tuple.of(row - 1, col + 1))
                .filter(p -> p._1 >= 0 && p._1 < size)
                .filter(p -> p._2 >=0 && p._2 < size)
                .filter(t -> lights[t._1][t._2] == 1)
                .count();
    }

    private static int countLightsOn(int[][] lights) {
        return Arrays.stream(lights)
                .mapToInt(row -> Arrays.stream(row).sum())
                .sum();
    }

}
