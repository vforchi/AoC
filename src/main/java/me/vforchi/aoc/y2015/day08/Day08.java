package me.vforchi.aoc.y2015.day08;

import me.vforchi.aoc.Day;
import org.apache.commons.lang3.StringUtils;

public class Day08 extends Day {

    @Override
    public Object partOne() {
       return input.stream()
               .mapToInt(Day08::decode)
               .sum();
    }

    // TODO: this is wrong, and returns 1378 instead of 1371. No idea why
    private static int decode(String string) {
        var quotes = StringUtils.countMatches(string, "\\\"");
        var backslashes = StringUtils.countMatches(string, "\\\\");
        string = string.replaceAll("\\\\\\\\", "");
        var ascii = StringUtils.countMatches(string, "\\x");
        return 2 + quotes + backslashes + 3 * ascii;
    }

    @Override
    public Object partTwo() {
        return input.stream()
                .mapToInt(Day08::encode)
                .sum();
    }

    private static int encode(String string) {
        return 2 + StringUtils.countMatches(string, "\"")
                + StringUtils.countMatches(string, "\\");
    }

}
