package me.vforchi.aoc.y2019.day03;

import com.google.common.collect.Lists;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@AllArgsConstructor
public class Wire {

    List<Pos> positions;

    public record Pos(int x, int y) {
        public Pos move(String direction) {
            var amount = Integer.parseInt(direction.substring(1));
            return switch (direction.charAt(0)) {
                case 'R' -> new Pos(x + amount, y);
                case 'L' -> new Pos(x - amount, y);
                case 'U' -> new Pos(x, y + amount);
                case 'D' -> new Pos(x, y - amount);
                default -> throw new RuntimeException();
            };
        }

        public int manhattan() {
            return Math.abs(x) + Math.abs(y);
        }
    }

    public List<Pos> intersect(Wire other) {
        return ListUtils.union(
                intersect(this.horizontal(), other.vertical()),
                intersect(other.horizontal(), this.vertical())
        );
    }

    private List<Pos> intersect(List<Tuple2<Pos, Pos>> horizontal, List<Tuple2<Pos, Pos>> vertical) {
        return Lists.cartesianProduct(horizontal, vertical).stream()
                .map(l -> intersect(l.get(0), l.get(1)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private static Optional<Pos> intersect(Tuple2<Pos, Pos> hor, Tuple2<Pos, Pos> ver) {
        if (ver._1.x > Math.min(hor._1.x, hor._2.x) &&
                ver._1.x < Math.max(hor._1.x, hor._2.x) &&
                hor._1.y > Math.min(ver._1.y, ver._2.y) &&
                hor._1.y < Math.max(ver._1.y, ver._2.y)) {
            return Optional.of(new Pos(ver._1.x, hor._1.y));
        } else {
            return Optional.empty();
        }
    }

    private List<Tuple2<Pos, Pos>> horizontal() {
        int start = (positions.get(1).x == 0) ? 1 : 0;
        return IntStream.range(0, (positions.size() - 1) / 2)
                .mapToObj(i -> Tuple.of(positions.get(start + i * 2), positions.get(start + i * 2 + 1)))
                .toList();
    }

    private List<Tuple2<Pos, Pos>> vertical() {
        int start = (positions.get(1).x == 0) ? 0 : 1;
        return IntStream.range(0, (positions.size() - 1) / 2)
                .mapToObj(i -> Tuple.of(positions.get(start + i * 2), positions.get(start + i * 2 + 1)))
                .toList();
    }

    public int distance(Pos pos) {
        int distance = 0;
        int i = 0;
        while (!contains(pos, positions.get(i), positions.get(i + 1))) {
            distance += distance(positions.get(i), positions.get(i + 1));
            i++;
        }
        distance += distance(pos, positions.get(i));
        return distance;
    }

    private boolean contains(Pos pos, Pos pos1, Pos pos2) {
        return pos.x >= Math.min(pos1.x, pos2.x) &&
                pos.x <= Math.max(pos1.x, pos2.x) &&
                pos.y >= Math.min(pos1.y, pos2.y) &&
                pos.y <= Math.max(pos1.y, pos2.y);
    }

    private int distance(Pos pos1, Pos pos2) {
        return Math.max(Math.abs(pos2.x - pos1.x), Math.abs(pos2.y - pos1.y));
    }

    public static Wire fromText(String directions) {
        var positions = new ArrayList<Pos>();
        var pos = new Pos(0, 0);
        positions.add(pos);
        for (var direction: directions.split(",")) {
            pos = pos.move(direction);
            positions.add(pos);
        }
        return new Wire(positions);
    }
}
