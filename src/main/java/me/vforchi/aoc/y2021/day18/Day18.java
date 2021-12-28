package me.vforchi.aoc.y2021.day18;

import com.google.common.collect.Lists;
import io.vavr.Tuple;
import me.vforchi.aoc.Day;

public class Day18 extends Day {

    @Override
    public Object partOne() {
        return input.stream()
                .map(SnailfishNumber::fromText)
                .reduce(SnailfishNumber::add)
                .map(SnailfishNumber::magnitude)
                .orElseThrow();
    }

    @Override
    public Object partTwo() {
        return Lists.cartesianProduct(input, input).stream()
                .map(l -> Tuple.of(l.get(0), l.get(1)))
                .filter(t -> !t._1.equals(t._2))
                .map(t -> t.map(SnailfishNumber::fromText, SnailfishNumber::fromText))
                .map(t -> t._1.add(t._2))
                .mapToInt(SnailfishNumber::magnitude)
                .max()
                .orElseThrow();
    }

}
