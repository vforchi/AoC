package me.vforchi.aoc.y2021.day08;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.StringUtils.replaceEach;

public class DisplayLog {

    private static Map<String, String> digits = Map.of(
            "abcefg", "0",
            "cf", "1",
            "acdeg", "2",
            "acdfg", "3",
            "bcdf", "4",
            "abdfg", "5",
            "abdefg", "6",
            "acf", "7",
            "abcdefg", "8",
            "abcdfg", "9"
    );
    private static final String[] toBars = new String[] { "a", "b", "c", "d", "e", "f", "g" };

    private String[] fromBars;
    private final List<String> values;

    public DisplayLog(List<String> patterns, List<String> values, Map<String, Long> barCounts) {
        this.values = values;
        this.fromBars = identifyBars(patterns, barCounts);
    }

    private String[] identifyBars(List<String> digits, Map<String, Long> barCounts) {
        var one = digits.get(0);
        var seven = digits.get(1);
        var four = digits.get(2);

        var a = StringUtils.deleteAny(seven, one);
        var b = identifyBar(barCounts, 6);
        var e = identifyBar(barCounts, 4);
        var f = identifyBar(barCounts, 9);
        var c = identifyBar(barCounts, 8, a);
        var d = StringUtils.deleteAny(four, seven + b);
        var g = identifyBar(barCounts, 7, d);

        return new String[]{ a, b, c, d, e, f, g };
    }

    private String identifyBar(Map<String, Long> bars, int count) {
        return identifyBar(bars, count, null);
    }

    private String identifyBar(Map<String, Long> bars, int count, String exclude) {
        return bars.entrySet()
                .stream()
                .filter(e -> e.getValue() == count)
                .filter(e -> !Objects.equals(e.getKey(), exclude))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }

    public static DisplayLog fromText(String line) {
        var tokens = line.split(" \\| ");
        var digits = Arrays.stream(tokens[0].split(" "))
                .sorted(comparing(String::length))
                .toList();
        var values = Arrays.stream(tokens[1].split(" "))
                .toList();
        var barCounts = String.join("", digits).chars()
                .boxed()
                .collect(groupingBy(c -> String.valueOf((char) c.intValue()), counting()));
        return new DisplayLog(digits, values, barCounts);
    }

    private static String sortDigit(String digit) {
        return digit.chars()
                .sorted()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    public long output() {
        var text = values.stream()
                .map(v -> replaceEach(v, fromBars, toBars))
                .map(v -> digits.get(sortDigit(v)))
                .collect(Collectors.joining());
        return Long.parseLong(text);
    }

}
