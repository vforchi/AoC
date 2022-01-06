package me.vforchi.aoc.y2019.day19;

import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;

import java.util.stream.IntStream;

import static java.lang.Boolean.TRUE;

public class Day19 extends Day {

    private Intcode droid;

    @Override
    public void setup(String path) {
        super.setup(path);
        droid = Intcode.fromText(input.get(0));
    }

    @Override
    public Object partOne() {
        return IntStream.range(0, 50).boxed()
                .flatMap(x -> IntStream.range(0, 50)
                            .mapToObj(y -> tractorBeam(y, x)))
                .filter(TRUE::equals)
                .count();
    }

    @Override
    public Object partTwo() {
        int x = 0, y = 0;
        while (true) {
            while (!tractorBeam(x, y + 99)) {
                x += 1;
            }
            while (!tractorBeam(x + 99, y)) {
                y += 1;
            }
            if (tractorBeam(x, y + 99)) {
                return x * 10000 + y;
            }
        }
    }

    private boolean tractorBeam(int x, int y) {
        droid.reset();
        droid.run(x, y);
        return droid.getLastOutput() == 1;
    }

}
