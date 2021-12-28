package me.vforchi.aoc.y2021.day25;

import me.vforchi.aoc.Day;

import java.util.Arrays;

public class Day25 extends Day {

    private char[][] cucumbers;
    private int rows;
    private int columns;

    @Override
    public void setup(String path) {
        super.setup(path);
        cucumbers = input.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);
        rows = cucumbers.length;
        columns = cucumbers[0].length;
    }

    @Override
    public Object partOne() {
        int steps = 1;
        while (moving()) {
            steps++;
        }
        return steps;
    }

    private boolean moving() {
        boolean moving = false;

        var newCucumbers = initCucumbers(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cucumbers[i][j] == '>' && cucumbers[i][(j + 1) % columns] == '.') {
                    moving = true;
                    newCucumbers[i][j] = '.';
                    newCucumbers[i][(j + 1) % columns] = '>';
                    j++;
                } else {
                    newCucumbers[i][j] = cucumbers[i][j];
                }
            }
        }
        cucumbers = newCucumbers;

        newCucumbers = initCucumbers(rows, columns);
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                if (cucumbers[i][j] == 'v' && cucumbers[(i + 1) % rows][j] == '.') {
                    moving = true;
                    newCucumbers[i][j] = '.';
                    newCucumbers[(i + 1) % rows][j] = 'v';
                    i++;
                } else {
                    newCucumbers[i][j] = cucumbers[i][j];
                }
            }
        }
        cucumbers = newCucumbers;

        return moving;
    }

    private char[][] initCucumbers(int rows, int columns) {
        var newCucumbers = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(newCucumbers[i], '.');
        }
        return newCucumbers;
    }

    @Override
    public Object partTwo() {
        return "Nothing to do";
    }

}