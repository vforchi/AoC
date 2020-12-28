package me.vforchi.aoc.y2020.day16;

import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.vavr.collection.Stream.ofAll;

@ToString
@RequiredArgsConstructor
public class Ticket {

    private final List<Integer> fields;

    public int getField(int field) {
        return fields.get(field);
    }

    public int invalidFields(List<TicketRule> rules) {
        return fields.stream()
                .filter(f -> noMatches(f, rules))
                .reduce(Integer::sum)
                .orElse(0);
    }

    private boolean noMatches(Integer field, List<TicketRule> rules) {
        return rules.stream().noneMatch(rule -> rule.isValid(field));
    }

    public boolean hasNoInvalidFields(List<TicketRule> rules) {
        return fields.stream().noneMatch(f -> noMatches(f, rules));
    }

    public Set<Integer> compatibleFields(TicketRule rule) {
        return ofAll(fields)
                .zipWithIndex()
                .filter(t -> rule.isValid(t._1))
                .map(Tuple2::_2)
                .collect(Collectors.toSet());
    }

    public static Ticket fromString(String string) {
        var fields = Arrays.stream(string.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return new Ticket(fields);
    }

}
