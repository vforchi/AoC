package me.vforchi.aoc.y2021.day22;

import me.vforchi.aoc.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

public class Day22 extends Day {

    private List<Cuboid> innerCuboids;
    private List<Cuboid> cuboids;

    private record Cuboid(boolean on, int x1, int x2, int y1, int y2, int z1, int z2) {

        public Optional<Cuboid> intersection(Cuboid other) {
            if (this.intersect(other)) {
                var x = IntStream.of(x1, x2, other.x1, other.x2)
                        .sorted()
                        .toArray();
                var y = IntStream.of(y1, y2, other.y1, other.y2)
                        .sorted()
                        .toArray();
                var z = IntStream.of(z1, z2, other.z1, other.z2)
                        .sorted()
                        .toArray();
                return Optional.of(new Cuboid(!on, x[1], x[2], y[1], y[2], z[1], z[2]));
            } else {
                return Optional.empty();
            }
        }

        public boolean intersect(Cuboid other) {
            return intersect(x1, x2, other.x1, other.x2) &&
                    intersect(y1, y2, other.y1, other.y2) &&
                    intersect(z1, z2, other.z1, other.z2) ;
        }

        public boolean intersect(int u1, int u2, int v1, int v2) {
            return !(v1 > u2 || u1 > v2);
        }

        public long volume() {
            long vol = ((long) (Math.abs(x2 - x1) + 1)) * (Math.abs(y2 - y1) + 1) * (Math.abs(z2 - z1) + 1);
            return on ? vol : -vol;
        }

        public static Cuboid fromText(String text) {
            var t = text.split("[.= ,]");
            return new Cuboid(
                    Objects.equals(t[0], "on"),
                    Integer.parseInt(t[2]),
                    Integer.parseInt(t[4]),
                    Integer.parseInt(t[6]),
                    Integer.parseInt(t[8]),
                    Integer.parseInt(t[10]),
                    Integer.parseInt(t[12])
            );
        }
    }

    @Override
    public void setup(String path) {
        super.setup(path);
        cuboids = input.stream()
                .map(Cuboid::fromText)
                .toList();
        innerCuboids = cuboids.stream()
                .filter(s -> Math.abs(s.x1) < 51)
                .toList();
    }

    @Override
    public Object partOne() {
        return reboot(innerCuboids);
    }

    @Override
    public Object partTwo() {
        return reboot(cuboids);
    }

    long reboot(List<Cuboid> cuboids) {
        List<Cuboid> volumes = new ArrayList<>();
        for (var cube: cuboids) {
            var intersection = volumes.stream()
                    .map(p -> p.intersection(cube))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            volumes.addAll(intersection);
            if (cube.on) {
                volumes.add(cube);
            }
        }

        return volumes.stream()
                .mapToLong(Cuboid::volume)
                .sum();
    }

}
