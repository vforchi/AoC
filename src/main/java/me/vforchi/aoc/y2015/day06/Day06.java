package me.vforchi.aoc.y2015.day06;

import me.vforchi.aoc.Day;

public class Day06 extends Day {

    public static class Lights {
        public int[][] lights = new int[1000][1000];

        public int count() {
            int count = 0;
            for (int i = 0; i < 1000; i++) {
                for (int j = 0; j < 1000; j++) {
                    count += lights[i][j];
                }
            }
            return count;
        }
    }

    @Override
    public Object partOne() {
        var lights = new Lights();
        input.stream()
                .map(Command::fromString)
                .forEach(c -> c.apply1(lights));
        return lights.count();
    }

    @Override
    public Object partTwo() {
        var lights = new Lights();
        input.stream()
                .map(Command::fromString)
                .forEach(c -> c.apply2(lights));
        return lights.count();    }

}
