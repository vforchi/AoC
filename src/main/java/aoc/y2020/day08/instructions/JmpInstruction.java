package aoc.y2020.day08.instructions;

import aoc.y2020.day08.ConsoleStatus;
import aoc.y2020.day08.LoopException;

public class JmpInstruction extends Instruction {

	public JmpInstruction(int value) {
		this.name = "jmp";
		this.value = value;
	}

	@Override
	public void execute(ConsoleStatus status) throws LoopException {
		super.execute(status);
		status.setPointer(status.getPointer() + value);
	}

	@Override
	public Instruction duplicate() {
		return new JmpInstruction(value);
	}

}
