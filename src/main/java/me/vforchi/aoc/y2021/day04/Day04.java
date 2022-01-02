package me.vforchi.aoc.y2021.day04;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.Utils;

import java.util.*;

import static java.util.stream.Collectors.toCollection;

public class Day04 extends Day {

    private List<Integer> numbers;
    private List<Board> boards;
    private Deque<Tuple2<Integer, Board>> winners = new ArrayDeque<>();

    @Override
    public void setup(String path) {
        super.setup(path);
        numbers = Arrays.stream(input.get(0).split(","))
                .map(Integer::parseInt)
                .toList();
        boards = Utils.partitionByEmptyLine(input.subList(2, input.size()))
                .map(Board::fromText)
                .collect(toCollection(ArrayList::new));
        for (int n: numbers) {
            boards.forEach(b -> b.play(n));
            var roundWinners = boards.stream()
                    .filter(Board::won)
                    .toList();
            roundWinners.forEach(w -> {
                winners.add(Tuple.of(n, w));
                boards.remove(w);
            });
        }
    }

    @Override
    public Object partOne() {
       var w = winners.getFirst();
       return w._1 * w._2.score();
    }

    @Override
    public Object partTwo() {
        var w = winners.getLast();
        return w._1 * w._2.score();
    }

}
