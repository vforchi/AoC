package me.vforchi.aoc.y2021.day12;

import lombok.EqualsAndHashCode;
import me.vforchi.aoc.Day;
import org.apache.commons.collections4.SetUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

// TODO: remove regex matching
public class Day12 extends Day {

    @EqualsAndHashCode
    private static class Cave {
        public static final Cave START = new Cave("start");
        public static final Cave END = new Cave("end");
        public static final List<Cave> START_ROUTE = List.of(START);

        public String name;
        public boolean isLarge;

        public Cave(String name) {
            this.name = name;
            this.isLarge = name.matches("[A-Z]+");
        }
    }

    private Map<Cave, Set<Cave>> connections;

    @Override
    public void setup(String path) {
        super.setup(path);
        connections = input.stream()
                .map(l -> l.split("-"))
                .filter(l -> !Objects.equals(l[1], Cave.START.name))
                .filter(l -> !Objects.equals(l[0], Cave.END.name))
                .collect(groupingBy(l -> new Cave(l[0]), mapping(l -> new Cave(l[1]), toSet())));

        input.stream()
                .map(l -> l.split("-"))
                .filter(l -> !Objects.equals(l[1], Cave.END.name))
                .filter(l -> !Objects.equals(l[0], Cave.START.name))
                .forEach(l -> connections.merge(new Cave(l[1]), Set.of(new Cave(l[0])), SetUtils::union));
    }

    @Override
    public Object partOne() {
        return allRoutes(Cave.START_ROUTE, new ArrayList<>(), (routes, c) -> !routes.contains(c)).size();
    }

    @Override
    public Object partTwo() {
        return allRoutes(Cave.START_ROUTE, new ArrayList<>(), this::canAdd).size();
    }

    private List<List<Cave>> allRoutes(List<Cave> route, List<List<Cave>> routes, BiFunction<List<Cave>, Cave, Boolean> canAddSmallCaveToRoute) {
        var last = route.get(route.size() - 1);
        if (Objects.equals(last, Cave.END)) {
            routes.add(route);
        } else {
            connections.get(last).stream()
                    .filter(c -> c.isLarge || canAddSmallCaveToRoute.apply(route, c))
                    .map(c -> Stream.concat(route.stream(), Stream.of(c)).toList())
                    .forEach(r -> allRoutes(r, routes, canAddSmallCaveToRoute));
        }
        return routes;
    }

    private boolean canAdd(List<Cave> route, Cave cave) {
        return !route.contains(cave) || !route.stream()
                .filter(c -> !c.isLarge)
                .collect(groupingBy(identity(), counting()))
                .containsValue(2L);
    }

}
