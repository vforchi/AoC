package me.vforchi.aoc.y2021.day14;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import me.vforchi.aoc.Day;

import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.max;
import static java.util.Collections.min;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class Day14 extends Day {

    private Map<String, String> rules;

    @Override
    public void setup(String path) {
        super.setup(path);
        rules = input.stream()
                .skip(2)
                .map(l -> l.split(" -> "))
                .collect(toMap(t -> t[0], t -> t[1]));
    }

    @Override
    public Object partOne() {
        return iterate(10);
    }

    @Override
    public Object partTwo() {
        return iterate(40);
    }

    public Long iterate(int rounds) {
        var start = input.get(0);
        var grownPairs = Stream.iterate(pairs(start), this::grow)
                .skip(rounds)
                .map(poly -> poly.entrySet().stream()
                        .map(Tuple::fromEntry)
                        .flatMap(t -> Stream.of(
                                t.map(p -> p.charAt(0), v -> v * 0.5),
                                t.map(p -> p.charAt(1), v -> v * 0.5)
                        ))
                        .collect(toMap(Tuple2::_1, Tuple2::_2, Double::sum)))
                .findFirst().orElseThrow();
        // each element is counted twice, so we halved the count, but the first and last are counted only once, so we compensate here.
        grownPairs.computeIfPresent(start.charAt(0), (k, v) -> v + 0.5);
        grownPairs.computeIfPresent(start.charAt(start.length() - 1), (k, v) -> v + 0.5);
        return (long) (max(grownPairs.values()) - min(grownPairs.values()));
    }

    private Map<String, Long> pairs(String polymer) {
        return IntStream.range(1, polymer.length())
                .mapToObj(i -> polymer.substring(i - 1, i + 1))
                .collect(groupingBy(identity(), counting()));
    }

    private Map<String, Long> grow(Map<String, Long> pairs) {
        return pairs.entrySet().stream()
                .flatMap(this::growPair)
                .collect(toMap(Tuple2::_1, Tuple2::_2, Long::sum));
    }

    private Stream<Tuple2<String, Long>> growPair(Map.Entry<String, Long> pair) {
        var tuple = Tuple.fromEntry(pair);
        var base = tuple._1;
        if (rules.containsKey(base)) {
            var newBase = rules.get(base);
            return Stream.of(
                    tuple.map1(p -> base.charAt(0) + newBase),
                    tuple.map1(p -> newBase + base.charAt(1))
            );
        } else {
            return Stream.of(tuple);
        }
    }

}
