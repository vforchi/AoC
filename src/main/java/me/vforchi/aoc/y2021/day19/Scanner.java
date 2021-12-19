package me.vforchi.aoc.y2021.day19;

import com.google.common.collect.Sets;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import lombok.Getter;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Scanner {
    private final String name;
    @Getter private final List<Beacon> beacons;
    private final List<Tuple3<Beacon, Beacon, Integer>> distances;

    public Scanner(String name, List<Beacon> beacons) {
        this.name = name;
        this.beacons = beacons;
        this.distances = Sets.combinations(new HashSet<>(beacons), 2).stream()
                .map(ArrayList::new)
                .map(l -> Tuple.of(l.get(0), l.get(1)))
                .map(t -> t.append(t._1.distance(t._2)))
                .toList();
    }

    public Optional<Function<Beacon, Beacon>> match(Scanner scanner) {
        var commonDistances = ListUtils.intersection(
                distances.stream().map(Tuple3::_3).toList(),
                scanner.distances.stream().map(Tuple3::_3).toList()
        );

        if (commonDistances.size() >= (12 * 11 / 6)) {
            var beacons1 = this.findCouples(commonDistances.get(0));
            var beacons2 = scanner.findCouples(commonDistances.get(0));
            for (var up : Beacon.ups) {
                for (var rot : Beacon.rots) {
                    var transformationOneToOne = beacons2._1.transformationTo(beacons1._1, up.andThen(rot));
                    if (transformationOneToOne.apply(beacons2._2).equals(beacons1._2)) {
                        return Optional.of(transformationOneToOne);
                    }
                    var transformationOneToTwo = beacons2._1.transformationTo(beacons1._2, up.andThen(rot));
                    if (transformationOneToTwo.apply(beacons2._2).equals(beacons1._1)) {
                        return Optional.of(transformationOneToTwo);
                    }
                }
            }
        }
        return Optional.empty();
    }

    private Tuple2<Beacon, Beacon> findCouples(Integer distance) {
        return distances.stream()
                .filter(t -> Objects.equals(t._3, distance))
                .map(t -> Tuple.of(t._1, t._2))
                .findFirst()
                .orElseThrow();
    }

    public Set<Beacon> transpose(Function<Beacon, Beacon> transformation) {
        return beacons.stream()
                .map(transformation)
                .collect(Collectors.toSet());
    }

    public static Scanner fromText(List<String> text) {
        var name = text.get(0);
        var beacons = text.stream()
                .skip(1)
                .map(Beacon::fromText)
                .toList();
        return new Scanner(name, beacons);
    }
}
