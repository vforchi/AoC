package me.vforchi.aoc.y2020.day12;

import me.vforchi.aoc.Day;

import java.util.List;
import java.util.stream.Collectors;

public class Day12 extends Day {

	List<Command> commands;

	@Override
	public Object partOne() {
		var ship = new Ship(0, 0);
		commands.forEach(c -> c.apply(ship));
		return ship.toString();
	}

	@Override
	public Object partTwo() {
		var ship = new Ship(0, 0);
		var waypoint = new Waypoint(10, 1);
		commands.forEach(c -> c.apply(ship, waypoint));
		return ship.toString();
	}

	@Override
	public void setup(String path) {
		super.setup(path);

		commands = input.stream()
				.map(Command::fromString)
				.collect(Collectors.toList());
	}

}
