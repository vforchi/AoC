package me.vforchi.aoc.y2019.day05;

import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;

public class Day05 extends Day {

    private Intcode intcode;

    @Override
    public void setup(String path) {
        super.setup(path);
        intcode = Intcode.fromText(this.input.get(0));
    }

    @Override
    public Object partOne() {
        intcode.reset();
        intcode.run(1);
        return intcode.getLastOutput();
    }

    @Override
    public Object partTwo() {
        intcode.reset();
        intcode.run(5);
        return intcode.getOutput(0);
    }

}
