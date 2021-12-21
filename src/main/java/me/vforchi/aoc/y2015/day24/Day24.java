package me.vforchi.aoc.y2015.day24;

import com.google.common.collect.Sets;
import me.vforchi.aoc.Day;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day24 extends Day {

    private Set<Long> packages;
    private Long weight;

    @Override
    public void setup(String path) {
        super.setup(path);
        packages = input.stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());
        weight = packages.stream()
                .reduce(Long::sum)
                .orElseThrow();
    }

    @Override
    public Object partOne() {
        return minimizeQuantumEntanglement(3);
    }

    @Override
    public Object partTwo() {
        return minimizeQuantumEntanglement(4);
    }

    private Long minimizeQuantumEntanglement(int numGroups) {
        return IntStream.range(4, 10)
                .boxed()
                .map(n -> findConfigurations(n, numGroups))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst().orElseThrow()
                .stream()
                .map(l -> l.stream()
                        .reduce((a, b) -> a * b)
                        .orElseThrow())
                .sorted()
                .findFirst()
                .orElseThrow();
    }

    public Optional<List<Set<Long>>> findConfigurations(int n, int numGroups) {
        var groups = Sets.combinations(packages, n).stream()
                .filter(g -> sumMatches(g, numGroups))
                .toList();
        if (groups.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(groups);
        }
    }

    private boolean sumMatches(Set<Long> group, int numGroups) {
        return group.stream()
                .reduce(Long::sum)
                .filter(w -> w == weight / numGroups)
                .isPresent();
    }

}
