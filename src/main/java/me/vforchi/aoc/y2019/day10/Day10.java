package me.vforchi.aoc.y2019.day10;

import me.vforchi.aoc.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

public class Day10 extends Day {

    private record Pos(int x, int y) {
        public double angle(Pos other) {
            var angle = 90 + Math.atan2(other.y - y, other.x - x) * 180 / Math.PI;
            return (angle < 0) ? (angle + 360) : angle;
        }

        public long visible(List<Pos> image) {
            return image.stream()
                    .map(this::angle)
                    .distinct()
                    .count();
        }

        public Pos closest(List<Pos> asteroids) {
            return asteroids.stream()
                    .min(Comparator.comparing(this::distance))
                    .orElseThrow();
        }

        private double distance(Pos other) {
            return (other.x - x) * (other.x - x) + (other.y - y) * (other.y - y);
        }
    }

    private List<Pos> image;
    private Pos station;

    @Override
    public void setup(String path) {
        super.setup(path);
        image = IntStream.range(0, input.size()).boxed()
                .flatMap(y -> IntStream.range(0, input.get(y).length())
                        .filter(x -> input.get(y).charAt(x) == '#')
                        .mapToObj(x -> new Pos(x, y)))
                .toList();
        station = image.stream()
                .max(Comparator.comparing(p -> p.visible(image)))
                .orElseThrow();
    }

    @Override
    public Object partOne() {
        return station.visible(image);
    }

    @Override
    public Object partTwo() {
        List<Pos> destroyed = new ArrayList<>();
        while (destroyed.size() < 200) {
            destroyed.addAll(destroy(station));
        }
       var a200 = destroyed.get(199);
        return 100 * a200.x + a200.y;
    }

    private List<Pos> destroy(Pos station) {
        return image.stream()
                .collect(groupingBy(station::angle))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .map(station::closest)
                .toList();
    }

}
