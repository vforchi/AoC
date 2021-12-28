package me.vforchi.aoc.y2021.day10;

import io.vavr.control.Either;
import me.vforchi.aoc.Day;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day10 extends Day {

    private static List<String> open = List.of("(", "[", "{", "<");

    private static Map<String, String> closeToOpen = Map.of(")", "(", "]", "[", "}", "{", ">", "<");
    private static Map<String, Long> scores1 = Map.of(")", 3L, "]", 57L, "}", 1197L, ">", 25137L);
    private static Map<String, Long> scores2 = Map.of("(", 1L, "[", 2L, "{", 3L, "<", 4L);

    @Override
    public Object partOne() {
        return input.stream()
                .map(Day10::findFirstCorrupted)
                .filter(Either::isLeft)
                .map(Either::getLeft)
                .mapToLong(scores1::get)
                .sum();
    }

    @Override
    public Object partTwo() {
        var autocomplete = input.stream()
                .map(Day10::findFirstCorrupted)
                .filter(Either::isRight)
                .map(Either::get)
                .map(Day10::closeSequence)
                .sorted()
                .toList();
        return autocomplete.get(autocomplete.size() / 2);
    }

    private static Either<String, Deque<String>> findFirstCorrupted(String line) {
        Deque<String> blocks = new ArrayDeque<>();
        for (var c: line.getBytes()) {
            var s = String.valueOf((char) c);
            if (open.contains(s)) {
                blocks.add(s);
            } else {
                var last = blocks.removeLast();
                if (!last.equals(closeToOpen.get(s))) {
                    return Either.left(s);
                }
            }
        }
        return Either.right(blocks);
    }

    private static long closeSequence(Deque<String> open) {
        return IntStream.range(0, open.size())
                .mapToObj(i -> open.removeLast())
                .map(scores2::get)
                .reduce((a, b) -> a * 5 + b)
                .orElseThrow();
    }

}
