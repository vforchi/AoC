package me.vforchi.aoc.y2020.day11;

import me.vforchi.aoc.Day;

public class Day11 extends Day {

	@Override
	public Object partOne() {
		return Ferry.fromText(this.input, false, 4).play();
	}

	@Override
	public Object partTwo() {
		return Ferry.fromText(this.input, true, 5).play();
	}

}
