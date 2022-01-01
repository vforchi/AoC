package me.vforchi.aoc.y2019;

import java.util.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;

public class Intcode {
    private static final int ADD = 1;
    private static final int MUL = 2;
    private static final int IN = 3;
    private static final int OUT = 4;
    private static final int JIT = 5;
    private static final int JIF = 6;
    private static final int LT = 7;
    private static final int EQ = 8;
    private static final int END = 99;

    private final String program;

    private List<Integer> runningProgram;

    private int pos;
    private List<Integer> outputs;

    public Intcode(String program) {
        this.program = program;
        reset();
    }

    public void reset() {
        runningProgram = Arrays.stream(program.split(","))
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
        run(new ArrayDeque<>());
    }

    public void run(int... inputs) {
        run(Arrays.stream(inputs).boxed().collect(toCollection(ArrayDeque::new)));
    }

    public void run(Deque<Integer> inputs) {
        outputs = new ArrayList<>();
        while (runningProgram.get(pos) != END) {
            int ins = runningProgram.get(pos);
            switch (ins % 100) {
                case ADD -> add(getParameter(ins, 1), getParameter(ins, 2), runningProgram.get(pos + 3));
                case MUL -> multiply(getParameter(ins, 1), getParameter(ins, 2), runningProgram.get(pos + 3));
                case IN -> input(inputs.pop());
                case OUT -> output(getParameter(ins, 1));
                case JIT -> jumpIfTrue(getParameter(ins, 1), getParameter(ins, 2));
                case JIF -> jumpIfFalse(getParameter(ins, 1), getParameter(ins, 2));
                case LT -> lessThan(getParameter(ins, 1), getParameter(ins, 2), runningProgram.get(pos + 3));
                case EQ -> equals(getParameter(ins, 1), getParameter(ins, 2), runningProgram.get(pos + 3));
            }
        }
    }

    private void add(Integer val1, Integer val2, Integer dest) {
        runningProgram.set(dest, val1 + val2);
        pos += 4;
    }

    private void multiply(Integer val1, Integer val2, Integer dest) {
        runningProgram.set(dest, val1 * val2);
        pos += 4;
    }

    private void input(Integer par) {
        runningProgram.set(runningProgram.get(pos + 1), par);
        pos += 2;
    }

    private void output(int par) {
        outputs.add(par);
        pos += 2;
    }

    private void jumpIfTrue(int par1, int par2) {
        pos = (par1 == 0) ? pos + 3 : par2;
    }

    private void jumpIfFalse(int par1, int par2) {
        pos = (par1 == 0) ? par2 : pos + 3;
    }

    private void lessThan(int par1, int par2, Integer dest) {
        runningProgram.set(dest, (par1 < par2) ? 1 : 0);
        pos += 4;
    }

    private void equals(int par1, int par2, Integer dest) {
        runningProgram.set(dest, (par1 == par2) ? 1 : 0);
        pos += 4;
    }

    private int getParameter(Integer ins, int par) {
        if ((ins % (int) Math.pow(10, par + 2) / (int) Math.pow(10, par + 1)) == 1) {
            return runningProgram.get(pos + par);
        } else {
            return runningProgram.get(runningProgram.get(pos + par));
        }
    }

    public String getStatus() {
        return runningProgram.stream()
                .map(Objects::toString)
                .collect(joining(","));
    }

    public int getOutput(int pos) {
        return outputs.get(pos);
    }

    public int getLastOutput() {
        return outputs.get(outputs.size() - 1);
    }

    public static Intcode fromText(String input) {
        return new Intcode(input);
    }
}
