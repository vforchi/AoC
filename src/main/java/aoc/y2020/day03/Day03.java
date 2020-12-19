package aoc.y2020.day03;

import aoc.Day;
import io.vavr.Tuple2;

import java.util.stream.Stream;


public class Day03 extends Day {

    @Override
    public Object partOne() {
        return crashes(3, 1);
    }

    @Override
    public Object partTwo() {
        return crashes(1, 1) *
                crashes(3, 1) *
                crashes(5, 1) *
                crashes(7, 1) *
                crashes(1, 2);
    }

    private Long crashes(int right, int down) {
        int w = input.get(0).length();
        int h = input.size();
        return Stream.iterate(new Tuple2<>(0, 0), t -> new Tuple2<>((t._1+right)%w, (t._2+down)%h))
                .limit(h/down)
                .map(t -> input.get(t._2).charAt(t._1))
                .filter(c -> c == '#')
                .count();
    }
    
}
