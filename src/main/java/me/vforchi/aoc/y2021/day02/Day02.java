package me.vforchi.aoc.y2021.day02;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import me.vforchi.aoc.Day;

import java.util.List;

public class Day02 extends Day {

    private List<Tuple2<Direction, Long>> movements;

    public enum Direction {
        forward, down, up
    }

    public abstract class Position {
        protected long depth = 0;
        protected long horizontal = 0;
        abstract public Position move(Tuple2<Direction, Long> movement);

        public long position() {
            return horizontal * depth;
        }
    }

    public class Position1 extends Position {
        public Position move(Tuple2<Direction, Long> movement) {
            switch (movement._1) {
                case forward -> horizontal += movement._2;
                case up -> depth -= movement._2;
                case down -> depth += movement._2;
            }
            return this;
        }
    }

    public class Position2 extends Position {
        private int aim = 0;
        public Position move(Tuple2<Direction, Long> movement) {
            switch (movement._1) {
                case forward -> { horizontal += movement._2; depth += aim * movement._2; }
                case up -> aim -= movement._2;
                case down -> aim += movement._2;
            }
            return this;
        }
    }

    @Override
    public void setup(String path) {
        super.setup(path);
        movements = input.stream()
                .map(s -> s.split(" "))
                .map(s -> Tuple.of(Direction.valueOf(s[0]), Long.parseLong(s[1])))
                .toList();
    }

    @Override
    public Object partOne() {
        var submarine = new Position1();
        movements.forEach(submarine::move);
        return submarine.position();
    }

    @Override
    public Object partTwo() {
        var submarine = new Position2();
        movements.forEach(submarine::move);
        return submarine.position();
    }

}
