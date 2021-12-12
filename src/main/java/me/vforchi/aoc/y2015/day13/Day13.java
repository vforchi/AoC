package me.vforchi.aoc.y2015.day13;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import me.vforchi.aoc.Day;
import org.paukov.combinatorics3.Generator;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class Day13 extends Day {

    private Map<Tuple2<String, String>, Integer> happinessChanges;
    private Set<String> people;

    @Override
    public void setup(String path) {
        super.setup(path);
        happinessChanges = input.stream()
                .map(s -> s.substring(0, s.length() - 1).split(" "))
                .collect(toMap(
                        t -> Tuple.of(t[0], t[t.length - 1]),
                        t -> (t[2].equals("gain") ? 1 : -1) * Integer.parseInt(t[3])
                ));
        people = happinessChanges.keySet().stream()
                .map(Tuple2::_1)
                .collect(toSet());
    }

    @Override
    public Object partOne() {
        return Generator.permutation(people).simple().stream()
                .mapToInt(this::countHappiness)
                .max()
                .orElseThrow();
    }

    @Override
    public Object partTwo() {
        people.add("me");
        return Generator.permutation(people).simple().stream()
                .mapToInt(this::countHappiness)
                .max()
                .orElseThrow();
    }

    private int countHappiness(List<String> people) {
        return IntStream.range(0, people.size())
                .map(i -> getHappinessChange(people.get(i), people.get((i + 1) % people.size())))
                .sum();
    }

    private int getHappinessChange(String p1, String p2) {
        if (Objects.equals(p1, "me") || Objects.equals(p2, "me")) {
            return 0;
        } else {
            return happinessChanges.get(Tuple.of(p1, p2)) + happinessChanges.get(Tuple.of(p2, p1));
        }
    }

}
