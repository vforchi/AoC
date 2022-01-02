package me.vforchi.aoc.y2020.day11;

import me.vforchi.aoc.Utils;
import org.apache.commons.collections4.ListUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Ferry {

    static int noSeat = '.';
    static int emptySeat = 'L';
    static int occupiedSeat = '#';

    List<Integer> seats;
    int numRows;
    int numColumns;

    boolean seatsStable;

    boolean longSearch;
    int occupiedSeatsLimit;

    Ferry(List<Integer> seats, int numRows, boolean longSearch, int occupiedSeatsLimit) {
        this.seats = seats;
        this.numRows = numRows;
        this.numColumns = seats.size() / numRows;

        this.longSearch = longSearch;
        this.occupiedSeatsLimit = occupiedSeatsLimit;
    }

    public long play() {
        while (!seatsStable) {
            this.next();
        }
        return countOccupiedSeats(this.seats);
    }

    public void next() {
        var newSeats = IntStream.range(0, numRows)
                .flatMap(r -> IntStream.range(0, numColumns)
                        .map(c -> nextSeat(r, c)))
                .boxed()
                .collect(Collectors.toList());

        this.seatsStable = ListUtils.isEqualList(seats, newSeats);
        this.seats = newSeats;
    }

    int nextSeat(int r, int c) {
        var nextSeats = getAdjacentSeats(r, c);
        var thisSeat = get(r, c);
        if (thisSeat == emptySeat && !nextSeats.contains(occupiedSeat)) {
            return occupiedSeat;
        } else if (thisSeat == occupiedSeat && countOccupiedSeats(nextSeats) >= occupiedSeatsLimit) {
            return emptySeat;
        } else {
            return thisSeat;
        }
    }

    List<Integer> getAdjacentSeats(int r, int c) {
        return List.of(
                getAdjacentSeatInDirection(r, c, -1, -1), getAdjacentSeatInDirection(r, c, -1, 0), getAdjacentSeatInDirection(r, c, -1, 1),
                getAdjacentSeatInDirection(r, c, 0, -1),                                                 getAdjacentSeatInDirection(r, c, 0, 1),
                getAdjacentSeatInDirection(r, c, 1, -1), getAdjacentSeatInDirection(r, c, 1, 0),   getAdjacentSeatInDirection(r, c, 1, 1)
        );
    }

    Integer getAdjacentSeatInDirection(int r, int c, int h, int v) {
        if (longSearch) {
            int nr = r, nc = c;
            do {
                nr += h; nc += v;
                if (get(nr, nc) != noSeat) {
                    return get(nr, nc);
                }
            } while (nr >= 0 && nr < numRows && nc >= 0 && nc < numColumns);
            return noSeat;
        } else {
            return get(r + h, c + v);
        }
    }

    int get(int row, int column) {
        if (row >= 0 && row < numRows && column >=0 && column < numColumns) {
            return seats.get(row * numColumns + column);
        } else {
            return noSeat;
        }
    }

    public String toString() {
        return ListUtils.partition(seats, numColumns).stream()
                .map(Utils::characterListToString)
                .collect(Collectors.joining("\n")) + "\n";
    }

    public static long countOccupiedSeats(List<Integer> seats) {
        return seats.stream()
                .filter(s -> s == occupiedSeat)
                .count();
    }

    public static Ferry fromText(List<String> lines, boolean longSearch, int occupiedSeatsLimit) {
        var seats = lines.stream()
                .flatMap(line -> line.chars().boxed())
                .collect(Collectors.toList());
        return new Ferry(seats, lines.size(), longSearch, occupiedSeatsLimit);
    }

}
