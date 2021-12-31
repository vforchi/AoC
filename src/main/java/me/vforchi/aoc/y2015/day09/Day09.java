package me.vforchi.aoc.y2015.day09;

import me.vforchi.aoc.Day;
import org.paukov.combinatorics3.Generator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class Day09 extends Day {

    private record Connection(String from, String to, int distance) {

        public boolean matches(String city1, String city2) {
            return (from.equals(city1) && to.equals(city2)) ||
                    (from.equals(city2) && to.equals(city1));
        }

        private static Pattern p = Pattern.compile("(.*) to (.*) = (.*)");
        public static Connection fromString(String line) {
            var m = p.matcher(line);
            if (m.matches()) {
                return new Connection(m.group(1), m.group(2), Integer.parseInt(m.group(3)));
            } else {
                throw new RuntimeException();
            }
        }
    }

    private List<List<String>> permutations;
    private List<Connection> connections;

    @Override
    public void setup(String path) {
        super.setup(path);
        connections = input.stream()
                .map(Connection::fromString)
                .toList();
        var cities = Stream.concat(
                        connections.stream().map(Connection::from),
                        connections.stream().map(Connection::to)
                )
                .distinct()
                .sorted()
                .collect(toCollection(ArrayList::new));
        permutations = Generator.permutation(cities).simple().stream().toList();
    }

    @Override
    public Object partOne() {
        return permutations.stream()
                .mapToInt(this::distance)
                .min()
                .orElseThrow();
    }

    private int distance(List<String> cities) {
        return IntStream.range(0, cities.size() - 1)
                .map(i -> connections.stream()
                        .filter(c -> c.matches(cities.get(i), cities.get(i + 1)))
                        .map(Connection::distance)
                        .findFirst().orElseThrow())
                .sum();
    }

    @Override
    public Object partTwo() {
        return permutations.stream()
                .mapToInt(this::distance)
                .max()
                .orElseThrow();
    }

}
