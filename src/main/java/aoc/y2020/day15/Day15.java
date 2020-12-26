package aoc.y2020.day15;

import aoc.Day;

import java.util.HashMap;
import java.util.Map;

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
		Map<Integer, Integer> numbers = new HashMap<>(Map.of(16, 1, 11, 2, 15, 3, 0, 4, 1, 5));

		var nextNumber = 7;
		var round = 5;

		while (++round < size) {
			nextNumber = next(numbers, nextNumber, round);
		}
		return nextNumber;
	}

	private static int next(Map<Integer, Integer> numbers, int lastNumber, int round) {
		var nextNumber = 0;
		if (numbers.containsKey(lastNumber)) {
			nextNumber = round - numbers.get(lastNumber);
		}
		numbers.put(lastNumber, round);
		return nextNumber;
	}

	@Override
	public void setup(String path) throws Exception {
	}

}
