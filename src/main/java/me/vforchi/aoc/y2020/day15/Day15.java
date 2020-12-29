package me.vforchi.aoc.y2020.day15;

import me.vforchi.aoc.Day;

public class Day15 extends Day {

	@Override
	public Object partOne() {
		return getNumber(2020);
	}

	@Override
	public Object partTwo() {
		return getNumber(30000000);
	}

	private static Integer getNumber(int size) {
		int[] numbers = new int[size];
		numbers[16] = 1;
		numbers[11] = 2;
		numbers[15] = 3;
		numbers[0] = 4;
		numbers[1] = 5;

		var nextNumber = 7;
		var round = 5;

		while (++round < size) {
			nextNumber = next(numbers, nextNumber, round);
		}
		return nextNumber;
	}

	private static int next(int[] numbers, int lastNumber, int round) {
		var lastNumberRound = numbers[lastNumber];
		numbers[lastNumber] = round;
		return (lastNumberRound == 0) ? 0 : (round - lastNumberRound);
	}

}
