package me.vforchi.aoc.y2019.day17;

import com.google.common.collect.Sets;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.With;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.countMatches;

public class Day17 extends Day {

    static final int STAY = -1, NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
    private VacuumRobot vacuumRobot;

    @With
    @ToString
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Pos {
        int x, y;

        public Pos move(int direction) {
            return move(direction, 1);
        }

        public Pos move(int direction, int amount) {
            return moveFunction(direction, amount).apply(this);
        }

        public static Function<Pos, Pos> moveFunction(int direction) {
            return moveFunction(direction, 1);
        }

        public static Function<Pos, Pos> moveFunction(int direction, int amount) {
            return switch (direction) {
                case NORTH -> pos -> pos.withY(pos.y - amount);
                case EAST -> pos -> pos.withX(pos.x + amount);
                case SOUTH -> pos -> pos.withY(pos.y + amount);
                case WEST -> pos -> pos.withX(pos.x - amount);
                default -> Function.identity();
            };
        }
    }

    private static class VacuumRobot {
        private final Intcode asciiProgram;
        private final Character[][] scaffolds;

        private Pos position;
        private int direction = NORTH;

        public VacuumRobot(Intcode asciiProgram) {
            this.asciiProgram = asciiProgram;
            asciiProgram.run();
            int columns = asciiProgram.getOutputs().indexOf((long) '\n');
            var output = asciiProgram.getOutputs().stream()
                    .map(o -> (char) o.intValue())
                    .filter(c -> c != '\n')
                    .toList();

            int robotIdx = output.indexOf('^');
            int robotY = robotIdx / columns;
            int robotX = robotIdx - robotY * columns;
            position = new Pos(robotX, robotY);
            scaffolds = ListUtils.partition(output, columns).stream()
                    .map(l -> l.toArray(Character[]::new))
                    .toArray(Character[][]::new);
        }

        public List<Pos> intersections() {
            return IntStream.range(1, scaffolds.length - 2)
                    .boxed()
                    .flatMap(c -> IntStream.range(1, scaffolds[0].length - 2)
                            .mapToObj(r -> new Pos(r, c)))
                    .filter(this::isIntersection)
                    .toList();
        }

        private boolean isIntersection(Pos pos) {
            return IntStream.rangeClosed(STAY, WEST)
                    .boxed()
                    .map(pos::move)
                    .allMatch(this::isScaffold);
        }

        public String visit() {
            return Stream.iterate(this::canMoveOnScaffold)
                    .collect(joining(","));
        }

        private Option<String> canMoveOnScaffold() {
            return tryMoveRight()
                    .orElse(this::tryMoveLeft);
        }

        private Option<String> tryMoveRight() {
            return tryMove((direction + 1) % 4)
                    .map(l -> "R," + l);
        }

        private Option<String> tryMoveLeft() {
            return tryMove((direction + 3) % 4)
                    .map(l -> "L," + l);
        }

        private Option<String> tryMove(int dir) {
            int length = moveOnScaffold(Pos.moveFunction(dir));
            if (length > 0) {
                direction = dir;
                position = position.move(direction, length);
                return Option.of(String.valueOf(length));
            } else {
                return Option.none();
            }
        }

        private int moveOnScaffold(Function<Pos, Pos> movement) {
            return Stream.iterate(position, movement)
                    .drop(1)
                    .zipWithIndex()
                    .find(t -> !isScaffold(t._1))
                    .map(Tuple2::_2)
                    .getOrElse(0);
        }

        private boolean isScaffold(Pos pos) {
            try {
                return scaffolds[pos.y][pos.x] == '#';
            } catch (Exception e) {
                return false;
            }
        }

        public Long clean(String path) {
            asciiProgram.reset();
            asciiProgram.setValue(0, 2);
            asciiProgram.runCommands(convertToCommands(path));
            return asciiProgram.getLastOutput();
        }

        private List<String> convertToCommands(String path) {
            var repeated = findAllRepeated(path);
            var parts = Sets.combinations(repeated, 3).stream()
                    .map(ArrayList::new)
                    .filter(abc -> path.matches(String.format("(%s|%s|%s|,)+", abc.get(0), abc.get(1), abc.get(2))))
                    .findFirst()
                    .orElseThrow();
            var mainRoutine = getMainRoutine(path, parts.get(0), parts.get(1), parts.get(2));
            return List.of(mainRoutine, parts.get(0), parts.get(1), parts.get(2), "n");
        }

        private String getMainRoutine(String path, String A, String B, String C) {
            var subRoutines = Map.of("A", A, "B", B, "C", C);
            List<String> commands = new ArrayList<>();
            int idx = 0;
            while (idx < path.length()) {
                for (var entry: subRoutines.entrySet()) {
                    if (path.startsWith(entry.getValue(), idx)) {
                        commands.add(entry.getKey());
                        idx += entry.getValue().length() + 1;
                    }
                }
            }
            return String.join(",", commands);
        }

        private Set<String> findAllRepeated(String path) {
            return IntStream.range(0, path.length()).boxed()
                    .filter(start -> path.charAt(start) == 'R' || path.charAt(start) == 'L')
                    .flatMap(start -> IntStream.rangeClosed(3, 20).boxed()
                            .filter(l -> start + l <= path.length())
                            .filter(l -> start + l == path.length() || path.charAt(start + l) == ',')
                            .map(l -> path.substring(start, start + l)))
                    .filter(s -> countMatches(s, ',') % 2 == 1)
                    .filter(s -> Character.isDigit(s.charAt(s.length() - 1)))
                    .filter(p -> countMatches(path, p) > 1)
                    .collect(toSet());
        }
    }

    @Override
    public void setup(String path) {
        super.setup(path);
        var asciiProgram = Intcode.fromText(input.get(0));
        vacuumRobot = new VacuumRobot(asciiProgram);
    }

    @Override
    public Object partOne() {
        return vacuumRobot.intersections().stream()
                .mapToInt(t -> t.x * t.y)
                .sum();
    }

    @Override
    public Object partTwo() {
        var path = vacuumRobot.visit();
        return vacuumRobot.clean(path);
    }

}
