package aoc.y2020.day04;

import aoc.Day;
import aoc.y2020.Utils;

import java.util.*;
import java.util.stream.Collectors;

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
    public void setup(String path) throws Exception {
        super.setup(path);

        passports = Utils.partitionByEmptyLine(input)
                .map(Passport::fromFields)
                .collect(Collectors.toSet());
    }

}
