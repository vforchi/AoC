package me.vforchi.aoc.y2019;

import io.vavr.Tuple2;
import io.vavr.collection.Stream;
import lombok.Getter;

import java.util.*;

import static java.util.stream.Collectors.*;

public class Intcode {
    private static final int ADD = 1;
    private static final int MUL = 2;
    private static final int IN = 3;
    private static final int OUT = 4;
    private static final int JIT = 5;
    private static final int JIF = 6;
    private static final int LT = 7;
    private static final int EQ = 8;
    private static final int BASE = 9;
    private static final int END = 99;

    private final String program;

    private Map<Long, Long> runningProgram;

    private long pos;
    private long relativeBase;
    @Getter private List<Long> outputs;

    public Intcode(String program) {
        this.program = program;
        reset();
    }

    public void reset() {
        runningProgram = Stream.ofAll(Arrays.stream(program.split(",")))
                .map(Long::parseLong)
                .zipWithIndex()
                .collect(toMap(t -> t._2.longValue(), Tuple2::_1));
        pos = 0;
        relativeBase = 0;
        outputs = new ArrayList<>();
    }

    public long getValue(int pos) {
        return runningProgram.get(pos);
    }

    public void setValue(long pos, long value) {
        runningProgram.put(pos, value);
    }

    public List<Long> run() {
        return run(new ArrayDeque<>());
    }

    public List<Long> run(long... inputs) {
        return run(Arrays.stream(inputs).boxed().collect(toCollection(ArrayDeque::new)));
    }

    public List<Long> run(Deque<Long> inputs) {
        var newOutputs = new ArrayList<Long>();
        while (runningProgram.get(pos) != END) {
            int ins = runningProgram.get(pos).intValue();
            switch (ins % 100) {
                case ADD -> add(getParameter(ins, 1), getParameter(ins, 2), getAddress(ins, 3));
                case MUL -> multiply(getParameter(ins, 1), getParameter(ins, 2), getAddress(ins, 3));
                case IN -> {
                    if (inputs.isEmpty()) {
                        outputs.addAll(newOutputs);
                        return newOutputs;
                    } else {
                        input(inputs.pop(), getAddress(ins, 1));
                    }
                }
                case OUT -> {
                    newOutputs.add(getParameter(ins, 1));
                    pos += 2;
                }
                case JIT -> jumpIfTrue(getParameter(ins, 1), getParameter(ins, 2));
                case JIF -> jumpIfFalse(getParameter(ins, 1), getParameter(ins, 2));
                case LT -> lessThan(getParameter(ins, 1), getParameter(ins, 2), getAddress(ins, 3));
                case EQ -> equals(getParameter(ins, 1), getParameter(ins, 2), getAddress(ins, 3));
                case BASE -> adjustBase(getParameter(ins, 1));
            }
        }
        outputs.addAll(newOutputs);
        return newOutputs;
    }

    private void add(long val1, long val2, long dest) {
        runningProgram.put(dest, val1 + val2);
        pos += 4;
    }

    private void multiply(long val1, long val2, long dest) {
        runningProgram.put(dest, val1 * val2);
        pos += 4;
    }

    private void input(long par, long dest) {
        runningProgram.put(dest, par);
        pos += 2;
    }

    private void jumpIfTrue(long par1, Long par2) {
        pos = (par1 == 0L) ? pos + 3 : par2.intValue();
    }

    private void jumpIfFalse(long par1, Long par2) {
        pos = (par1 == 0L) ? par2.intValue() : pos + 3;
    }

    private void lessThan(long par1, long par2, long dest) {
        runningProgram.put(dest, (par1 < par2) ? 1L : 0L);
        pos += 4;
    }

    private void equals(long par1, long par2, long dest) {
        runningProgram.put(dest, (par1 == par2) ? 1L : 0L);
        pos += 4;
    }

    private void adjustBase(Long par) {
        relativeBase += par.intValue();
        pos += 2;
    }

    private long getParameter(Integer ins, Integer par) {
        return runningProgram.getOrDefault(getAddress(ins, par), 0L);
    }

    private long getAddress(Integer ins, Integer par) {
        return switch ((ins % (int) Math.pow(10, par + 2) / (int) Math.pow(10, par + 1))) {
            case 0 -> runningProgram.get(pos + par).intValue();
            case 1 -> pos + par;
            case 2 -> relativeBase + runningProgram.get(pos + par);
            default -> throw new RuntimeException();
        };
    }

    public String getStatus() {
        return runningProgram.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getValue().toString())
                .collect(joining(","));
    }

    public Long getOutput(int pos) {
        return outputs.get(pos);
    }

    public Long getLastOutput() {
        return outputs.get(outputs.size() - 1);
    }

    public Long getLastOutput(int fromEnd) {
        return outputs.get(outputs.size() - 1 - fromEnd);
    }

    public static Intcode fromText(String input) {
        return new Intcode(input);
    }

    public boolean isNotFinished() {
        return runningProgram.get(pos) != END;
    }
}
