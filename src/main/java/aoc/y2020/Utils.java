package aoc.y2020;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

}
