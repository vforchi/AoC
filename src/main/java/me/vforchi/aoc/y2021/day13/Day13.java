package me.vforchi.aoc.y2021.day13;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2020.Utils;

import java.util.List;

public class Day13 extends Day {

    private List<Tuple2<Integer, Integer>> sheet;
    private List<Tuple2<String, Integer>> folds;

    @Override
    public void setup(String path) {
        super.setup(path);
        var inputs = Utils.partitionByEmptyLine(input).toList();
        sheet = inputs.get(0).stream()
                .takeWhile(l -> !l.isBlank())
                .map(l -> l.split(","))
                .map(t -> Tuple.of(Integer.parseInt(t[0]), Integer.parseInt(t[1])))
                .toList();
        folds = inputs.get(1).stream()
                .map(l -> l.split("[ =]"))
                .map(t -> Tuple.of(t[2], Integer.parseInt(t[3])))
                .toList();
    }

    @Override
    public Object partOne() {
        return fold(sheet, folds.get(0)).size();
    }

    @Override
    public Object partTwo() {
        folds.forEach(f -> sheet = fold(sheet, f));
        return print(sheet);
    }

    private List<Tuple2<Integer, Integer>> fold(List<Tuple2<Integer, Integer>> sheet, Tuple2<String, Integer> fold) {
        return sheet.stream()
                .map(t -> {
                    if (fold._1.equals("y")) {
                        return t.map2(c -> foldCoordinate(c, fold._2));
                    } else {
                        return t.map1(c -> foldCoordinate(c, fold._2));
                    }
                })
                .distinct()
                .toList();
    }

    private Integer foldCoordinate(Integer c, Integer fold) {
        return (c > fold) ? (2 * fold - c) : c;
    }

    private String print(List<Tuple2<Integer, Integer>> folded) {
        var out = new StringBuilder("\n");
        var width = folded.stream()
                .mapToInt(Tuple2::_1)
                .max()
                .orElseThrow();
        var height = folded.stream()
                .mapToInt(Tuple2::_2)
                .max()
                .orElseThrow();
        for (int y = 0; y <= height; y++) {
            for (int x = 0; x <= width; x++) {
                if (folded.contains(Tuple.of(x, y))) {
                    out.append("#");
                } else {
                    out.append(" ");
                }
            }
            out.append("\n");
        }
        return out.toString();
    }
}
