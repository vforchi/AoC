package me.vforchi.aoc.y2021.day09;

import me.vforchi.aoc.Day;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toSet;

public class Day09 extends Day {
    
    private record Pos(int row, int col) {}

    private int[][] tubes;
    private int rows;
    private int columns;

    @Override
    public void setup(String path) {
        super.setup(path);
        tubes = input.stream()
                .map(l -> Arrays.stream(l.split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
        rows = tubes.length;
        columns = tubes[0].length;
    }

    private int getTube(Pos coord) {
        return tubes[coord.row][coord.col];
    }

    @Override
    public Object partOne() {
        return findRiskLevel();
    }

    private int findRiskLevel() {
        return findLowPoints().stream()
                .map(this::getTube)
                .mapToInt(t -> t + 1)
                .sum();
    }

    private List<Pos> findLowPoints() {
        return IntStream.range(0, rows).boxed()
                .flatMap(r -> IntStream.range(0, columns)
                        .mapToObj(c -> new Pos(r, c)))
                .filter(p -> findNeighbours(p)
                        .noneMatch(t -> getTube(t) < getTube(p)))
                .toList();
    }

    private Stream<Pos> findNeighbours(Pos coord) {
        return Stream.of(
                        new Pos(coord.row - 1, coord.col),
                        new Pos(coord.row + 1, coord.col),
                        new Pos(coord.row, coord.col - 1),
                        new Pos(coord.row, coord.col + 1))
                .filter(p -> p.row >= 0 && p.row < rows)
                .filter(p -> p.col >=0 && p.col < columns)
                .distinct();
    }

    @Override
    public Object partTwo() {
        return findBasins();
    }

    private int findBasins() {
        return findLowPoints().stream()
                .map(this::findBasin)
                .sorted(reverseOrder())
                .limit(3)
                .reduce((a, b) -> a * b)
                .orElseThrow();
    }

    private int findBasin(Pos point) {
        var basin = new HashSet<Pos>(){{add(point);}};
        var toAdd = grow(basin);
        while (!toAdd.isEmpty()) {
            basin.addAll(toAdd);
            toAdd = grow(basin);
        }
        return basin.size();
    }

    private Set<Pos> grow(Set<Pos> basin) {
        return basin.stream()
                .flatMap(this::findNeighbours)
                .filter(p -> !basin.contains(p))
                .filter(p -> getTube(p) < 9)
                .filter(p -> canAdd(p, basin))
                .collect(toSet());
    }

    private boolean canAdd(Pos point, Set<Pos> basin) {
        return findNeighbours(point)
                .filter(basin::contains)
                .anyMatch(p -> getTube(p) < getTube(point));
    }

}
