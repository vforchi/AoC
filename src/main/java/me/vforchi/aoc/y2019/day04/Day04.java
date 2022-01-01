package me.vforchi.aoc.y2019.day04;

import me.vforchi.aoc.Day;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day04 extends Day {

    private Integer minPassword = 178416;
    private Integer maxPassword = 676461;

    @Override
    public Object partOne() {
        return IntStream.rangeClosed(minPassword, maxPassword)
                .mapToObj(Objects::toString)
                .filter(Day04::isValid1)
                .count();
    }

    private static boolean isValid1(String password) {
        return isNonDecreasing(password) && hasAtLeastCouple(password);
    }

    private static boolean isNonDecreasing(String password) {
        return Arrays.equals(password.chars().sorted().toArray(), password.chars().toArray());
    }

    private static boolean hasAtLeastCouple(String password) {
        return IntStream.range(0, password.length() - 1)
                .anyMatch(i -> password.charAt(i) == password.charAt(i + 1));
    }

    @Override
    public Object partTwo() {
        return IntStream.rangeClosed(minPassword, maxPassword)
                .mapToObj(Objects::toString)
                .filter(Day04::isValid2)
                .count();
    }

    private static boolean isValid2(String password) {
        return isNonDecreasing(password) && hasCouple(password);
    }

    private static boolean hasCouple(String password) {
        return IntStream.range(0, password.length() - 1)
                .filter(i -> password.charAt(i) == password.charAt(i + 1))
                .filter(i -> (i + 2 == password.length()) || (password.charAt(i + 1) != password.charAt(i + 2)))
                .filter(i -> (i == 0) || (password.charAt(i - 1) != password.charAt(i)))
                .findFirst()
                .isPresent();
    }

}
