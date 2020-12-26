package aoc.y2020.day14;

import aoc.Day;
import io.vavr.Tuple2;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day14 extends Day {

	private static int ZERO = '0';
	private static int ONE = '1';
	private static int X = 'X';

	Map<Long, Long> memory = new HashMap<>();

	@Override
	public Object partOne() {
		return execute(input, this::applyMaskToValue);
	}

	@Override
	public Object partTwo() {
		return execute(input, this::applyMaskToAddress);
	}

	private long execute(List<String> commands, BiConsumer<List<Integer>, Tuple2<List<Integer>, List<Integer>>> applyMask) {
		memory.clear();

		List<Integer> mask = new ArrayList<>();
		for (var command: commands) {
			if (command.startsWith("mask")) {
				mask = getMask(command);
			} else {
				applyMask.accept(mask, getAddressAndValue(command));
			}
		}

		return memory.values().stream()
				.mapToLong(l -> l)
				.sum();
	}

	private void applyMaskToValue(List<Integer> mask, Tuple2<List<Integer>, List<Integer>> addressAndValue) {
		var newValue = IntStream.range(0, 36)
				.boxed()
				.map(i -> mask.get(i) == X ? addressAndValue._2.get(i) : mask.get(i))
				.collect(Collectors.toList());

		writeToMemory(addressAndValue._1, newValue);
	}

	private void applyMaskToAddress(List<Integer> mask, Tuple2<List<Integer>, List<Integer>> addressAndValue) {
		var newAddress = IntStream.range(0, 36)
				.boxed()
				.map(i -> (mask.get(i) == ZERO) ? addressAndValue._1.get(i) : mask.get(i))
				.collect(Collectors.toList());

		generateAllAddresses(newAddress).forEach(a -> writeToMemory(a, addressAndValue._2));
	}

	private void writeToMemory(List<Integer> address, List<Integer> value) {
		var decimalAddress = characterListToDecValue(address);
		var decimalValue = characterListToDecValue(value);
		memory.put(decimalAddress, decimalValue);
	}

	private static List<List<Integer>> generateAllAddresses(List<Integer> address) {
		var firstX = address.indexOf(X);
		if (firstX != -1) {
			var address0 = new ArrayList<>(address);
			address0.set(firstX, ZERO);
			var address1 = new ArrayList<>(address);
			address1.set(firstX, ONE);

			return Stream.concat(
					generateAllAddresses(address0).stream(),
					generateAllAddresses(address1).stream()
			).collect(Collectors.toList());
		} else {
			return List.of(address);
		}
	}

	private static List<Integer> getMask(String command) {
		return command.replaceAll("mask = ", "").chars()
				.boxed()
				.collect(Collectors.toList());
	}

	private static Pattern pattern = Pattern.compile("mem\\[([0-9]+)] = ([0-9]+)");

	private static Tuple2<List<Integer>, List<Integer>> getAddressAndValue(String command) {
		var m = pattern.matcher(command);
		if (m.matches()) {
			return new Tuple2<>(
					decValueToCharacterList(m.group(1)),
					decValueToCharacterList(m.group(2))
			);
		} else {
			throw new RuntimeException("Can't parse " + command);
		}
	}

	private static List<Integer> decValueToCharacterList(String value) {
		return Optional.of(value)
				.map(Long::parseLong)
				.map(Long::toBinaryString)
				.map(l -> StringUtils.leftPad(l, 36, "0"))
				.map(s -> s.chars().boxed().collect(Collectors.toList()))
				.orElseThrow();
	}

	private static Long characterListToDecValue(List<Integer> value) {
		var stringValue = value.stream()
				.map(i -> String.valueOf((char) i.intValue()))
				.collect(Collectors.joining());
		return Long.parseLong(stringValue, 2);
	}

}
