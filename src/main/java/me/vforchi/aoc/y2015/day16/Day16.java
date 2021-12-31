package me.vforchi.aoc.y2015.day16;

import me.vforchi.aoc.Day;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 extends Day {

    private record Sue(String name, Map<String, Integer> ingredients) {
        public static Sue fromText(String text) {
            var tokens = text.split("[ :,]");
            var name = tokens[1];
            var ingredients = IntStream.iterate(3, i -> i < tokens.length - 2, i -> i = i + 4)
                    .boxed()
                    .collect(Collectors.toMap(
                            i -> tokens[i],
                            i -> Integer.parseInt(tokens[i + 2])
                    ));
            return new Sue(name, ingredients);
        }
    }

    private Map<String, Integer> gift = Map.of(
            "children", 3,
            "cats", 7,
            "samoyeds", 2,
            "pomeranians", 3,
            "akitas", 0,
            "vizslas", 0,
            "goldfish", 5,
            "trees", 3,
            "cars", 2,
            "perfumes", 1
    );

    @Override
    public Object partOne() {
        return input.stream()
                .map(Sue::fromText)
                .filter(sue -> matches(sue, gift))
                .map(Sue::name)
                .findFirst()
                .orElseThrow();
    }

    private boolean matches(Sue sue, Map<String, Integer> gift) {
        return sue.ingredients.entrySet().stream().allMatch(e -> e.getValue() == gift.get(e.getKey()));
    }

    private boolean contains3(String sue, List<String> gift) {
        return gift.stream()
                .filter(sue::contains)
                .count() == 3;
    }

    @Override
    public Object partTwo() {
        return input.stream()
                .map(Sue::fromText)
                .filter(this::matches2)
                .map(Sue::name)
                .findFirst()
                .orElseThrow();
    }

    private boolean matches2(Sue sue) {
        return sue.ingredients.entrySet().stream().allMatch(e -> matchIngredient(gift, e.getKey(), e.getValue()));
    }

    private boolean matchIngredient(Map<String, Integer> gift, String name, Integer amount) {
        return switch (name) {
            case "cats", "trees" -> amount > gift.get(name);
            case "pomeranians", "goldfish" -> amount < gift.get(name);
            default -> amount == gift.get(name);
        };
    }

}
