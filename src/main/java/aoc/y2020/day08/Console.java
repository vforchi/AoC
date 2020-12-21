package aoc.y2020.day08;

import aoc.y2020.day08.instructions.Instruction;
import aoc.y2020.day08.instructions.InstructionFactory;

import java.util.List;
import java.util.stream.Collectors;

class Console {

	List<Instruction> instructions;
	ConsoleStatus status;

	public Console(List<Instruction> instructions) {
		this.status = new ConsoleStatus();
		this.instructions = instructions;
	}

	Long execute() throws LoopException {
		status.clear();
		while (true) {
			instructions.get(status.getPointer()).execute(status);
			if (status.getPointer() == instructions.size())
				return status.getAccumulator();
		}
	}

	Long executeNoThrow() {
		try {
			return execute();
		} catch (LoopException e) {
			return null;
		}
	}

	void reset() {
		this.instructions.forEach(i -> i.setExecuted(false));
	}

	Console duplicate() {
		var duplicated = instructions.stream()
				.map(Instruction::duplicate)
				.collect(Collectors.toList());
		return new Console(duplicated);
	}

	static Console fromLines(List<String> lines) {
		var instructions = lines.stream()
				.map(InstructionFactory::makeInstruction)
				.collect(Collectors.toList());
		return new Console(instructions);
	}

}

