package me.vforchi.aoc.y2019.day11;

import lombok.AllArgsConstructor;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class Day11 extends Day {
    
    private final int BLACK = 0;
    private final int WHITE = 1;

    private record Pos(int x, int y) { }

    @AllArgsConstructor
    private static class Robot {
        int x;
        int y;
        int direction;

        public Pos pos() {
            return new Pos(x, y);
        }

        public void move(int direction) {
            this.direction += (direction == 0) ? -1 : 1;
            this.direction = this.direction % 4;
            switch (this.direction) {
                case 0 -> y -= 1;
                case 1, -3 -> x += 1;
                case 2, -2 -> y += 1;
                case 3, -1 -> x -= 1;
            }
        }
    }

    private Intcode paintingProgram;

    @Override
    public void setup(String path) {
        super.setup(path);
        paintingProgram = Intcode.fromText(input.get(0));
    }

    @Override
    public Object partOne() {
        return paint(BLACK).size();
    }

    private Map<Pos, Integer> paint(int color) {
        var ship = new HashMap<Pos, Integer>() {{ put(new Pos(0, 0), color); }};
        var robot = new Robot(0, 0, 0);
        paintingProgram.reset();
        while (paintingProgram.isNotFinished()) {
            paintingProgram.run(ship.getOrDefault(robot.pos(), BLACK));
            ship.put(robot.pos(), paintingProgram.getLastOutput(1).intValue());
            robot.move(paintingProgram.getLastOutput(0).intValue());
        }
        return ship;
    }

    @Override
    public Object partTwo() {
        return toRegistrationId(paint(WHITE));
    }

    private String toRegistrationId(Map<Pos, Integer> ship) {
        var minx = ship.keySet().stream()
                .mapToInt(Pos::x)
                .min().orElseThrow();
        var maxx = ship.keySet().stream()
                .mapToInt(Pos::x)
                .max().orElseThrow();
        var miny = ship.keySet().stream()
                .mapToInt(Pos::y)
                .min().orElseThrow();
        var maxy = ship.keySet().stream()
                .mapToInt(Pos::y)
                .max().orElseThrow();
        return "\n" + IntStream.rangeClosed(miny, maxy)
                .mapToObj(y -> IntStream.rangeClosed(minx, maxx)
                        .mapToObj(x -> ship.getOrDefault(new Pos(x, y), BLACK) == WHITE ? "#" : ".")
                        .collect(joining("", "", "\n")))
                .collect(joining());
    }

}
