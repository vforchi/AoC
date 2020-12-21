package aoc.y2020.day09;

import aoc.Day;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day09 extends Day {

    int preamble = 25;
    List<Long> numbers;

    @Override
    public Object partOne() {
        return IntStream.range(preamble, numbers.size()).boxed()
                .map(i -> numbers.subList(i - preamble, i+1))
                .filter(this::isNotSum)
                .map(l -> l.get(l.size()-1))
                .findFirst()
                .orElseThrow();
    }

    private boolean isNotSum(List<Long> numbers) {
        long last = numbers.get(numbers.size()-1);
        return Sets.combinations(Sets.newHashSet(numbers.subList(0, preamble)), 2).stream()
                .map(s -> s.stream().mapToLong(Long::longValue).sum())
                .allMatch(l -> l != last);
    }

    @Override
    public Object partTwo() {
        var target = Long.parseLong(partOne().toString());
        return IntStream.range(0, numbers.size()).boxed()
                .map(l -> isSum(numbers, target, l))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow();
    }

    static Long isSum(List<Long> numbers, long number, int start) {
        int i = start;
        long sum = 0;

        while (sum < number && i < numbers.size()) {
            sum += numbers.get(i++);
        }

        if (sum == number) {
            var interval = numbers.subList(start, i).stream().sorted().collect(Collectors.toList());
            return interval.get(0) + interval.get(interval.size()-1);
        } else {
            return null;
        }
    }

    @Override
    public void setup(String path) throws Exception {
        super.setup(path);
        this.numbers = input.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

}
