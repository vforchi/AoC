package me.vforchi.aoc.y2015.day15;

import me.vforchi.aoc.Day;

import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Day15 extends Day {

    private record Ingredient(String name, int[] properties) {
        public static Ingredient fromText(String text) {
            var tokens = text.split("[ ,]");
            return new Ingredient(tokens[0], new int[]{
                    Integer.parseInt(tokens[2]),
                    Integer.parseInt(tokens[5]),
                    Integer.parseInt(tokens[8]),
                    Integer.parseInt(tokens[11]),
                    Integer.parseInt(tokens[14])
            });
        }
    }

    private Ingredient[] ingredients;

    @Override
    public void setup(String path) {
        super.setup(path);
        ingredients = input.stream()
                .map(Ingredient::fromText)
                .toArray(Ingredient[]::new);
    }

    @Override
    public Object partOne() {
        return mix(q -> true);
    }

    @Override
    public Object partTwo() {
        return mix(q -> mixCalories(ingredients, q) == 500);
    }

    public int mix(Predicate<int[]> filter) {
        return IntStream.rangeClosed(0, 100)
                .flatMap(i -> IntStream.rangeClosed(0, 100 - i)
                        .flatMap(j -> IntStream.rangeClosed(0, 100 - i - j)
                                .mapToObj(k -> new int[]{ i, j, k, 100 - i - j - k})
                                .filter(filter)
                                .mapToInt(q -> mixWithoutCalories(ingredients, q))))
                .max()
                .orElseThrow();
    }

    private int mixWithoutCalories(Ingredient[] ingredients, int[] quantities) {
        return IntStream.range(0, 4)
                .map(quality -> mixQuality(ingredients, quality, quantities))
                .map(q -> Math.max(0, q))
                .reduce((a, b) -> a * b)
                .orElseThrow();
    }

    private int mixCalories(Ingredient[] ingredients, int[] quantities) {
        return mixQuality(ingredients, 4, quantities);
    }

    private int mixQuality(Ingredient[] ingredients, int quality, int[] quantities) {
        return IntStream.range(0, ingredients.length)
                .map(i -> ingredients[i].properties[quality] * quantities[i])
                .reduce(Integer::sum)
                .orElseThrow();
    }

}
