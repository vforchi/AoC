package me.vforchi.aoc.y2019.day09;

import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;

public class Day09 extends Day {

    private Intcode intcode;

    @Override
    public void setup(String path) {
        super.setup(path);
        intcode = Intcode.fromText(input.get(0));
    }

    @Override
    public Object partOne() {
        return intcode.run(1L).get(0);
    }


    @Override
    public Object partTwo() {
        intcode.reset();
        return intcode.run(2L).get(0);
    }

}
