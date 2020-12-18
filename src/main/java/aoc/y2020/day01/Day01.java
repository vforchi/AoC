package aoc.y2020.day01;

import aoc.Day;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.stream.Collectors;

public class Day01 extends Day {

    @Override
    public Object partOne() {
        return combinations(2);
    }

    @Override
    public Object partTwo() {
        return combinations(3);
    }

    public Long combinations(int size) {
        var numbers = input.stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        return Sets.combinations(numbers, size).stream()
                .filter(s -> sum(s) == 2020)
                .findFirst()
                .map(this::multiply)
                .orElseThrow();
    }

    private Long multiply(Set<Long> numbers) {
        return numbers.stream().reduce((a,b) -> a*b).orElseThrow();
    }

    private Long sum(Set<Long> numbers) {
        return numbers.stream().reduce(Long::sum).orElseThrow();
    }

}
