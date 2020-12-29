package me.vforchi.aoc.y2020.day07;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import me.vforchi.aoc.Day;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day07 extends Day {

    Map<String, Bag> bags;

    @Override
    public Object partOne() {
        return bags.values().stream()
                .filter(containsShinyGoldMemo::getUnchecked)
                .count();
    }

    LoadingCache<Bag, Boolean> containsShinyGoldMemo = CacheBuilder.newBuilder()
            .build(CacheLoader.from(this::containsShinyGold));

    private boolean containsShinyGold(Bag bag) {
        if (bag.containedBags.containsKey("shiny gold")) {
            return true;
        } else {
            return bag.containedBags.keySet().stream()
                    .map(bags::get)
                    .anyMatch(containsShinyGoldMemo::getUnchecked);
        }
    }

    @Override
    public Object partTwo() {
        return countContainedBags(bags.get("shiny gold"));
    }

    private Long countContainedBags(Bag bag) {
        return bag.containedBags.entrySet().stream()
                .mapToLong(e -> e.getValue() * (1 + countContainedBags(bags.get(e.getKey()))))
                .sum();
    }

    @Override
    public void setup(String path) {
        super.setup(path);

        this.bags = this.input.stream()
                .map(Bag::fromString)
                .collect(Collectors.toMap(
                        Bag::getColor,
                        Function.identity()
                ));
    }

}
