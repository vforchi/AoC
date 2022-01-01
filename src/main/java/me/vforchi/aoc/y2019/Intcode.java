package me.vforchi.aoc.y2019;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@RequiredArgsConstructor
public class Intcode {
    private static final int ADD = 1;
    private static final int MUL = 2;
    private static final int END = 99;

    private final String input;

    private List<Integer> runningProgram;
    private int pos;

    public static Intcode fromText(String input) {
        return new Intcode(input);
    }

    public void reset() {
        runningProgram = Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .collect(toCollection(ArrayList::new));
        pos = 0;
    }

    public int getValue(int pos) {
        return runningProgram.get(pos);
    }

    public void setValue(int pos, int value) {
        runningProgram.set(pos, value);
    }

    public void run() {
        while (runningProgram.get(pos) != END) {
            switch (runningProgram.get(pos)) {
                case ADD -> add(runningProgram.get(pos + 1), runningProgram.get(pos + 2), runningProgram.get(pos + 3));
                case MUL -> multiply(runningProgram.get(pos + 1), runningProgram.get(pos + 2), runningProgram.get(pos + 3));
                case END -> {}
            }
        }
    }

    private void add(Integer pos1, Integer pos2, Integer dest) {
        runningProgram.set(dest, runningProgram.get(pos1) + runningProgram.get(pos2));
        pos += 4;
    }

    private void multiply(Integer pos1, Integer pos2, Integer dest) {
        runningProgram.set(dest, runningProgram.get(pos1) * runningProgram.get(pos2));
        pos += 4;
    }
}
