package me.vforchi.aoc.y2015.day06;

import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

@AllArgsConstructor
public class Command {
    private static final Pattern pattern = Pattern.compile("([a-z ]+) ([0-9]+),([0-9]+) through ([0-9]+),([0-9]+)");
    private String command;
    private int x1, y1, x2, y2;
    public static Command fromString(String line) {
        var m = pattern.matcher(line);
        if (m.matches()) {
            var command = m.group(1);
            var x1 = Integer.parseInt(m.group(2));
            var y1 = Integer.parseInt(m.group(3));
            var x2 = Integer.parseInt(m.group(4));
            var y2 = Integer.parseInt(m.group(5));
            return new Command(command, x1, y1, x2, y2);
        } else {
            throw new RuntimeException();
        }
    }

    public void apply1(Day06.Lights lights) {
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                switch (command) {
                    case "turn on" -> lights.lights[x][y] = 1;
                    case "turn off" -> lights.lights[x][y] = 0;
                    case "toggle" -> lights.lights[x][y] = (lights.lights[x][y] == 1) ? 0 : 1;
                }
            }
        }
    }

    public void apply2(Day06.Lights lights) {
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                switch (command) {
                    case "turn on" -> lights.lights[x][y] = lights.lights[x][y] + 1;
                    case "turn off" -> lights.lights[x][y] = Math.max(0, lights.lights[x][y] - 1);
                    case "toggle" -> lights.lights[x][y] = lights.lights[x][y] + 2;
                }
            }
        }
    }
}

