package me.vforchi.aoc.y2021.day04;

import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@NoArgsConstructor
public class Board {
    public static int size = 5;
    private static List<Integer> indices = IntStream.range(0, size).boxed().toList();

    private int[][] numbers = new int[size][size];
    private int[][] found = new int[size][size];

    public void setNumber(int row, int column, int number) {
        numbers[row][column] = number;
    }

    public Board play(int n) {
        Lists.cartesianProduct(indices, indices).stream()
                .filter(l -> numbers[l.get(0)][l.get(1)] == n)
                .findFirst()
                .ifPresent(l -> found[l.get(0)][l.get(1)] = 1);
        return this;
    }

    public boolean won() {
        return IntStream.range(0, size)
                .anyMatch(i -> isRowFull(i) || isColumnFull(i));
    }

    private boolean isColumnFull(int col) {
        return IntStream.range(0, size)
                .map(i -> found[i][col])
                .sum() == size;
    }

    private boolean isRowFull(int row) {
        return IntStream.range(0, size)
                .map(i -> found[row][i])
                .sum() == size;
    }

    public Integer score() {
        var indices = IntStream.range(0, size).boxed().toList();
        return Lists.cartesianProduct(indices, indices).stream()
                .filter(l -> found[l.get(0)][l.get(1)] == 0)
                .mapToInt(l -> numbers[l.get(0)][l.get(1)])
                .sum();
    }

    public static Board fromText(List<String> text) {
        var board = new Board();
        for (int row = 0; row < size; row++) {
            var line = text.get(row).trim().split("[ ]+");
            for (int column = 0; column < size; column++) {
                board.setNumber(row, column, Integer.parseInt(line[column]));
            }
        }
        return board;
    }
}
