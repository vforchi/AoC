package me.vforchi.aoc.y2020.day08.instructions;

import me.vforchi.aoc.y2020.day08.ConsoleStatus;
import me.vforchi.aoc.y2020.day08.LoopException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Instruction {

	protected String name;
	protected int value;
	protected boolean executed = false;

	public void execute(ConsoleStatus status) throws LoopException {
		if (executed) {
			throw new LoopException(status.getAccumulator());
		} else {
			executed = true;
		}
	}

	public abstract Instruction duplicate();

	public String toString() {
		return name + value;
	}

}
