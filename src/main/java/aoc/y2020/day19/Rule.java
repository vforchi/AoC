package aoc.y2020.day19;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Rule {

    int number;

    List<Integer> firstGroup;
    List<Integer> secondGroup;

    Character value;

    public Rule(String rule) {
        var tokens = rule.split(": ");
        this.number = Integer.parseInt(tokens[0]);

        if (tokens[1].contains("\"")) {
            this.value = tokens[1].replaceAll("\"", "").charAt(0);
        } else {
            tokens = tokens[1].split(" \\| ");
            firstGroup = toIntList(tokens[0]);
            if (tokens.length == 2) {
                secondGroup = toIntList(tokens[1]);
            } else {
                secondGroup = Collections.emptyList();
            }
        }
    }

    private List<Integer> toIntList(String token) {
        return Arrays.stream(token.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

}