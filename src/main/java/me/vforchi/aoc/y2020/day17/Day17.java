package me.vforchi.aoc.y2020.day17;

import io.vavr.Tuple2;
import me.vforchi.aoc.Day;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vavr.collection.Stream.ofAll;

public class Day17 extends Day {

    @Override
    public Object partOne() {
        return solve(false);
    }

    @Override
    public Object partTwo() {
        return solve(true);
    }

    private Object solve(boolean fourDim) {
        return Stream.iterate(getActiveCubes(input, fourDim), this::nextStep)
                .skip(6)
                .findFirst()
                .map(Collection::size)
                .orElseThrow();
    }

    private Collection<Cube> nextStep(Collection<Cube> activeCubes) {
        var gridDim = GridDimensions.gridFromCubes(activeCubes);
        Collection<Cube> newActiveCubes = new ArrayList<>();
        for (int x = gridDim.minx; x < gridDim.maxx; x++) {
            for (int y = gridDim.miny; y < gridDim.maxy; y++) {
                for (int z = gridDim.minz; z < gridDim.maxz; z++) {
                    if (gridDim.minw == null) {
                        var cube = new Cube(x, y, z, null);
                        if (cube.checkCubeNewStatus(activeCubes)) {
                            newActiveCubes.add(cube);
                        }
                    } else {
                        for (int w = gridDim.minw; w < gridDim.maxw; w++) {
                            var cube = new Cube(x, y, z, w);
                            if (cube.checkCubeNewStatus(activeCubes)) {
                                newActiveCubes.add(cube);
                            }
                        }
                    }
                }
            }
        }
        return newActiveCubes;
    }

    private Collection<Cube> getActiveCubes(Collection<String> input, boolean fourDim) {
        return ofAll(input)
                .zipWithIndex()
                .flatMap(tuple -> makeCubes(tuple._1, tuple._2, fourDim))
                .collect(Collectors.toList());
    }

    private io.vavr.collection.Stream<Cube> makeCubes(String line, Integer y, boolean fourDim) {
        return ofAll(line.toCharArray())
                .zipWithIndex()
                .filter(tuple -> tuple._1 == '#')
                .map(Tuple2::_2)
                .map(x -> new Cube(x, y, 0, fourDim ? 0 : null));
    }

}