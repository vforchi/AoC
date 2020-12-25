package aoc.y2020.day21;

import aoc.Day;
import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends Day {

    List<String> allIngredients;
    HashMap<String, String> allergensMap = new HashMap<>();

    @Override
    public Object partOne() {
        allergensMap.values().forEach(ingredient -> allIngredients.removeIf(i -> i.equals(ingredient)));
        return allIngredients.size();
    }

    @Override
    public Object partTwo() {
        return allergensMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.joining(","));
    }

    @Override
    public void setup(String path) throws Exception {
        super.setup(path);

        var foods = this.input.stream()
                .map(Food::fromString)
                .collect(Collectors.toList());

        allIngredients = foods.stream()
                .flatMap(f -> f.getIngredients().stream())
                .collect(Collectors.toList());

        var uniqueAllergens = foods.stream()
                .flatMap(f -> f.getAllergens().stream())
                .distinct()
                .collect(Collectors.toList());

        while (allergensMap.size() != uniqueAllergens.size()) {
            for (var allergen: uniqueAllergens) {
                foods.stream()
                        .filter(f -> f.getAllergens().contains(allergen))
                        .map(Food::getIngredients)
                        .reduce(ListUtils::intersection)
                        .filter(l -> l.size() == 1)
                        .map(l -> l.get(0))
                        .ifPresent(commonIngredient -> {
                            allergensMap.put(allergen, commonIngredient);
                            foods.forEach(f -> f.getIngredients().remove(commonIngredient));
                        });
            }
        }
    }
}