package me.vforchi.aoc.y2021.day17;

import me.vforchi.aoc.Day;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day17 extends Day {

    private Target target;

    private record Target(int x1, int x2, int y1, int y2) {
        public static Target fromText(String input) {
            var p = Pattern.compile("target area: x=(.+)\\.\\.(.+), y=(.+)\\.\\.(.+)");
            var m = p.matcher(input);
            if (m.matches()) {
                return new Target(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
            } else {
                throw new RuntimeException();
            }
        }

        public boolean contains(int x, int y) {
            return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
        }
    }

    @Override
    public void setup(String path) {
        super.setup(path);
        target = Target.fromText(input.get(0));
    }

    @Override
    public Object partOne() {
        return IntStream.rangeClosed(1, target.x2)
                .flatMap(vx -> IntStream.rangeClosed(target.y1, target.x2)
                        .boxed()
                        .map(vy -> play(vx, vy))
                        .filter(Optional::isPresent)
                        .mapToInt(Optional::get))
                .max()
                .orElseThrow();
    }

    @Override
    public Object partTwo() {
        return IntStream.rangeClosed(1, target.x2)
                .mapToLong(vx -> IntStream.rangeClosed(target.y1, target.x2)
                        .boxed()
                        .map(vy -> play(vx, vy))
                        .filter(Optional::isPresent)
                        .count())
                .sum();
    }

    private Optional<Integer> play(int vx, int vy) {
        int x = 0, y = 0;
        int maxy = 0;
        while (x <= target.x2 && y >= target.y1) {
            x += vx;
            y += vy;
            maxy = Math.max(maxy, y);
            vy -= 1;
            vx = updateVx(vx);
            if (target.contains(x, y)) {
                return Optional.of(maxy);
            }
        }
        return Optional.empty();
    }

    private int updateVx(int vx) {
        if (vx > 0) {
            return vx - 1;
        } else if (vx < 0) {
            return vx + 1;
        } else {
            return vx;
        }
    }

}
