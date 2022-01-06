package me.vforchi.aoc.y2019.day21;

import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;

public class Day21 extends Day {

    private Intcode springdroid;

    @Override
    public void setup(String path) {
        super.setup(path);
        springdroid = Intcode.fromText(input.get(0));
    }

    @Override
    public Object partOne() {
        springdroid.runCommands(
                "NOT A J",
                "NOT B T",
                "OR T J",
                "NOT C T",
                "OR T J",  // hole in A, B or C
                "AND D J", // ground in D
                "WALK"
        );
        printOutput();
        return springdroid.getLastOutput();
    }

    @Override
    public Object partTwo() {
        springdroid.reset();
        springdroid.runCommands(
                "NOT A J",
                "NOT B T",
                "OR T J",
                "NOT C T",
                "OR T J",  // hole in A, B or C
                "AND D J", // ground in D
                "NOT E T",
                "NOT T T",
                "OR H T",   
                "AND T J", // T = E or H (we can't jump to D if E and H are holes
                "RUN"
        );
        printOutput();
        return springdroid.getLastOutput();
    }

    private void printOutput() {
        springdroid.getOutputs().stream()
                .map(l -> (char) l.intValue())
                .takeWhile(l -> l < 255)
                .forEach(System.out::print);
    }

}
