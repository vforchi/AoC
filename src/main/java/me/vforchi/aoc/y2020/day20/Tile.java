package me.vforchi.aoc.y2020.day20;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    char[][] pixels;
    String[] sides = new String[4];

    Map<Direction, Tile> connections = new HashMap<>();

    public Tile(Long id, char[][] pixels) {
        this.id = id;
        this.pixels = pixels;
        this.size = pixels.length;
        updateSides();
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
        return sides[idx];
    }

    public void updateSides() {
        sides[0] = new String(pixels[0]);
        var right = new StringBuilder();
        for (int i = 0; i < size; i++) {
            right.append(pixels[i][size - 1]);
        }
        sides[1] = right.toString();
        sides[2] = StringUtils.reverse(new String(pixels[size - 1]));
        var left = new StringBuilder();
        for (int i = 0; i < size; i++) {
            left.append(pixels[size - 1 - i][0]);
        }
        sides[3] = left.toString();
    }

    public void rotate() {
        char[][] newPixels = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newPixels[i][j] = pixels[size - 1 - j][i];
            }
        }
        pixels = newPixels;
        updateSides();
    }

    public void flip() {
        char[][] newPixels = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newPixels[i][j] = pixels[i][size - 1 - j];
            }
        }
        pixels = newPixels;
        updateSides();
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

    public char[][] stripBorders() {
        var stripped = new char[size - 2][size - 2];
        for (int i = 1; i < size - 1; i++) {
            System.arraycopy(pixels[i], 1, stripped[i - 1], 0, size - 2);
        }
        return stripped;
    }

    public static Tile fromLines(List<String> lines) {
        var id = Long.parseLong(lines.remove(0).substring(5, 9));
        var pixels = lines.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        return new Tile(id, pixels);
    }

    private static List<Boolean> stringToBooleanList(String line) {
        return line.chars()
                .mapToObj(c -> c != '.')
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        var buf = new StringBuilder("Id: " + id + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buf.append(pixels[i][j]);
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    public static String convertLine(List<Boolean> line) {
        return line.stream()
                .map(p -> p ? "#" : ".")
                .collect(Collectors.joining());
    }

}
