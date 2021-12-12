package me.vforchi.aoc.y2015.day14;

import me.vforchi.aoc.Day;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class Day14 extends Day {

    private record Reindeer(String name, int speed, int endurance, int pause) {
        public static Reindeer fromText(String text) {
            var tokens = text.substring(0, text.length() - 1).split(" ");
            return new Reindeer(tokens[0], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[6]), Integer.parseInt(tokens[13]));
        }

        public int distance(int time) {
            var fullRounds = speed * endurance * (time / (endurance + pause));
            var rem = time % (endurance + pause);
            var lastRound = (rem > endurance) ? speed * endurance : speed * rem;
            return fullRounds + lastRound;
        }
    }

    private List<Reindeer> reindeers;

    @Override
    public void setup(String path) {
        super.setup(path);
        reindeers = input.stream()
                .map(Reindeer::fromText)
                .toList();
    }

    @Override
    public Object partOne() {
        return reindeers.stream()
                .mapToInt(r -> r.distance(2503))
                .max()
                .orElseThrow();
    }

    @Override
    public Object partTwo() {
        return IntStream.rangeClosed(1, 2503)
                .boxed()
                .flatMap(t -> score(reindeers, t))
                .collect(groupingBy(identity(), counting()))
                .values().stream()
                .max(Long::compareTo)
                .orElseThrow();
    }

    private Stream<String> score(List<Reindeer> reindeers, int time) {
        var distances = reindeers.stream()
                .collect(toMap(
                        Function.identity(),
                        r -> r.distance(time)));
        var maxDistance = distances.values().stream()
                .max(Integer::compareTo).orElseThrow();
        return distances.entrySet().stream()
                .filter(e -> Objects.equals(e.getValue(), maxDistance))
                .map(e -> e.getKey().name);
    }

}
