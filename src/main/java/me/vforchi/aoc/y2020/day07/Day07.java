package me.vforchi.aoc.y2020.day07;

import me.vforchi.aoc.Day;

import java.util.List;
import java.util.stream.Collectors;

public class Day07 extends Day {

    List<Bag> bags;

    @Override
    public Object partOne() {
        return bags.stream()
                .filter(this::containsShinyGold)
                .count();
    }

    private boolean containsShinyGold(Bag bag) {
        if (bag.containedBags.containsKey("shiny gold")) {
            return true;
        } else {
            return bag.containedBags.keySet().stream()
                    .map(this::findBagByColor)
                    .anyMatch(this::containsShinyGold);
        }
    }

    @Override
    public Object partTwo() {
        return countContainedBags(findBagByColor("shiny gold"));
    }

    private Long countContainedBags(Bag bag) {
        return bag.containedBags.entrySet().stream()
                .mapToLong(e -> e.getValue() * (1 + countContainedBags(findBagByColor(e.getKey()))))
                .sum();
    }

    private Bag findBagByColor(String color) {
        return bags.stream()
                .filter(b -> b.color.equals(color))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public void setup(String path) {
        super.setup(path);

        this.bags = this.input.stream()
                .map(Bag::fromString)
                .collect(Collectors.toList());
    }

}
