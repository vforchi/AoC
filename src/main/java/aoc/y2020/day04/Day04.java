package aoc.y2020.day04;

import aoc.Day;

import java.util.*;

public class Day04 extends Day {

    Set<Passport> passports = new HashSet<>();

    @Override
    public Object partOne() {
        return passports.stream()
                .filter(Passport::isValid1)
                .count();
    }

    @Override
    public Object partTwo() {
        return passports.stream()
                .filter(Passport::isValid2)
                .count();
    }

    @Override
    public void setup(String path) {
        super.setup(path);

        List<String> current = new ArrayList<>();
        for (var line: this.input) {
            if (line.length() == 0) {
                passports.add(Passport.fromFields(current));
                current = new ArrayList<>();
            } else {
                current.addAll(Arrays.asList(line.split(" ")));
            }
        }
        passports.add(Passport.fromFields(current));
    }

}
