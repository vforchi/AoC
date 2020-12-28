package me.vforchi.aoc.y2020.day02;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public class PasswordAndPolicy {
    private static Pattern pattern = Pattern.compile("([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)");

    private final int firstLimit;
    private final int secondLimit;
    private final char value;
    private final String password;

    public static PasswordAndPolicy fromString(String line) {
        var m = pattern.matcher(line);
        if (m.matches()) {
            var one = Integer.parseInt(m.group(1));
            var two = Integer.parseInt(m.group(2));
            var value = m.group(3).toCharArray()[0];
            return new PasswordAndPolicy(one, two, value, m.group(4));
        } else {
            throw new RuntimeException("Line not parsable: " + line);
        }
    }

    public boolean isValid1() {
        var found = this.password.chars()
                .filter(s -> s == this.value)
                .count();

        return (found >= firstLimit && found <= secondLimit);
    }

    public boolean isValid2() {
        var c1 = this.password.charAt(this.firstLimit - 1);
        var c2 = this.password.charAt(this.secondLimit - 1);

        return (this.value == c1 && this.value != c2) ||
               (this.value == c2 && this.value != c1);
    }

}
