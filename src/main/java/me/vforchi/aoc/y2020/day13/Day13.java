package me.vforchi.aoc.y2020.day13;

import me.vforchi.aoc.Day;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 extends Day {

	private Long arrival;
	private List<Integer> buses;
	private List<Tuple2<BigInteger, BigInteger>> busesAndRemainders;

	@Override
	public Object partOne() {
		return buses.stream()
				.min(Comparator.comparingLong(b -> b - (arrival % b)))
				.map(b -> b * (b - (arrival % b)))
				.orElseThrow();
	}

	@Override
	public Object partTwo() {
		var N = busesAndRemainders.stream()
				.map(Tuple2::_1)
				.reduce(BigInteger::multiply)
				.orElseThrow();

		return busesAndRemainders.stream()
				.map(t -> {
					var c = t._2;
					var n = N.divide(t._1);
					var d = n.modInverse(t._1);
					return c.multiply(n).multiply(d);
				})
				.reduce(BigInteger::add)
				.map(r -> r.mod(N))
				.orElseThrow();
	}

	@Override
	public void setup(String path) {
		super.setup(path);

		this.arrival = Long.parseLong(input.get(0));

		this.buses = Stream.of(input.get(1).split(","))
				.filter(s -> !s.equals("x"))
				.map(Integer::parseInt)
				.collect(Collectors.toList());

		this.busesAndRemainders = Stream.of(input.get(1).split(","))
				.zipWithIndex()
				.filter(t -> !t._1.equals("x"))
				.map(Day13::toBusAndRemainder)
				.collect(Collectors.toList());
	}

	private static Tuple2<BigInteger, BigInteger> toBusAndRemainder(Tuple2<String, Integer> busAndDelay) {
		var bus = new BigInteger(busAndDelay._1);
		var remainder = bus.subtract(BigInteger.valueOf(busAndDelay._2));
		return new Tuple2<>(bus, remainder);
	}

}
