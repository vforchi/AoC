package me.vforchi.aoc.y2019.day02;

import com.google.common.collect.Lists;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;

import java.util.stream.IntStream;

public class Day02 extends Day {

    private Intcode intcode;

    @Override
    public void setup(String path) {
        super.setup(path);
        intcode = Intcode.fromText(input.get(0));
    }

    @Override
    public Object partOne() {
        return run(12, 2);
    }

    @Override
    public Object partTwo() {
        var input = IntStream.range(0, 100).boxed().toList();
        return Lists.cartesianProduct(input, input).stream()
                .filter(l -> run(l.get(0), l.get(1)) == 19690720)
                .mapToInt(l -> 100 * l.get(0) + l.get(1))
                .findFirst()
                .orElseThrow();
    }

    public int run(int noun, int verb) {
        intcode.reset();
        intcode.setValue(1, noun);
        intcode.setValue(2, verb);
        intcode.run();
        return intcode.getValue(0);
    }

}
