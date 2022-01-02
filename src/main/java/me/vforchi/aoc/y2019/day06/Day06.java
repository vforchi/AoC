package me.vforchi.aoc.y2019.day06;

import me.vforchi.aoc.Day;
import org.apache.commons.collections4.ListUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day06 extends Day {

    private Map<String, List<String>> orbits;
    private List<String> objects;

    @Override
    public void setup(String path) {
        super.setup(path);
        orbits = input.stream()
                .map(l -> l.split("\\)"))
                .collect(Collectors.toMap(t -> t[0], t -> List.of(t[1]), ListUtils::union));
        objects = orbits.values().stream()
                .flatMap(Collection::stream)
                .distinct()
                .filter(o -> !o.equals("COM"))
                .toList();
    }

    @Override
    public Object partOne() {
       return objects.stream()
               .flatMap(this::orbits)
               .count();
    }

    private Stream<String> orbits(String object) {
        return Stream.iterate(directOrbit(object), Optional::isPresent, o -> o.flatMap(this::directOrbit))
                .map(Optional::get);
    }

    private Optional<String> directOrbit(String object) {
        return orbits.entrySet().stream()
                .filter(e -> e.getValue().contains(object))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Override
    public Object partTwo() {
        var myOrbits = orbits("YOU").toList();
        var santaOrbits = orbits("SAN").toList();
        var common = ListUtils.intersection(myOrbits, santaOrbits).get(0);
        return myOrbits.indexOf(common) + santaOrbits.indexOf(common);
    }

}
