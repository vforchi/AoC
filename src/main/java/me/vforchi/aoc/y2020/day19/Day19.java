package me.vforchi.aoc.y2020.day19;

import me.vforchi.aoc.Day;
import me.vforchi.aoc.y2020.Utils;
import com.google.common.base.Functions;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 extends Day {

    Map<Integer, Rule> rules;
    List<String> messages;

    @Override
    public Object partOne() {
        return this.messages.stream()
                .filter(this::matchesRule0)
                .count();
    }

    public boolean matchesRule0(String message) {
        return message.matches(getRuleRegex(0));
    }

    @Override
    public Object partTwo() {
        return this.messages.stream()
                .filter(this::matchesRules42And31)
                .count();
    }

    public boolean matchesRules42And31(String message) {
        var regex42 = this.getRuleRegex(42);
        var regex31 = this.getRuleRegex(31);
        var regex = String.format("(%s+)(%s+)", regex42, regex31);

        var m = Pattern.compile(regex).matcher(message);
        if (m.matches()) {
            return countMatches(regex42, m.group(1)) > countMatches(regex31, m.group(2));
        } else {
            return false;
        }
    }

    private int countMatches(String regex, String matchGroup) {
        int matches = 0;
        var m = Pattern.compile(regex).matcher(matchGroup);
        while(m.find()) {
            matches++;
        }
        return matches;
    }

    public String getRuleRegex(int idx) {
        var rule = rules.get(idx);
        if (rule.value != null) {
            return rule.value.toString();
        } else if (rule.secondGroup.size() == 0) {
            return String.format("(?:%s)", ruleListToRegex(rule.firstGroup));
        } else {
            return String.format("(?:%s|%s)", ruleListToRegex(rule.firstGroup), ruleListToRegex(rule.secondGroup));
        }
    }

    private String ruleListToRegex(List<Integer> firstGroup) {
        return firstGroup.stream()
                .map(this::getRuleRegex)
                .collect(Collectors.joining());
    }

    @Override
    public void setup(String path) {
        super.setup(path);

        var sections = Utils.partitionByEmptyLine(this.input)
                .collect(Collectors.toList());

        this.rules = sections.get(0).stream()
                .map(Rule::new)
                .collect(Collectors.toMap(
                        r -> r.number,
                        Functions.identity()
                ));

        messages = sections.get(1);
    }

}