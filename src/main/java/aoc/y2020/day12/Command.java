package aoc.y2020.day12;

public class Command {
    String name;
    int value;

    public void apply(Ship ship) {
        switch (name) {
            case "N", "S", "W", "E" -> ship.move(name, value);
            case "F" -> ship.forward(value);
            case "L" -> ship.rotate(-value);
            case "R" -> ship.rotate(value);
        }
    }

    public void apply(Ship ship, Waypoint waypoint) {
        switch (name) {
            case "N", "S", "W", "E" -> waypoint.move(name, value);
            case "F" -> ship.toWaypoint(waypoint, value);
            case "R" -> waypoint.rotate(value);
            case "L" -> waypoint.rotate(360 - value);
        }
    }

    public static Command fromString(String posString) {
        var pos = new Command();
        pos.name = posString.substring(0, 1);
        pos.value = Integer.parseInt(posString.substring(1));
        return pos;
    }

}