package me.vforchi.aoc.y2015.day07;

import me.vforchi.aoc.Day;

import java.util.*;

public class Day07 extends Day {

    private Circuit circuit;

    private static class Circuit {
        private Map<String, List<String>> instructions;
        private List<String> wires;
        private Map<String, Integer> signals = new HashMap<>();

        public Circuit(Map<String, List<String>> dependencies, Map<String, List<String>> instructions) {
            this.instructions = instructions;
            this.wires = new ArrayList<>();

            var visited = new HashSet<String>();
            dependencies.keySet().forEach(w -> visit(w, visited, wires, dependencies));
        }

        private static void visit(String wire, Set<String> visited, List<String> sorted, Map<String, List<String>> dependencies) {
            if (!visited.contains(wire)) {
                visited.add(wire);
                dependencies.get(wire).forEach(w -> visit(w, visited, sorted, dependencies));
                sorted.add(wire);
            }
        }

        public void reset() {
            signals.clear();
        }

        public int getSignal(String wire) {
            wires.forEach(w -> signals.put(w, evaluate(w)));
            return signals.get(wire);
        }

        public void setSignal(String wire, int value) {
            signals.put(wire, value);
        }

        private Integer evaluate(String wire) {
            var instruction = instructions.get(wire);
            if (wire.matches("[0-9]+")) {
                return Integer.parseInt(wire);
            } else if (signals.containsKey(wire)) {
                return signals.get(wire);
            } else if (instruction.size() == 1 && instruction.get(0).matches("[0-9]+")) {
                return Integer.parseInt(instruction.get(0));
            } else if (instruction.size() == 3 && instruction.get(1).equals("AND")) {
                return evaluate(instruction.get(0)) & evaluate(instruction.get(2));
            } else if (instruction.size() == 3 && instruction.get(1).equals("OR")) {
                return evaluate(instruction.get(0)) | evaluate(instruction.get(2));
            } else if (instruction.size() == 3 && instruction.get(1).equals("LSHIFT")) {
                return evaluate(instruction.get(0)) << evaluate(instruction.get(2));
            } else if (instruction.size() == 3 && instruction.get(1).equals("RSHIFT")) {
                return evaluate(instruction.get(0)) >> evaluate(instruction.get(2));
            } else if (instruction.size() == 2 && instruction.get(0).equals("NOT")) {
                return ~evaluate(instruction.get(1)) & 0xffff;
            } else {
                return evaluate(instruction.get(0));
            }
        }

        public static Circuit fromInstructions(List<String> lines) {
            var dependencies = new HashMap<String, List<String>>();
            var instructions = new HashMap<String, List<String>>();

            for (var line: lines) {
                var tokens = line.split(" -> ");
                var wire = tokens[1];
                var instruction = List.of(tokens[0].split(" "));
                var dependency = instruction.stream()
                        .filter(d -> d.matches("[a-z]+"))
                        .toList();
                dependencies.put(wire, dependency);
                instructions.put(wire, instruction);
            }
            return new Circuit(dependencies, instructions);
        }
    }

    @Override
    public void setup(String path) {
        super.setup(path);
        circuit = Circuit.fromInstructions(input);
    }

    @Override
    public Integer partOne() {
        circuit.reset();
        return circuit.getSignal("a");
    }

    @Override
    public Integer partTwo() {
        circuit.reset();
        int a = circuit.getSignal("a");
        circuit.reset();
        circuit.setSignal("b", a);
        return circuit.getSignal("a");
    }

}
