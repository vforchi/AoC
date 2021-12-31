package me.vforchi.aoc.y2015.day05;

import me.vforchi.aoc.Day;

import java.util.regex.Pattern;

public class Day05 extends Day {

    private static final Pattern vowelPattern = Pattern.compile("[aeiou]");
    private static final Pattern pairPattern = Pattern.compile("([a-z])\\1");
    private static final Pattern forbiddenPattern = Pattern.compile("ab|cd|pq|xy");

    private static final Pattern doublePairPattern = Pattern.compile("([a-z][a-z]).*\\1");
    private static final Pattern repeatedPattern = Pattern.compile("([a-z]).\\1");

    @Override
    public Object partOne() {
        return input.stream()
                .filter(Day05::hasVowels)
                .filter(Day05::hasPair)
                .filter(Day05::hasNoForbidden)
                .count();
    }

    private static boolean hasVowels(String word) {
        return vowelPattern.matcher(word).results().count() >= 3;
    }

    private static boolean hasPair(String word) {
        return pairPattern.matcher(word).results().findAny().isPresent();
    }

    private static boolean hasNoForbidden(String word) {
        return forbiddenPattern.matcher(word).results().findAny().isEmpty();
    }

    @Override
    public Object partTwo() {
        return input.stream()
                .filter(Day05::hasDoublePair)
                .filter(Day05::hasRepeated)
                .count();
    }

    private static boolean hasDoublePair(String word) {
        return doublePairPattern.matcher(word).results().findAny().isPresent();
    }

    private static boolean hasRepeated(String word) {
        return repeatedPattern.matcher(word).results().findAny().isPresent();
    }

}
