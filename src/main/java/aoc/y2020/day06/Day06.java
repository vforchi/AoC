package aoc.y2020.day06;

import aoc.Day;

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

        List<List<String>> current = new ArrayList<>();
        for (var line: this.input) {
            if (line.length() == 0) {
                answersGroups.add(current);
                current = new ArrayList<>();
            } else {
                var answer = line.chars().boxed()
                        .map(String::valueOf)
                        .collect(Collectors.toList());
                current.add(answer);
            }
        }
        answersGroups.add(current);
    }
    
}
