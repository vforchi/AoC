package aoc.y2020.day21;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class Food {
    private static final Pattern pattern = Pattern.compile("([a-z ]+) \\(contains ([a-z, ]+)\\)");

    public final List<String> ingredients;
    public final List<String> allergens;

    public static Food fromString(String str) {
        var m = pattern.matcher(str);
        if (m.matches()) {
            var ingredients = Arrays.stream(m.group(1).split(" ")).collect(Collectors.toList());
            var allergens = m.group(2).split(", ");
            return new Food(ingredients, List.of(allergens));
        } else {
            throw new RuntimeException(str);
        }
    }
}
