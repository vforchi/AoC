package me.vforchi.aoc.y2021.day19;

import com.google.common.collect.Lists;
import io.vavr.Tuple;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2020.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Day19 extends Day {

    private Map<Scanner, Function<Beacon, Beacon>> scannersAndTransformation = new HashMap<>();

    @Override
    public void setup(String path) {
        super.setup(path);
        var scanners = Utils.partitionByEmptyLine(input)
                .map(Scanner::fromText)
                .toList();
        align(scanners);
    }

    private void align(List<Scanner> scanners) {
        scannersAndTransformation.put(scanners.get(0), Function.identity());
        while (scannersAndTransformation.size() != scanners.size()) {
            Lists.cartesianProduct(scanners, scanners).stream()
                    .map(l -> Tuple.of(l.get(0), l.get(1)))
                    .filter(t -> t._1 != t._2)
                    .filter(t -> scannersAndTransformation.containsKey(t._1))
                    .map(t -> t.append(t._1.match(t._2)))
                    .filter(t -> t._3.isPresent())
                    .map(t -> t.map3(Optional::get))
                    .forEach(t -> scannersAndTransformation.put(t._2, t._3.andThen(scannersAndTransformation.get(t._1))));
        }
    }

    @Override
    public Object partOne() {
        return scannersAndTransformation.entrySet().stream()
                .flatMap(e -> e.getKey().transpose(e.getValue()).stream())
                .distinct()
                .count();
    }

    @Override
    public Object partTwo() {
        var centers = scannersAndTransformation.values().stream()
                .map(t -> t.apply(new Beacon(0, 0, 0)))
                .toList();
        return Lists.cartesianProduct(centers, centers).stream()
                .mapToInt(l -> l.get(0).manhattan(l.get(1)))
                .max().orElseThrow();
    }

}
