package aoc.y2020.day08;

import aoc.Day;
import aoc.y2020.day08.instructions.Instruction;
import aoc.y2020.day08.instructions.JmpInstruction;
import aoc.y2020.day08.instructions.NopInstruction;

import java.util.Objects;
import java.util.stream.IntStream;

public class Day08 extends Day {

	Console console;

	@Override
	public Object partOne() {
		try {
			console.execute();
			throw new RuntimeException("We shouldn't be here");
		} catch (LoopException e) {
			return e.getAccumulator();
		}
	}

	@Override
	public Object partTwo() {
		return IntStream.range(0, console.instructions.size()).boxed()
				.filter(i -> !console.instructions.get(i).getName().equals("acc"))
				.map(i -> modifyConsole(console, i))
				.map(Console::executeNoThrow)
				.filter(Objects::nonNull)
				.findFirst()
				.orElseThrow();
	}

	private Console modifyConsole(Console console, int i) {
		var newConsole = console.duplicate();
		var insToChange = newConsole.instructions.get(i);
		Instruction newInstruction;
		if (insToChange.getName().equals("jmp")) {
			newInstruction = new NopInstruction(insToChange.getValue());
		} else {
			newInstruction = new JmpInstruction(insToChange.getValue());
		}
		newConsole.instructions.set(i, newInstruction);
		return newConsole;
	}

	@Override
	public void setup(String path) throws Exception {
		super.setup(path);
		console = Console.fromLines(this.input);
	}

}
