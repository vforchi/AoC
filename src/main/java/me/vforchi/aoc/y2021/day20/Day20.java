package me.vforchi.aoc.y2021.day20;

import me.vforchi.aoc.Day;

import java.util.stream.Stream;

public class Day20 extends Day {

    private Image image;

    @Override
    public void setup(String path) {
        super.setup(path);
        image = Image.fromText(input);
    }

    @Override
    public Object partOne() {
        return grow(2);
    }

    @Override
    public Object partTwo() {
        return grow(50);
    }

    private int grow(int rounds) {
        return Stream.iterate(image, Image::grow)
                .skip(rounds)
                .mapToInt(Image::countLit)
                .findFirst()
                .orElseThrow();
    }
    
}
