package me.vforchi.aoc.y2020.day10;

import me.vforchi.aoc.Day;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.vavr.collection.Stream;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.vavr.collection.Stream.ofAll;
import static java.lang.Math.min;

public class Day10 extends Day {

	private List<Integer> adapters;

	@Override
	public Object partOne() {
		var intervalCounts = IntStream.range(1, adapters.size()).boxed()
				.map(i -> adapters.get(i) - adapters.get(i - 1))
				.collect(Collectors.groupingBy(
						Function.identity(),
						Collectors.counting()
				));
		return intervalCounts.get(1) * intervalCounts.get(3);
	}

	@Override
	public Object partTwo() {
		return findCombinations(0);
	}

	LoadingCache<Integer, Long> memo = CacheBuilder.newBuilder()
			.build(CacheLoader.from(this::findCombinations));

	private long findCombinations(int index) {
		if (index == adapters.size() - 1) {
			return 1;
		} else {
			return ofAll(adapters.subList(index + 1, min(index + 4, adapters.size())))
					.map(adapter -> adapter - adapters.get(index))
					.zipWithIndex()
					.filter(t -> t._1 > 0 && t ._1 < 4)
					.map(t -> memo.getUnchecked(index + 1 + t._2))
					.sum().longValue();
		}
	}

	@Override
	public void setup(String path) {
		super.setup(path);

		var adaptersStream = Stream.ofAll(input)
				.map(Integer::parseInt)
				.sorted();

		this.adapters = Stream.concat(
				Stream.of(0),
				adaptersStream,
				Stream.of(adaptersStream.last() + 3)
		).collect(Collectors.toList());
	}

}
