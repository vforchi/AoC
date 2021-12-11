package me.vforchi.aoc.y2015.day11;

import me.vforchi.aoc.Day;

import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 extends Day {

    private String password;
    private String firstPassword;

    @Override
    public void setup(String path) {
        super.setup(path);
        password = input.get(0);
    }

    public static String toAlphabetic(long num) {
        char[] str = Long.toString(num, 26).toCharArray();
        for (int i = 0; i < str.length; i++) {
            str[i] += str[i] > '9' ? 10 : 49;
        }
        return new String(str);
    }

    public static long toInteger(String str) {
        byte[] bytes = str.getBytes();
        long pow = 1;
        long res = 0;
        for (int i = bytes.length - 1; i > -1; i--) {
            res += pow * (((long) bytes[i]) - 97);
            pow *= 26;
        }
        return res;
    }

    @Override
    public Object partOne() {
        firstPassword = Stream.iterate(toInteger(password), i -> i + 1)
                .map(Day11::toAlphabetic)
                .filter(Day11::isGoodPassword)
                .findFirst()
                .orElseThrow();
        return firstPassword;
    }

    private static boolean isGoodPassword(String password) {
        var m = hasDoublePair.matcher(password);
        return !password.contains("i")
                && !password.contains("o")
                && !password.contains("l")
                && hasIncreasingSequence(password)
                && m.matches();
    }
    private static final Pattern hasDoublePair = Pattern.compile(".*([a-z])\\1.*([a-z])\\2");

    private static boolean hasIncreasingSequence(String password) {
        return IntStream.range(0, password.length() - 2)
                .filter(i -> password.charAt(i + 1) == password.charAt(i) + 1)
                .anyMatch(i -> password.charAt(i + 2) == password.charAt(i + 1) + 1);
    }

    @Override
    public Object partTwo() {
        return Stream.iterate(toInteger(firstPassword) + 1, i -> i + 1)
                .map(Day11::toAlphabetic)
                .filter(Day11::isGoodPassword)
                .findFirst()
                .orElseThrow();
    }

}
