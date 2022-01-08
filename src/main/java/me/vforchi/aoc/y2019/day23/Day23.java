package me.vforchi.aoc.y2019.day23;

import lombok.RequiredArgsConstructor;
import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2019.Intcode;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

public class Day23 extends Day {

    @RequiredArgsConstructor
    private static class Network {
        private final Map<Integer, Intcode> computers;
        private Map<Integer, List<Long>> inputs;
        private List<Long> natInput = new ArrayList<>();

        public void boot() {
            computers.forEach((addr, computer) -> {
                computer.reset();
                computer.run(addr);
            });
            inputs = IntStream.range(0, 50).boxed()
                    .collect(toMap(Function.identity(), i -> List.of(-1L)));
        }

        public static Network fromText(String program) {
            return new Network(IntStream.range(0, 50).boxed()
                    .collect(toMap(
                            Function.identity(),
                            i -> Intcode.fromText(program))));
        }

        public boolean hasNoNatInputs() {
            return Optional.ofNullable(natInput)
                    .map(List::isEmpty)
                    .orElse(true);
        }

        public void tick() {
            var newInputs = computers.entrySet().stream()
                    .map(e -> e.getValue().run(new ArrayDeque<>(inputs.getOrDefault(e.getKey(), List.of(-1L)))))
                    .flatMap(o -> ListUtils.partition(o, 3).stream())
                    .collect(toMap(
                            o -> o.get(0).intValue(),
                            o -> List.of(o.get(1), o.get(2)),
                            ListUtils::union
                    ));
            computers.keySet().forEach(i -> newInputs.computeIfAbsent(i, k -> List.of(-1L)));
            if (newInputs.containsKey(255)) {
                var newNatInputs = newInputs.get(255);
                natInput = newNatInputs.subList(newNatInputs.size() - 2, newNatInputs.size());
            }
            inputs = newInputs;
        }

        public void setInput(int addr, List<Long> packet) {
            inputs.put(addr, packet);
        }

        public boolean isNotIdle() {
            return inputs.values().stream()
                    .anyMatch(l -> l.size() > 1);
        }
    }

    private Network network;

    @Override
    public void setup(String path) {
        super.setup(path);
        network = Network.fromText(input.get(0));
    }

    @Override
    public Object partOne() {
        network.boot();
        while (network.hasNoNatInputs()) {
            network.tick();
        }
        return network.natInput.get(1);
    }

    @Override
    public Object partTwo() {
        network.boot();
        Long lastSent = null;
        while (true) {
            while (network.isNotIdle()) {
                network.tick();
            }
            network.setInput(0, network.natInput);
            if (lastSent == network.natInput.get(1)) {
                return lastSent;
            } else {
                lastSent = network.natInput.get(1);
            }
        }
    }

}
