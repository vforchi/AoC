package aoc.y2020.day06;

import aoc.Day;
import aoc.y2020.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Day06 extends Day {

    private List<List<List<String>>> answersGroups = new ArrayList<>();

    @Override
    public Object partOne() {
        return answersGroups.stream()
                .mapToLong(this::countAnswers1)
                .sum();
    }
    
    private long countAnswers1(List<List<String>> answers) {
        return answers.stream()
                .flatMap(Collection::stream)
                .distinct()
                .count();
    }

    @Override
    public Object partTwo() {
        return answersGroups.stream()
                .mapToLong(this::countAnswers2)
                .sum();
    }

    private long countAnswers2(List<List<String>> answers) {
        var answersCount = answers.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                ));

        return answersCount.entrySet().stream()
                .filter(e -> e.getValue() == answers.size())
                .count();
    }

    @Override
    public void setup(String path) {
        super.setup(path);

        answersGroups = Utils.partitionByEmptyLine(input)
                .map(Day06::toAnswerGroup)
                .collect(Collectors.toList());
    }

    private static List<List<String>> toAnswerGroup(List<String> lines) {
        return lines.stream()
                .map(Day06::splitAnswers)
                .collect(Collectors.toList());
    }

    private static List<String> splitAnswers(String line) {
        return line.chars().boxed()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }
    
}
