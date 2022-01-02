package me.vforchi.aoc;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.swap;

public class Utils {

    public static Stream<List<String>> partitionByEmptyLine(List<String> list) {
        List<List<String>> partitionedList = new ArrayList<>();

        var current = new ArrayList<String>();
        for (String elem: list) {
            if (Strings.isNullOrEmpty(elem)) {
                partitionedList.add(current);
                current = new ArrayList<>();
            } else {
                current.add(elem);
            }
        }
        partitionedList.add(current);

        return partitionedList.stream();
    }

    public static String characterListToString(List<Integer> numbers) {
        return numbers.stream()
                .map(i -> String.valueOf((char) i.intValue()))
                .collect(Collectors.joining());
    }

    public static <T> Stream<List<T>> permutations(List<T> elements) {
        return permutations(new ArrayList<>(elements), elements.size());
    }

    private static <T> Stream<List<T>> permutations(List<T> elements, int n) {
        if (n == 1) {
            return Stream.of(new ArrayList<>(elements));
        } else {
            Stream<List<T>> tmp = Stream.empty();
            for (int i = 0; i < n; i++) {
                swap(elements, i, n - 1);
                tmp = Stream.concat(permutations(elements, n - 1), tmp);
                swap(elements, i, n - 1);
            }
            return tmp;
        }
    }
}
