package me.vforchi.aoc.y2020.day08.instructions;

import java.util.regex.Pattern;

public class InstructionFactory {

	public static Instruction makeInstruction(String name, int value) {
		return switch (name) {
			case "acc" -> new AccInstruction(value);
			case "jmp" -> new JmpInstruction(value);
			case "nop" -> new NopInstruction(value);
			default -> throw new RuntimeException("Unknown instruction " + name);
		};
	}

    public static Instruction makeInstruction(String line) {
		var m = Pattern.compile("([a-z]+) ([+\\-0-9]+)").matcher(line);
		if (m.matches()) {
			return InstructionFactory.makeInstruction(m.group(1), Integer.parseInt(m.group(2)));
		} else {
			throw new RuntimeException("Invalid line " + line);
		}
    }

}
