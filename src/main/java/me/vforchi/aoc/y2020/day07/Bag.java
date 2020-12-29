package me.vforchi.aoc.y2020.day07;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class Bag {

    @Getter
    String color;
    Map<String, Integer> containedBags;

    public static Bag fromString(String string) {
        var tokens = string.split("( bags contain | bag[s]*(, |\\.))");
        var color = tokens[0];
        Map<String, Integer> containedBags = new HashMap<>();
        if (!tokens[1].contains("no other")) {
            for (int i = 1; i < tokens.length; i++) {
                var firstSpace = tokens[i].indexOf(' ');
                var inNumber = Integer.parseInt(tokens[i].substring(0, firstSpace));
                var inColor = tokens[i].substring(firstSpace+1);
                containedBags.put(inColor, inNumber);
            }
        }
        return new Bag(color, containedBags);
    }

}
