package me.vforchi.aoc.y2019.day08;

import me.vforchi.aoc.Day;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class Day08 extends Day {

    private final int width = 25;
    private final int height = 6;
    private final int size = width * height;

    private String image;
    private int numLayers;

    @Override
    public void setup(String path) {
        super.setup(path);
        image = input.get(0);
        numLayers = image.length() / size;
    }

    @Override
    public Object partOne() {
        return IntStream.range(0, numLayers)
                .mapToObj(i -> image.substring(i * size, (i + 1) * size))
                .min(Comparator.comparing(layer -> StringUtils.countMatches(layer, '0')))
                .map(layer -> StringUtils.countMatches(layer, '1') * StringUtils.countMatches(layer, '2'))
                .orElseThrow();
    }


    @Override
    public Object partTwo() {
        return '\n' + IntStream.range(0, height)
                .mapToObj(y -> IntStream.range(0, width)
                        .mapToObj(x -> render(y * width + x))
                        .collect(joining("", "", "\n")))
                .collect(joining());
    }

    private String render(int pos) {
        return IntStream.range(0, numLayers)
                .mapToObj(l -> image.charAt(l * size + pos))
                .filter(p -> p != '2')
                .findFirst()
                .map(p -> (p == '1') ? "#" : ".")
                .orElseThrow();
    }

}
