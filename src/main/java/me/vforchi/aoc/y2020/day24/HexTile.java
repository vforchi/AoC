package me.vforchi.aoc.y2020.day24;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.stream.Stream;

@AllArgsConstructor
@EqualsAndHashCode
public class HexTile {

    private final double east;
    private final double north;

    public Stream<HexTile> getNeighbours() {
        return Stream.of(
                this.add(new HexTile(1, 0)),
                this.add(new HexTile(-1, 0)),
                this.add(new HexTile(0.5, 0.5)),
                this.add(new HexTile(0.5, -0.5)),
                this.add(new HexTile(-0.5, 0.5)),
                this.add(new HexTile(-0.5, -0.5))
        );
    }

    public HexTile add(HexTile other) {
        return new HexTile(this.east + other.east, this.north + other.north);
    }

    public static HexTile fromString(String movement) {
        double east = 0.0, north = 0.0;

        if (movement.contains("e")) {
            east = 1.0 / movement.length();
        } else if (movement.contains("w")) {
            east = -1.0 / movement.length();
        }

        if (movement.contains("n")) {
            north = 0.5;
        } else if (movement.contains("s")) {
            north = -0.5;
        }

        return new HexTile(east, north);
    }

    public static HexTile sum(HexTile tile1, HexTile tile2) {
        return tile1.add(tile2);
    }

}
