package aoc.y2020;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Utils {

    public static Stream<List<String>> partitionByEmptyLine(List<String> list) {
        int[] indexes = Stream.of(
                IntStream.of(-1),
                IntStream.range(0, list.size())
                        .filter(i -> list.get(i).equals("")),
                IntStream.of(list.size())
        ).flatMapToInt(s -> s).toArray();

        return IntStream.range(0, indexes.length - 1)
                        .mapToObj(i -> list.subList(indexes[i] + 1, indexes[i + 1]));
    }

}
