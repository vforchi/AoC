package me.vforchi.aoc.y2015.day23;

import lombok.AllArgsConstructor;
import me.vforchi.aoc.Day;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 extends Day {

    private List<Instruction> instructions;
    private Map<String, Integer> registers = new HashMap<>();
    private int pos;

    @AllArgsConstructor
    private static class Instruction {
        String name;
        String[] tokens;

        public static Instruction fromText(String text) {
            var name = text.split(" ")[0];
            var tokens = text.substring(name.length() + 1).split(", ");
            return new Instruction(name, tokens);
        }
    }

    @Override
    public void setup(String path) {
        super.setup(path);
        instructions = input.stream()
                .map(Instruction::fromText)
                .toList();
    }

    @Override
    public Object partOne() {
        return run(0, 0);
    }

    @Override
    public Object partTwo() {
        return run(1, 0);
    }

    private int run(int a, int b) {
        registers.put("a", a);
        registers.put("b", b);
        pos = 0;
        while (pos >= 0 && pos < instructions.size()) {
            execute(instructions.get(pos));
        }
        return registers.get("b");
    }

    public void execute(Instruction ins) {
        switch (ins.name) {
            case "hlf" -> { registers.compute(ins.tokens[0], (k, v) -> v / 2); pos += 1; }
            case "tpl" -> { registers.compute(ins.tokens[0], (k, v) -> v * 3); pos += 1; }
            case "inc" -> { registers.compute(ins.tokens[0], (k, v) -> v + 1); pos += 1; }
            case "jmp" -> pos += Integer.parseInt(ins.tokens[0]);
            case "jie" -> {
                if (registers.get(ins.tokens[0]) % 2 == 0) {
                    pos += Integer.parseInt(ins.tokens[1]);
                } else {
                    pos += 1;
                }
            }
            case "jio" -> {
                if (registers.get(ins.tokens[0]) == 1) {
                    pos += Integer.parseInt(ins.tokens[1]);
                } else {
                    pos += 1;
                }
            }
            default -> throw new RuntimeException();
        }
    }

}
