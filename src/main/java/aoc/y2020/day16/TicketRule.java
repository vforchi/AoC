package aoc.y2020.day16;

import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.SetUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor
@ToString
public class TicketRule {

    public String name;
    public int min1, max1, min2, max2;

    public boolean isValid(int number) {
        return (number >= min1 && number <= max1) || (number >= min2 && number <= max2);
    }

    public List<Integer> findValidFields(List<Ticket> tickets) {
        return tickets.stream()
                .map(t -> t.compatibleFields(this))
                .reduce(SetUtils::intersection)
                .map(ArrayList::new)
                .orElseThrow();
    }

    private static Pattern pattern = Pattern.compile("([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)");

    public static TicketRule fromString(String string) {
        var rule = new TicketRule();
        var m = pattern.matcher(string);
        if (m.matches()) {
            rule.name = m.group(1);
            rule.min1 = Integer.parseInt(m.group(2));
            rule.max1 = Integer.parseInt(m.group(3));
            rule.min2 = Integer.parseInt(m.group(4));
            rule.max2 = Integer.parseInt(m.group(5));
            return rule;
        } else {
            throw new RuntimeException("Can't parse " + string);
        }
    }

}
