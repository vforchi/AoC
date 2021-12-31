package me.vforchi.aoc.y2015.day03;

import io.vavr.Tuple;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import me.vforchi.aoc.Day;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day03 extends Day {

    @AllArgsConstructor
    public enum Direction {
        N('^'), E('>'), S('v'), W('<');
        char c;

        public static Direction fromChar(int c) {
            return Arrays.stream(values())
                    .filter(d -> d.c == (char) c)
                    .findFirst()
                    .orElseThrow();
        }
    }

    @EqualsAndHashCode
    @AllArgsConstructor
    public static class Position {
        int x, y;
        public static final Position ZERO = new Position(0, 0);
        public Position move(Direction dir) {
            int xx = x, yy = y;
            switch (dir) {
                case N -> yy += 1;
                case E -> xx += 1;
                case S -> yy += -1;
                case W -> xx += -1;
            }
            return new Position(xx, yy);
        }
    }

    private List<Direction> movements;

    @Override
    public void setup(String path) {
        super.setup(path);
        movements = input.get(0).chars()
                .mapToObj(Direction::fromChar)
                .toList();
    }

    @Override
    public Object partOne() {
        var it = movements.iterator();
        return Stream.iterate(Position.ZERO,
                        p -> it.hasNext(),
                        p -> p.move(it.next()))
                .collect(groupingBy(identity(), counting()))
                .keySet().size();
    }

    @Override
    public Object partTwo() {
        var it = movements.iterator();
        UnaryOperator<Position> move = p -> p.move(it.next());
        return Stream.iterate(Tuple.of(Position.ZERO, Position.ZERO),
                        t -> it.hasNext(),
                        t -> t.map(move, move))
                .flatMap(t -> Stream.of(t._1, t._2))
                .collect(groupingBy(identity(), counting()))
                .keySet().size();
    }

}
