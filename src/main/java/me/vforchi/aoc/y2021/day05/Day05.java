package me.vforchi.aoc.y2021.day05;

import lombok.AllArgsConstructor;
import me.vforchi.aoc.Day;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.*;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day05 extends Day {

    @AllArgsConstructor
    public static class Line {
        static Pattern pattern = Pattern.compile("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)");
        int x1, x2, y1, y2;
        boolean diagonal;

        public Stream<String> toPoints() {
            if (x1 == x2) {
                return IntStream.rangeClosed(min(y1, y2), max(y1, y2))
                        .mapToObj(y -> String.format("%d-%d", x1, y));
            } else if (y1 == y2) {
                return IntStream.rangeClosed(min(x1, x2), max(x1, x2))
                        .mapToObj(x -> String.format("%d-%d", x, y1));
            } else if (diagonal) {
                var dirx = (x2 - x1) / abs(x2 - x1);
                var diry = (y2 -y1) / abs(y2 - y1);
                return IntStream.rangeClosed(0, abs(x2 - x1))
                        .mapToObj(s -> String.format("%d-%d", x1 + s * dirx, y1 + s * diry));
            } else {
                return Stream.empty();
            }
        }

        public static Line fromText(String text, boolean diagonal) {
            var m = pattern.matcher(text);
            if (m.matches()) {
                var x1 = Integer.parseInt(m.group(1));
                var y1 = Integer.parseInt(m.group(2));
                var x2 = Integer.parseInt(m.group(3));
                var y2 = Integer.parseInt(m.group(4));
                return new Line(x1, x2, y1, y2, diagonal);
            } else {
                throw new RuntimeException();
            }
        }
    }

    private Map<String, Long> getVents(boolean diagonal) {
        return input.stream()
                .map(l -> Line.fromText(l, diagonal))
                .flatMap(Line::toPoints)
                .collect(groupingBy(identity(), counting()));
    }

    private Long count(Map<String, Long> vents) {
        return vents.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .count();
    }

    @Override
    public Object partOne() {
        return count(getVents(false));
    }

    @Override
    public Object partTwo() {
        return count(getVents(true));
    }

}
