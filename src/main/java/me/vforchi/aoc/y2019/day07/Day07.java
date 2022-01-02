package me.vforchi.aoc.y2019.day07;

import io.vavr.collection.Stream;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.Utils;
import me.vforchi.aoc.y2019.Intcode;

import java.util.List;
import java.util.stream.IntStream;

public class Day07 extends Day {

    private List<Intcode> amplifiers;
    private Intcode lastAmplifier;

    @Override
    public void setup(String path) {
        super.setup(path);
        amplifiers = IntStream.range(0, 5)
                .mapToObj(i -> Intcode.fromText(this.input.get(0)))
                .toList();
        lastAmplifier = amplifiers.get(amplifiers.size() - 1);
    }

    @Override
    public Object partOne() {
        var phases = List.of(0, 1, 2, 3, 4);
        return Utils.permutations(phases)
                .mapToLong(this::amplify)
                .max()
                .orElseThrow();
    }

    private long amplify(List<Integer> phases) {
        init(phases);
        return openLoop(0);
    }

    private int openLoop(int signal) {
        for (Intcode ampli: amplifiers) {
            ampli.run(signal);
            signal = ampli.getLastOutput();
        }
        return signal;
    }

    @Override
    public Object partTwo() {
        var phases = List.of(5, 6, 7, 8, 9);
        return Utils.permutations(phases)
                .mapToLong(this::loop)
                .max()
                .orElseThrow();
    }

    private void init(List<Integer> phases) {
        Stream.ofAll(amplifiers)
                .zip(phases)
                .forEach(ampli -> {
                    ampli._1.reset();
                    ampli._1.run(ampli._2);
                });
    }

    private long loop(List<Integer> phases) {
        init(phases);
        int signal = 0;
        boolean isRunning = true;
        while (isRunning) {
            var outputs = lastAmplifier.getOutputs().size();
            signal = openLoop(signal);
            isRunning = lastAmplifier.getOutputs().size() > outputs;
        }
        return signal;
    }

}
