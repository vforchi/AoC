package me.vforchi.aoc.y2020.day20;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vavr.collection.Stream.ofAll;

public class Tile {

    public enum Direction { TOP, RIGHT, BOTTOM, LEFT;
        public Direction opposite() {
            switch (this) {
                case TOP -> { return BOTTOM; }
                case RIGHT -> { return LEFT; }
                case BOTTOM -> { return TOP; }
                case LEFT -> { return RIGHT; }
            }
            throw new RuntimeException();
        }
    }

    public final int size;

    Long id;
    List<List<Boolean>> pixels;

    Map<Direction, Tile> connections = new HashMap<>();

    public Tile(Long id, List<List<Boolean>> pixels) {
        this.id = id;
        this.pixels = pixels;
        this.size = pixels.size();
    }

    public Optional<Tile> placeTile(Integer side, Tile tile) {
        if (tile.id.equals(this.id)) {
            return Optional.empty();
        } else if (connections.containsValue(tile) ) {
            return Optional.empty();
        } else if (tile.connections.size() == 4) {
            return Optional.empty();
        }

        var b1 = StringUtils.reverse(getSide(side));
        var b2Idx = Direction.values()[side].opposite().ordinal();

        for (int i = 0; i < 8; i++) {
            var b2 = tile.getSide(b2Idx);
            if (b2.equals(b1)) {
                this.setConnection(side, tile);
                return Optional.of(tile);
            }
            if (tile.connections.size() > 0) {
                return Optional.empty();
            }
            tile.rotate();
            if (i == 3) {
                tile.flip();
            }
        }
        return Optional.empty();
    }

    public String getSide(int idx) {
        Stream<Boolean> side;
        switch (idx) {
            case 0 -> side = pixels.get(0).stream();
            case 1 -> side = pixels.stream()
                    .map(l -> l.get(size - 1));
            case 2 -> side = ofAll(pixels.get(size - 1))
                    .reverse()
                    .toJavaStream();
            case 3 -> side = ofAll(pixels)
                    .reverse()
                    .map(l -> l.get(0))
                    .toJavaStream();
            default -> side = Stream.of(false);
        }
        return side.map(p -> p ? "#" : ".")
                .collect(Collectors.joining());
    }

    public void rotate() {
        transpose();
        flip();
    }

    public void transpose() {
        List<List<Boolean>> newPixels = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            var newRow = new ArrayList<Boolean>();
            for (int j = 0; j < size; j++) {
                newRow.add(j, pixels.get(j).get(i));
            }
            newPixels.add(newRow);
        }
        pixels = newPixels;
    }

    public void flip() {
        pixels.forEach(Collections::reverse);
    }

    private void setConnection(Integer side, Tile tile) {
        var direction = Direction.values()[side];
        this.connections.put(direction, tile);
        tile.connections.put(direction.opposite(), this);
    }

    public boolean isCorner() {
        return connections.values().size() == 2;
    }

    public boolean isTopLeftCorner() {
        return SetUtils.isEqualSet(connections.keySet(), Set.of(Direction.RIGHT, Direction.BOTTOM));
    }

    public List<List<Boolean>> stripBorders() {
         return pixels.subList(1, size - 1).stream()
                .map(l -> l.subList(1, size - 1))
                .collect(Collectors.toList());
    }

    public static Tile fromLines(List<String> lines) {
        var id = Long.parseLong(lines.remove(0).substring(5, 9));
        var pixels = lines.stream()
                .map(Tile::stringToBooleanList)
                .collect(Collectors.toList());
        return new Tile(id, pixels);
    }

    private static List<Boolean> stringToBooleanList(String line) {
        return line.chars()
                .mapToObj(c -> c != '.')
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        var buf = "Id: " + id + "\n";
        var body = pixels.stream()
                .map(Tile::convertLine)
                .collect(Collectors.joining("\n"));
        return buf + body;
    }

    public static String convertLine(List<Boolean> line) {
        return line.stream()
                .map(p -> p ? "#" : ".")
                .collect(Collectors.joining());
    }

}
