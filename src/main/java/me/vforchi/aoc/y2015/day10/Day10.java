package me.vforchi.aoc.y2015.day10;

import me.vforchi.aoc.Day;

import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day10 extends Day {

    private static Pattern regex = Pattern.compile("([0-9])\\1*");
    private String inputString;
    private String partOneSolution;

    @Override
    public void setup(String path) {
        super.setup(path);
        inputString = input.get(0);
    }

    private String play(String numbers) {
        var result = new StringBuilder();
        var matcher = regex.matcher(numbers);

        while (matcher.find()) {
            result.append(matcher.group(0).length());
            result.append(matcher.group(1));
        }
        return result.toString();
    }

    @Override
    public Object partOne() {
        partOneSolution = Stream.iterate(inputString, this::play)
                .limit(41)
                .max(Comparator.comparing(String::length))
                .orElseThrow();
        return partOneSolution.length();
    }

    @Override
    public Object partTwo() {
        return Stream.iterate(partOneSolution, this::play)
                .limit(11)
                .mapToInt(String::length)
                .max()
                .orElseThrow();
    }

}
