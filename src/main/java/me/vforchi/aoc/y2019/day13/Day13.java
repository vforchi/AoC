package me.vforchi.aoc.y2019.day13;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;
import org.apache.commons.collections4.ListUtils;

import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public class Day13 extends Day {
    
    private record Pos(int x, int y) { }

    private Intcode cabinet;

    @Override
    public void setup(String path) {
        super.setup(path);
        cabinet = Intcode.fromText(input.get(0));
    }

    @Override
    public Object partOne() {
        cabinet.reset();
        return play().values().stream()
                .filter(p -> p == 2)
                .count();
    }

    @Override
    public Object partTwo() {
        cabinet.reset();
        cabinet.setValue(0, 2);
        int joystick = 0;
        int score = 0;
        Map<Pos, Integer> screen;
        while (cabinet.isNotFinished()) {
            screen = play(joystick);
            score = screen.getOrDefault(new Pos(-1, 0), score);
            joystick = findBallAndPaddle(screen)
                    .map(t -> (int) Math.signum(t._1 - t._2))
                    .orElse(0);
        }
        return score;
    }

    private Map<Pos, Integer> play(long... inputs) {
        return ListUtils.partition(cabinet.run(inputs), 3).stream()
                .collect(toMap(
                        l -> new Pos(l.get(0).intValue(), l.get(1).intValue()),
                        l -> l.get(2).intValue(),
                        (v1, v2) -> v2));
    }

    private Optional<Tuple2<Integer, Integer>> findBallAndPaddle(Map<Pos, Integer> screen) {
        return find(screen, 4)
                .map(b -> Tuple.of(b, findPaddle(screen)));
    }

    private Integer findPaddle(Map<Pos, Integer> screen) {
        return find(screen, 3).orElseThrow();
    }

    private Optional<Integer> find(Map<Pos, Integer> screen, int val) {
        return screen.entrySet().stream()
                .filter(e -> e.getValue() == val)
                .map(e -> e.getKey().x)
                .findFirst();
    }

}
