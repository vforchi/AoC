package me.vforchi.aoc.y2020.day20;

import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2020.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static me.vforchi.aoc.y2020.day20.Tile.Direction.BOTTOM;
import static me.vforchi.aoc.y2020.day20.Tile.Direction.RIGHT;

public class Day20 extends Day {

    /*
     *                   #
     * #    ##    ##    ###
     *  #  #  #  #  #  #
     */
    private static final List<List<Integer>> seaMonster = List.of(
            List.of(1, 0), List.of(2, 1),
            List.of(2, 4), List.of(1, 5), List.of(1, 6), List.of(2, 7),
            List.of(2, 10), List.of(1, 11), List.of(1, 12), List.of(2, 13),
            List.of(2, 16), List.of(1, 17), List.of(1, 18), List.of(1, 19), List.of(0, 18)
    );

    List<Tile> tiles = new ArrayList<>();

    @Override
    public Object partOne() {
        return tiles.stream()
                .filter(Tile::isCorner)
                .mapToLong(t -> t.id)
                .reduce((a,b) -> a*b)
                .orElseThrow();
    }

    @Override
    public Object partTwo() {
        var image = combineTiles(tiles);

        for (int i = 0; i < 7; i++) {
            if (removeSeaMonsters(image)) {
                break;
            }
            image.rotate();
            if (i == 3) {
                image.flip();
            }
        }

        return image.pixels.stream()
                .flatMap(l -> l.stream().filter(p -> p))
                .count();
    }

    private boolean removeSeaMonsters(Tile image) {
        var size = image.pixels.size();
        var found = true;
        for (int i = 0; i < size - 2; i++) {
            for (int j = 0; j < size - seaMonster.size() - 1; j++) {
                found &= findAndRemoveSeaMonster(image, i, j);
            }
        }
        return found;
    }

    private boolean findAndRemoveSeaMonster(Tile image, int row, int col) {
        if (findSeaMonster(image, row, col)) {
            removeSeaMonster(image, row, col);
            return true;
        } else {
            return false;
        }
    }

    private boolean findSeaMonster(Tile image, int row, int col) {
        return seaMonster.stream()
                .allMatch(p -> image.pixels.get(row+p.get(0)).get(col+p.get(1)));
    }

    private void removeSeaMonster(Tile image, int row, int col) {
        seaMonster.forEach(p -> image.pixels.get(row+p.get(0)).set(col+p.get(1), false));
    }

    private Tile combineTiles(List<Tile> tiles) {
        List<List<Boolean>> combined = new ArrayList<>();

        var left = tiles.stream()
                .filter(Tile::isTopLeftCorner)
                .findFirst().orElseThrow();

        while (left != null) {
            var rows = left.stripBorders();
            var col = left;
            while (col.connections.containsKey(RIGHT)) {
                col = col.connections.get(RIGHT);
                var toAdd = col.stripBorders();
                for (int i = 0; i < rows.size(); i++) {
                    rows.get(i).addAll(toAdd.get(i));
                }
            }
            combined.addAll(rows);
            left = left.connections.get(BOTTOM);
        }
        return new Tile(0L, combined);
    }

    @Override
    public void setup(String path) {
        super.setup(path);

        this.tiles = Utils.partitionByEmptyLine(this.input)
                .map(Tile::fromLines)
                .collect(Collectors.toList());

        var toProcess = Set.of(tiles.get(0));
        while (toProcess.size() > 0) {
            toProcess = findAndPlaceNeighbours(toProcess, this.tiles);
        }
    }

    private Set<Tile> findAndPlaceNeighbours(Set<Tile> tilesToPlace, List<Tile> allTiles) {
        return tilesToPlace.stream()
                .flatMap(t -> findAndPlaceNeighbours(t, allTiles))
                .collect(Collectors.toSet());
    }

    private Stream<Tile> findAndPlaceNeighbours(Tile tile, List<Tile> allTiles) {
        return IntStream.range(0, 4)
                .boxed()
                .map(side -> findAndPlaceNeighbour(tile, side, allTiles))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private Optional<Tile> findAndPlaceNeighbour(Tile tile, int side, List<Tile> allTiles) {
        return allTiles.stream()
                .map(t -> tile.placeTile(side, t))
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());
    }

}