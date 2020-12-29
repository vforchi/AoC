package me.vforchi.aoc.y2020.day07;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Bag {

    private static Pattern pattern = Pattern.compile("([a-z ]+) contain ([0-9a-z, ]+)+\\.");

    @Getter
    String color;
    Map<String, Integer> containedBags;

    public static Bag fromString(String string) {
        var m = pattern.matcher(string.replaceAll(" bag[s]?", ""));
        if (m.matches()) {
            var color = m.group(1);
            Map<String, Integer> containedBags = Collections.emptyMap();
            if (!m.group(2).contains("no other")) {
                containedBags = Arrays.stream(m.group(2).split(", "))
                        .map(s -> s.split("(?<=[0-9]) "))
                        .collect(Collectors.toMap(
                                t -> t[1],
                                t -> Integer.parseInt(t[0])
                        ));
            }
            return new Bag(color, containedBags);
        } else {
            throw new RuntimeException("Can't parse bag " + string);
        }
    }

}
