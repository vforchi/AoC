package me.vforchi.aoc.y2019.day03;

import me.vforchi.aoc.Day;

public class Day03 extends Day {

    private Wire wire1;
    private Wire wire2;

    @Override
    public void setup(String path) {
        super.setup(path);
        wire1 = Wire.fromText(input.get(0));
        wire2 = Wire.fromText(input.get(1));
    }

    @Override
    public Object partOne() {
        return wire1.intersect(wire2).stream()
                .mapToInt(Wire.Pos::manhattan)
                .min()
                .orElseThrow();
    }

    @Override
    public Object partTwo() {
        return wire1.intersect(wire2).stream()
                .mapToInt(p -> wire1.distance(p) + wire2.distance(p))
                .min()
                .orElseThrow();
    }

}
