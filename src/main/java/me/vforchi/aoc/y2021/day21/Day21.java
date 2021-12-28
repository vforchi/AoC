package me.vforchi.aoc.y2021.day21;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import me.vforchi.aoc.Day;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class Day21 extends Day {

    private int start1;
    private int start2;

    @Override
    public void setup(String path) {
        super.setup(path);
        start1 = Integer.parseInt(input.get(0).substring(input.get(0).length() - 1));
        start2 = Integer.parseInt(input.get(1).substring(input.get(1).length() - 1));
    }

    @Override
    public Object partOne() {
        var game = Game1.start(start1, start2);
        while (!game.over()) {
            game.play();
        }
        return game.points();
    }

    @Override
    public Object partTwo() {
        Map<Game2, Long> games = new HashMap<>() {{ put(Game2.start(start1, start2), 1L); }};
        Map<Integer, Long> wins = new HashMap<>() {{ put(1, 0L); put(2, 0L); }};
        while (!games.isEmpty()) {
            var nextGamesByCompletion = games.entrySet().stream()
                    .flatMap(e -> e.getKey().play()
                            .map(g -> Tuple.of(g, e.getValue())))
                    .collect(Collectors.partitioningBy(t -> t._1.over()));

            nextGamesByCompletion.get(true).forEach(t -> wins.compute(t._1.winner(), (k, v) -> v + t._2));
            games = nextGamesByCompletion.get(false).stream()
                    .filter(g -> !g._1.over())
                    .collect(toMap(
                            Tuple2::_1,
                            Tuple2::_2,
                            Long::sum
                    ));
        }
        return Math.max(wins.get(1), wins.get(2));
    }

}
