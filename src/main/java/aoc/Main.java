package aoc;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        var year = args[0];
        var firstDay = Long.parseLong(args[1]);
        var lastDay = args.length == 3 ? Long.parseLong(args[2]) : firstDay;

        Stream.iterate(firstDay, i -> i + 1)
                .limit(lastDay - firstDay + 1)
                .forEach(day -> runDay(year, day.toString()));
    }

    private static void runDay(String yearString, String dayString) {
        try {
            var className = String.format("aoc.y%s.day%s.Day%s", yearString, dayString, dayString);
            Day day = (Day) Class.forName(className).getDeclaredConstructor().newInstance();
            day.setup(String.format("input/%s/day%s.txt", yearString, dayString));

            var start = System.currentTimeMillis();

            var resultOne = day.partOne().toString();
            var durationOne = System.currentTimeMillis() - start;
            System.out.println(String.format("Day %s, part 1: %s, %1.3f[s]", dayString, resultOne, 0.001 * durationOne));

            var resultTwo = day.partTwo().toString();
            var durationTwo = System.currentTimeMillis() - durationOne - start;
            System.out.println(String.format("Day %s, part 2: %s, %1.3f[s]", dayString, resultTwo, 0.001 * durationTwo));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}