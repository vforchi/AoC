package me.vforchi.aoc.y2021.day20;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;
import lombok.Builder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Builder(setterPrefix = "with")
public class Image {
    private String algorithm;
    private Set<Tuple2<Integer, Integer>> image;
    private char background;
    private int min, max;

    public static Image fromText(List<String> lines) {
        var algorithm = lines.get(0);
        var size = lines.size() - 2;
        var image = IntStream.range(0, size)
                .boxed()
                .flatMap(i -> Stream.ofAll(lines.get(i + 2).toCharArray())
                        .zipWithIndex()
                        .filter(c -> c._1 == '#')
                        .map(c -> c.map1(x -> i))
                        .toJavaStream())
                .collect(Collectors.toSet());
        return Image.builder()
                .withAlgorithm(algorithm)
                .withImage(image)
                .withBackground('.')
                .withMin(0)
                .withMax(size - 1)
                .build();
    }

    public Image grow() {
        var newImage = IntStream.rangeClosed(min - 1, max + 1)
                .boxed()
                .flatMap(i -> IntStream.rangeClosed(min - 1, max + 1)
                        .filter(j -> enhance(i, j))
                        .mapToObj(j -> Tuple.of(i, j)))
                .collect(Collectors.toSet());
        var newBackground = (background == '.') ? algorithm.charAt(0) : algorithm.charAt(511);
        return Image.builder()
                .withAlgorithm(algorithm)
                .withImage(newImage)
                .withBackground(newBackground)
                .withMin(min - 1)
                .withMax(max + 1)
                .build();
    }

    private boolean enhance(int row, int col) {
        var code = IntStream.rangeClosed(-1, 1)
                .boxed()
                .flatMap(i -> IntStream.rangeClosed(-1, 1)
                        .mapToObj(j -> {
                            if (isOutside(row + i, col + j)) {
                                return background == '#' ? '1': '0';
                            } else {
                                return getPixel(row + i, col + j);
                            }
                        }))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
        var pos = Integer.parseInt(code, 2);
        return algorithm.charAt(pos) == '#';
    }

    private boolean isOutside(int row, int col) {
        return row < min || row > max || col < min || col > max;
    }

    private char getPixel(int i, int j) {
        return image.contains(Tuple.of(i, j)) ? '1' : '0';
    }

    public int countLit() {
        return image.size();
    }

}
