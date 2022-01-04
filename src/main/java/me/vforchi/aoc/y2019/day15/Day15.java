package me.vforchi.aoc.y2019.day15;

import lombok.*;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class Day15 extends Day {

    private static final int NORTH = 1, SOUTH = 2, WEST = 3, EAST = 4;
    private static final int WALL = 0, OXYGEN = 2;

    @With
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Pos {
        int x, y;
        public Pos move(int direction) {
            return switch (direction) {
                case NORTH -> withY(y - 1);
                case EAST -> withX(x + 1);
                case SOUTH -> withY(y + 1);
                case WEST -> withX(x - 1);
                default -> throw new RuntimeException();
            };
        }
    }

    @RequiredArgsConstructor
    private static class Droid {
        public static final Pos START = new Pos(0, 0);

        private final Intcode program;
        private Pos position = START;
        private Pos oxygenPosition;
        private Map<Pos, Integer> area = new HashMap<>() {{ put(START, 1); }};

        public void explore() {
            IntStream.rangeClosed(1, 4)
                    .filter(d -> !area.containsKey(position.move(d)))
                    .filter(d -> move(d) != WALL)
                    .forEach(d -> {
                        explore();
                        moveBack(d);
                    });
        }

        public int move(int direction) {
            int found = program.run(direction).get(0).intValue();
            var newPosition = position.move(direction);
            if (found != WALL) {
                position = newPosition;
            }
            if (found == OXYGEN) {
                oxygenPosition = newPosition;
            }
            area.put(newPosition, found);
            return found;
        }

        public int moveBack(int direction) {
            return switch (direction) {
                case NORTH -> move(SOUTH);
                case EAST -> move(WEST);
                case SOUTH -> move(NORTH);
                case WEST -> move(EAST);
                default -> throw new RuntimeException();
            };
        }
    }

    private Droid droid;

    @Override
    public void setup(String path) {
        super.setup(path);
        droid = new Droid(Intcode.fromText(input.get(0)));
        droid.explore();
    }

    @Override
    public Object partOne() {
        var distances = initDistances(Droid.START, droid.area);
        calculateDistances(Droid.START, droid.area, distances);
        return distances.get(droid.oxygenPosition);
    }

    @Override
    public Object partTwo() {
        var distances = initDistances(droid.oxygenPosition, droid.area);
        calculateDistances(droid.oxygenPosition, droid.area, distances);
        return distances.values().stream()
                .max(Integer::compareTo)
                .orElseThrow();
    }

    private Map<Pos, Integer> initDistances(Pos start, Map<Pos, Integer> area) {
        var distances = area.entrySet().stream()
                .filter(e -> e.getValue() != WALL)
                .collect(toMap(Map.Entry::getKey, e -> Integer.MAX_VALUE));
        distances.put(start, 0);
        return distances;
    }

    private void calculateDistances(Pos pos, Map<Pos, Integer> area, Map<Pos, Integer> distances) {
        for (var neighbor: findNeighbors(pos, area)) {
            int newDistance = distances.get(pos) + 1;
            if (newDistance < distances.get(neighbor)) {
                distances.put(neighbor, newDistance);
                calculateDistances(neighbor, area, distances);
            }
        }
    }

    private List<Pos> findNeighbors(Pos pos, Map<Pos, Integer> area) {
        return IntStream.rangeClosed(1, 4)
                .mapToObj(pos::move)
                .filter(p -> area.get(p) != WALL)
                .toList();
    }

}
