package me.vforchi.aoc.y2015.day17;

import me.vforchi.aoc.Day;
import org.paukov.combinatorics3.Generator;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.vavr.collection.Stream.ofAll;

public class Day17 extends Day {

    private record Container(int index, int capacity) {}

    private List<Container> containers;

    @Override
    public void setup(String path) {
        super.setup(path);
        containers = ofAll(input)
                .map(Integer::parseInt)
                .zipWithIndex()
                .map(t -> new Container(t._2, t._1))
                .toJavaList();
    }

    @Override
    public Object partOne() {
        return combinations(containers, 150).count();
    }

    @Override
    public Object partTwo() {
        return combinations(containers, 150)
                .map(List::size)
                .sorted()
                .findFirst()
                .orElseThrow();
    }

    private Stream<List<Container>> combinations(List<Container> containers, Integer sum) {
        return IntStream.range(2, containers.size()).boxed()
                .flatMap(n -> Generator.combination(containers).simple(n).stream()
                        .filter(l -> l.stream()
                                .map(Container::capacity)
                                .reduce(Integer::sum)
                                .filter(sum::equals)
                                .isPresent()));
    }

}
