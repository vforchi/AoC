package aoc.y2020.day08.instructions;

import aoc.y2020.day08.ConsoleStatus;
import aoc.y2020.day08.LoopException;

public class AccInstruction extends Instruction {

	public AccInstruction(int value) {
		this.name = "acc";
		this.value = value;
	}

	@Override
	public void execute(ConsoleStatus status) throws LoopException {
		super.execute(status);
		status.setPointer(status.getPointer() + 1);
		status.setAccumulator(status.getAccumulator() + value);
	}

	@Override
	public Instruction duplicate() {
		return new AccInstruction(value);
	}

}
