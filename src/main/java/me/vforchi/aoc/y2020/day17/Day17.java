package me.vforchi.aoc.y2020.day17;

import me.vforchi.aoc.Day;
import com.google.common.collect.Sets;
import io.vavr.Tuple2;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vavr.collection.Stream.ofAll;

public class Day17 extends Day {

    private static Set<Integer> steps = Set.of(-1, 0, 1);
    private static Set<List<Integer>> cartesianProduct = Sets.cartesianProduct(List.of(steps, steps, steps, steps));

    @Override
    public Object partOne() {
        return solve(6, false);
    }

    @Override
    public Object partTwo() {
        return solve(6, true);
    }

    private Object solve(int steps, boolean fourDim) {
        return Stream.iterate(getActiveCubes(input, fourDim), this::nextStep)
                .skip(steps)
                .findFirst()
                .map(List::size)
                .orElseThrow();
    }

    private List<Cube> nextStep(List<Cube> activeCubes) {
        List<Cube> stayActiveCubes = activeCubes.stream()
                .filter(c -> staysActive(c, activeCubes))
                .collect(Collectors.toList());

        List<Cube> becomeActiveCubes = activeCubes.stream()
                .flatMap(c -> getInactiveNeighbours(c, activeCubes))
                .distinct()
                .filter(c -> becomesActive(c, activeCubes))
                .collect(Collectors.toList());

        stayActiveCubes.addAll(becomeActiveCubes);
        return stayActiveCubes;
    }

    private Stream<Cube> getInactiveNeighbours(Cube cube, List<Cube> activeCubes) {
        return cartesianProduct.stream()
                .filter(this::notAllZero)
                .map(cube::offset)
                .filter(c -> !activeCubes.contains(c));
    }

    private boolean notAllZero(List<Integer> l) {
        return l.stream().filter(i -> i == 0).count() != 4;
    }

    private boolean staysActive(Cube cube, List<Cube> activeCubes) {
        long active = getActiveAdjacent(cube, activeCubes);
        return (active == 2 || active == 3);
    }

    private boolean becomesActive(Cube cube, List<Cube> activeCubes) {
        return (getActiveAdjacent(cube, activeCubes) == 3);
    }

    private long getActiveAdjacent(Cube cube, List<Cube> activeCubes) {
        return activeCubes.stream()
                .filter(c -> c.isAdjacent(cube))
                .count();
    }

    private List<Cube> getActiveCubes(List<String> input, boolean fourDim) {
        return ofAll(input)
                .zipWithIndex()
                .flatMap(tuple -> makeCubes(tuple._1, tuple._2, fourDim))
                .collect(Collectors.toList());
    }

    private io.vavr.collection.Stream<Cube> makeCubes(String line, Integer y, boolean fourDim) {
        return ofAll(line.toCharArray())
                .zipWithIndex()
                .filter(tuple -> tuple._1 == '#')
                .map(Tuple2::_2)
                .map(x -> new Cube(x, y, 0, fourDim ? 0 : null));
    }

}