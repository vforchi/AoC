package aoc.y2020.day08.instructions;

import aoc.y2020.day08.ConsoleStatus;
import aoc.y2020.day08.LoopException;

public class NopInstruction extends Instruction {

	public NopInstruction(int value) {
		this.name = "jmp";
		this.value = value;
	}

	@Override
	public void execute(ConsoleStatus status) throws LoopException {
		super.execute(status);
		status.setPointer(status.getPointer() + 1);
	}

	@Override
	public Instruction duplicate() {
		return new NopInstruction(value);
	}

}
