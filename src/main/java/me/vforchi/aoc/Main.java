package me.vforchi.aoc;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        var year = args[0];
        var firstDay = Long.parseLong(args[1]);
        var lastDay = args.length == 3 ? Long.parseLong(args[2]) : firstDay;

        var start = System.currentTimeMillis();
        Stream.iterate(firstDay, i -> i + 1)
                .limit(lastDay - firstDay + 1)
                .forEach(day -> runDay(year, String.format("%02d", day)));
        System.out.printf("Total execution time: %1.3f[s]", 0.001 * (System.currentTimeMillis() - start));
    }

    private static void runDay(String yearString, String dayString) {
        try {
            var className = String.format("me.vforchi.aoc.y%s.day%s.Day%s", yearString, dayString, dayString);
            Day day = (Day) Class.forName(className).getDeclaredConstructor().newInstance();
            var start = System.currentTimeMillis();
            day.setup(String.format("input/%s/day%s.txt", yearString, dayString));

            var resultOne = day.partOne().toString();
            var durationOne = System.currentTimeMillis() - start;
            System.out.printf("Day %s, part 1: %s, %1.3f[s]%n", dayString, resultOne, 0.001 * durationOne);

            var resultTwo = day.partTwo().toString();
            var durationTwo = System.currentTimeMillis() - durationOne - start;
            System.out.printf("Day %s, part 2: %s, %1.3f[s]%n", dayString, resultTwo, 0.001 * durationTwo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
