package me.vforchi.aoc.y2020.day24;

import me.vforchi.aoc.Day;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day24 extends Day {

    private Set<HexTile> blackTiles;

    @Override
    public Object partOne() {
        return this.blackTiles.size();
    }

    @Override
    public Object partTwo() {
        IntStream.range(0, 100).forEach(i -> flipTiles(blackTiles));
        return blackTiles.size();
    }

    private void flipTiles(Set<HexTile> blackTiles) {
        var newBlackTiles = getWhiteNeighbours(blackTiles).stream()
                .filter(t -> flipsBlack(t, blackTiles))
                .collect(Collectors.toSet());

        var newWhiteTiles = blackTiles.stream()
                .filter(t -> flipsWhite(t, blackTiles))
                .collect(Collectors.toSet());

        blackTiles.removeAll(newWhiteTiles);
        blackTiles.addAll(newBlackTiles);
    }

    private Set<HexTile> getWhiteNeighbours(Set<HexTile> blackTiles) {
        return blackTiles.stream()
                .flatMap(HexTile::getNeighbours)
                .filter(t -> !blackTiles.contains(t))
                .collect(Collectors.toSet());
    }

    private boolean flipsWhite(HexTile blackTile, Set<HexTile> blackTiles) {
        var blackNeighbours = countBlackNeighbours(blackTile, blackTiles);
        return blackNeighbours == 0 || blackNeighbours > 2;
    }

    private boolean flipsBlack(HexTile whiteTile, Set<HexTile> blackTiles) {
        return countBlackNeighbours(whiteTile, blackTiles) == 2;
    }

    private long countBlackNeighbours(HexTile tile, Set<HexTile> blackTiles) {
        return tile.getNeighbours()
                .filter(blackTiles::contains)
                .count();
    }

    @Override
    public void setup(String path) {
        super.setup(path);

        blackTiles = input.stream()
                .map(this::toMovementsList)
                .map(this::sumMovements)
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                ))
                .entrySet().stream()
                .filter(e ->  e.getValue() % 2 == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private List<HexTile> toMovementsList(String line) {
        return Arrays.stream(line.split("(?<=(e|w))"))
                .map(HexTile::fromString)
                .collect(Collectors.toList());
    }

    private HexTile sumMovements(List<HexTile> movements) {
        return movements.stream()
                .reduce(HexTile::sum)
                .orElseThrow();
    }

}