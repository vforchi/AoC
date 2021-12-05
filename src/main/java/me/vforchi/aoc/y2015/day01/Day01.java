package me.vforchi.aoc.y2015.day01;

import me.vforchi.aoc.Day;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day01 extends Day {

    @Override
    public Object partOne() {
        var chars = input.get(0).chars()
                .boxed()
                .collect(groupingBy(identity(), counting()));
        return chars.get((int) '(') - chars.get((int) ')');
    }

    @Override
    public Object partTwo() {
        int pos = 0;
        int floor = 0;
        while (floor >= 0) {
            floor += (input.get(0).charAt(pos++) == '(') ? 1 : -1;
        }
        return pos;
    }

}
