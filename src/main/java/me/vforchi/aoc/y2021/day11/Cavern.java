package me.vforchi.aoc.y2021.day11;

import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
public class Cavern {

    private record Pos(int row, int col) { }

    private final int[][] octopuses;
    public final int size;

    public int flash() {
        increaseAll();
        var flashing = getFlashing();
        var newFlashing = getFlashingNeighbours(flashing, flashing);
        while (!newFlashing.isEmpty()) {
            flashing.addAll(newFlashing);
            newFlashing = getFlashingNeighbours(newFlashing, flashing);
        }
        resetFlashing();
        return flashing.size();
    }

    private void increaseAll() {
        applyToAll((r, c) -> octopuses[r][c] = octopuses[r][c] + 1);
    }

    public Set<Pos> getFlashing() {
        return IntStream.range(0, size)
                .boxed()
                .flatMap(r -> IntStream.range(0, size).mapToObj(c -> new Pos(r, c)))
                .filter(this::flashes)
                .collect(toSet());
    }

    public boolean flashes(Pos pos) {
        return octopuses[pos.row][pos.col] > 9;
    }

    private Set<Pos> getFlashingNeighbours(Set<Pos> octopuses, Set<Pos> flashing) {
        return octopuses.stream()
                .flatMap(this::increaseNeighbours)
                .filter(p -> !flashing.contains(p))
                .collect(toSet());
    }

    private Stream<Pos> increaseNeighbours(Pos pos) {
        var steps = Set.of(-1, 0, 1);
        return Sets.cartesianProduct(steps, steps).stream()
                .filter(l -> l.get(0) != 0 || l.get(1) != 0)
                .map(l -> new Pos(pos.row + l.get(0), pos.col + l.get(1)))
                .filter(p -> p.row > -1 && p.row < size)
                .filter(p -> p.col > -1 && p.col < size)
                .filter(p -> increaseOctopus(p) > 9);
    }

    public int increaseOctopus(Pos pos) {
        return octopuses[pos.row][pos.col] = octopuses[pos.row][pos.col]  + 1;
    }

    private void resetFlashing() {
        applyToAll((r, c) -> {
            if (octopuses[r][c] > 9) {
                octopuses[r][c] = 0;
            }
        });
    }

    private void applyToAll(BiConsumer<Integer, Integer> consumer) {
        IntStream.range(0, size)
                .forEach(r -> IntStream.range(0, size)
                        .forEach(c -> consumer.accept(r, c)));
    }

    public static Cavern fromLines(List<String> lines) {
        var octopuses = lines.stream()
                .map(l -> Arrays.stream(l.split(""))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
        return new Cavern(octopuses, octopuses.length);
    }
}






