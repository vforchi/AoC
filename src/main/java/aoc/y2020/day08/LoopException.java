package aoc.y2020.day08;

import lombok.Getter;

public class LoopException extends Exception {

	@Getter
	private long accumulator;

	public LoopException(long accumulator) {
		this.accumulator = accumulator;
	}

	public String toString() {
		return "Loop: " + accumulator;
	}

}
