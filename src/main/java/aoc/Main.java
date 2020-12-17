package aoc;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {
        var className = String.format("aoc.y%s.day%s.Day%s", args[0], args[1], args[1]);
        Day day = (Day) Class.forName(className).getDeclaredConstructor().newInstance();
        day.setup(String.format("input/%s/day%s.txt", args[0], args[1]));

        var start = System.currentTimeMillis();

        var resultOne = day.partOne().toString();
        var durationOne = System.currentTimeMillis() - start;
        System.out.println(String.format("Day %s, part 1: %s, %1.3f[s]", args[1], resultOne, 0.001*durationOne));

        var resultTwo = day.partTwo().toString();
        var durationTwo = System.currentTimeMillis() - durationOne - start;
        System.out.println(String.format("Day %s, part 2: %s, %1.3f[s]", args[1], resultTwo, 0.001*durationTwo));
    }

}