package me.vforchi.aoc.y2021.day19;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Beacon {
    private final int x, y, z;

    public static List<Function<Beacon, Beacon>> ups = List.of(
            (Beacon b) -> new Beacon(b.x, b.y, b.z),
            (Beacon b) -> new Beacon(b.y, -b.x, b.z),
            (Beacon b) -> new Beacon(-b.x, -b.y, b.z),
            (Beacon b) -> new Beacon(-b.y, b.x, b.z),
            (Beacon b) -> new Beacon(b.z, b.x, b.y),
            (Beacon b) -> new Beacon(-b.z, b.y, b.x)
    );
    
    public static List<Function<Beacon, Beacon>> rots = List.of(
            (Beacon b) -> new Beacon(b.x, b.y, b.z),
            (Beacon b) -> new Beacon(b.x, b.z, -b.y),
            (Beacon b) -> new Beacon(b.x, -b.y, -b.z),
            (Beacon b) -> new Beacon(b.x, -b.z, b.y)
    );
    
    public int distance(Beacon beacon) {
        return (int) (Math.pow(x - beacon.x, 2) + Math.pow(y - beacon.y, 2) + Math.pow(z - beacon.z, 2));
    }

    public Function<Beacon, Beacon> transformationTo(Beacon beacon, Function<Beacon, Beacon> orient) {
        var flipped = orient.apply(this);
        var translation = Beacon.translation(flipped, beacon);
        return orient.andThen(translation);
    }

    public static Function<Beacon, Beacon> translation(Beacon p1, Beacon p2) {
        return (Beacon p) -> new Beacon(p.x + p2.x - p1.x, p.y + p2.y - p1.y, p.z + p2.z - p1.z);
    }

    public int manhattan(Beacon pos) {
        return Math.abs(x - pos.x) + Math.abs(y - pos.y) + Math.abs(z - pos.z);
    }

    @Override
    public String toString() {
        return String.format("%d,%d,%d", x, y, z);
    }

    public static Beacon fromText(String line) {
        var t = line.split(",");
        return new Beacon(
                Integer.parseInt(t[0]),
                Integer.parseInt(t[1]),
                Integer.parseInt(t[2])
        );
    }
}
