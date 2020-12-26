package aoc.y2020.day05;

import aoc.Day;

import java.util.List;
import java.util.stream.Collectors;


public class Day05 extends Day {

    private List<Integer> tickets;

    @Override
    public Object partOne() {
        return tickets.stream().max(Integer::compare).orElseThrow();
    }

    @Override
    public Object partTwo() {
        var allTickets = tickets.stream().sorted().collect(Collectors.toList());
        var previous = allTickets.get(0);
        for (var current: allTickets) {
            if (current - previous == 2) {
                return (current - 1);
            } else {
                previous = current;
            }
        }
        throw new RuntimeException("Couldn't find the ticket");
    }

    @Override
    public void setup(String path) {
        super.setup(path);

        tickets = input.stream()
                .map(s -> s.replaceAll("[BR]", "1"))
                .map(s -> s.replaceAll("[FL]", "0"))
                .map(s -> Integer.parseInt(s, 2))
                .collect(Collectors.toList());
    }
    
}
