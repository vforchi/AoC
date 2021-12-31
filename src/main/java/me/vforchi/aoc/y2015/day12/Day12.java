package me.vforchi.aoc.y2015.day12;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import me.vforchi.aoc.Day;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day12 extends Day {

    @Override
    public Object partOne() {
        var m = Pattern.compile("-?\\d+").matcher(input.get(0));
        int sum = 0;
        while (m.find()) {
            sum += Integer.parseInt(m.group(0));
        }
        return sum;
    }


    @SneakyThrows
    @Override
    public Object partTwo() {
        var obj = new ObjectMapper().readValue(input.get(0), Object.class);
        return countNonRed(obj);
    }

    private int countNonRed(Object obj) {
        int count = 0;
        if (obj instanceof Map m) {
            if (!m.containsValue("red")) {
                for (var o: m.values()) {
                    count += countNonRed(o);
                }
            }
        } else if (obj instanceof List l) {
            for (var o: l) {
                count += countNonRed(o);
            }
        } else if (obj instanceof Integer n) {
            count += n;
        }
        return count;
    }

}
